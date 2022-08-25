package com.ssafy.hajo.plan

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityPlanDailyBinding
import com.ssafy.hajo.databinding.CalendarTasksDayBinding
import com.ssafy.hajo.databinding.DailyTasksItemViewBinding
import com.ssafy.hajo.home.MemoAdapter
import com.ssafy.hajo.home.TodoAdapter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter




@RequiresApi(Build.VERSION_CODES.O)
class PlanDailyActivity : AppCompatActivity() {

    // 캘린더 세팅?
    private var selectedDate = LocalDate.now()
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    // 캘린더 시작일 설정
    private var startDay: String? = ""
    private lateinit var startDayLocal: LocalDate

    // init 체크
    private var isInit = true

    // 테스크 목록(임시)
    data class taskD(
        val hasMemo: Boolean,
        val hasAlarm: Boolean,
        val hasPicture: Boolean,
        val content: String
    )
    private lateinit var tasks: MutableList<Task>

    private lateinit var viewModel: PlanDetailViewModel
    private lateinit var binding: ActivityPlanDailyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanDailyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tasks = mutableListOf()


        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(PlanDetailViewModel::class.java)

        startDay = intent.getStringExtra("startDate")
        startDayLocal = LocalDate.parse(startDay, DateTimeFormatter.ISO_DATE)

        binding.tvDay.text = startDayLocal.toString()
        selectedDate = startDayLocal

        initObserve()
        initData()
        initUI()
        initBtn()

    }

    private var plans = mutableListOf<LowPlanInfoUse>().groupBy {it.smallplanDate}
    private fun initObserve() {
        viewModel.bPlan1.observe(this) {
            Log.d("planDaily1","observe 현재 BPlan ============= 변경 감지됨 ${viewModel.bPlan1.value}")

            var planRaw = viewModel.bPlan1.value!!.subDto
            var planUse = mutableListOf<LowPlanInfoUse>()
            for(i in planRaw) {
                planUse.add(LowPlanInfoUse(i.midplanSeq,i.smallplanSeq,stringToLocaldate(i.smallplanDate),
                    i.midplanTitle,i.midplanColor,stringToLocaldate(i.midplanStart),i.hasTask))
            }
            plans = planUse.groupBy{it.smallplanDate} //todo 캘린더 init 해줘야하나?
            Log.d("planDaily1","observe 현재 BPlan에서 viewModel.setLowPlanUseList(planUse) ${planUse}")

//            viewModel.setLowPlanUseList(planUse)

            var smallPlanSeq = intent.getIntExtra("smallplanSeq", 0)
            Log.d("planDaily1","initdata getDayPlanInfo 시작 $smallPlanSeq")
            viewModel.getDayPlanInfo(smallPlanSeq)
//            initCalendar()

        }

        viewModel.littlePlanInfo.observe(this) {
            Log.d("planDaily1","observe littlePlanInfo 현재 일자 별 리스트 변경 감지됨 ${viewModel.littlePlanInfo.value}")

            if(viewModel.littlePlanInfo.value!!.smallplanSeq != -1) {
                Log.d("planDaily1", "소플랜 존재, 중플랜 이름 ${viewModel.littlePlanInfo.value!!.midplanTitle}")
                binding.tvMPlanName.text = viewModel.littlePlanInfo.value!!.midplanTitle
//                binding.imgMPlanColor.visibility = View.VISIBLE
                binding.tvEditMPlan.visibility = View.VISIBLE
                binding.imgAddTask.visibility = View.VISIBLE

            } else {
                Log.d("planDaily1", "소플랜 없음, 중플랜 이름 없음")
                binding.tvMPlanName.text = "중플랜을 생성하세요!"
//                binding.imgMPlanColor.visibility = View.GONE
                binding.tvEditMPlan.visibility = View.INVISIBLE
                binding.imgAddTask.visibility = View.INVISIBLE

            }

            viewModel.setTaskList(viewModel.littlePlanInfo.value!!.plantaskList)
            Log.d("planDaily1","observe littlePlanInfo setTaskList ${viewModel.taskList.value}")

            if(isInit) {
                initCalendar()
            }
            setRecyclerView()

//처음에만 진행? dayGetinfo 이후에 진행 되게


        }

        viewModel.taskDone.observe(this) {
            Log.d("planDaily1","observe taskDone 태스크 완료체크 ${viewModel.taskDone.value}")
            if(viewModel.taskDone.value != -1) {
                todoAdapter.notifyDataSetChanged()
            } else {
                todoAdapter.updateTaskChecked(viewModel.taskDone.value!!)
            }

        }

        viewModel.taskList.observe(this) {
//            if(viewModel.taskList.value != null && viewModel.taskList.value!!.size > 0) {
//                for (i in viewModel.taskList.value!!) {
//                    if(i.img != "" && i.img.toCharArray()[0] == 'h') {
//                        Glide.with(this@PlanDailyActivity).load(i.img)
//                    }
//                }
//            }
        }

        viewModel.mPlanName.observe(this) {
            Log.d("mplandetail","observe mPlanName 이름 변경 ${viewModel.mPlanName.value}")
            viewModel.littlePlanInfo.value!!.midplanTitle = viewModel.mPlanName.value!!
            binding.tvMPlanName.text = viewModel.mPlanName.value
        }

        viewModel.mPlanDes.observe(this) {
            Log.d("mplandetail","observe mPlanDes 설명 변경 ${viewModel.mPlanDes.value}")
            viewModel.littlePlanInfo.value!!.midplanDesc = viewModel.mPlanDes.value!!
        }

        viewModel.deleteMplan.observe(this) {
            binding.tvMPlanName.text = "중플랜 없음"
            binding.tvEditMPlan.visibility = View.GONE
//            binding.imgMPlanColor.visibility = View.GONE
            todoAdapter.update(mutableListOf())
        }



    }
    private fun initData() {
        var grandPlanSeq = intent.getIntExtra("gradplanSeq", 0)
        Log.d("planDaily1","initdata getBplanInfo 시작")
        viewModel.getBplanInfo(grandPlanSeq)


//        var smallPlanSeq = intent.getIntExtra("smallplanSeq", 0)
//        Log.d("planDetailViewModel1","initdata getDayPlanInfo 시작 $smallPlanSeq")
//        viewModel.getDayPlanInfo(smallPlanSeq)


    }

    private fun initUI() {


    }

    private fun initBtn() {
        binding.imgAddTask.setOnClickListener {
            // 태스크 추가
            val bundle = Bundle()
            bundle.putInt("taskPositon", -1)
            val taskDetailFragment = TaskDetailFragment(applicationContext).apply {
                arguments = bundle
            }
            Log.d("taskdetail", "add task 화면 띄우기 시작 -1")
            taskDetailFragment.show(supportFragmentManager, taskDetailFragment.tag)
        }

        binding.etMPlanDescription.setOnClickListener { // todo 중플랜 수정 버튼 따로 만들자 ...

        }

        binding.tvEditMPlan.setOnClickListener {
            var bundle = Bundle()
            val mplanDetailFragment = MPlanDetailFragment(applicationContext).apply {
                arguments = bundle
            }
            Log.d("mplandetail", "edit 으로 mplandetail 진입 ${viewModel.bPlan1.value}")
            Log.d("mplandetail", "edit 으로 mplandetail 진입 ${viewModel.littlePlanInfo.value}")
            mplanDetailFragment.show(supportFragmentManager, mplanDetailFragment.tag)
        }


    }

    private fun initCalendar() {
        // 한 줄만 표시
        Log.d("planDaily1","init Calendar ")
        val dm = DisplayMetrics()
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        binding.planDailyCalendar.apply {
            val dayWidth = dm.widthPixels / 5
            val dayHeight = (dayWidth * 1.25).toInt()
            daySize = Size(dayWidth, dayHeight)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = CalendarTasksDayBinding.bind(view)
            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    val firstDay = binding.planDailyCalendar.findFirstVisibleDay()
                    val lastDay = binding.planDailyCalendar.findLastVisibleDay()

                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        binding.planDailyCalendar.notifyDateChanged(day.date)
                        oldDate?.let { binding.planDailyCalendar.notifyDateChanged(it) }
                        var plan = plans[day.date]
                        if(plan != null) {
                            Log.d("planDaily1", "소플랜 존재, 중플랜 이름 ${plan!![0].midplanTitle}")
                            viewModel.getDayPlanInfo(plan!![0].smallplanSeq)
//                            binding.tvMPlanName.text = plan!![0].midplanTitle

//                            binding.imgMPlanColor.visibility = View.VISIBLE
                            binding.tvEditMPlan.visibility = View.VISIBLE
                        } else {
                            Log.d("planDaily1", "소플랜 없음, 중플랜 이름 없음")
//                            binding.tvMPlanName.text = "중플랜이 없어"
                            viewModel.getDayPlanInfo(-1)
//                            binding.imgMPlanColor.visibility = View.GONE
//                            binding.tvEditMPlan.visibility = View.GONE

                        }
                    }

                }
            }

            fun bind(day: CalendarDay) {
                this.day = day
                bind.exSevenDateText.text = dateFormatter.format(day.date)
                bind.exSevenDayText.text = dayFormatter.format(day.date)
                bind.exSevenMonthText.text = monthFormatter.format(day.date)

//                binding.tvDay.text = dateFormatter.format(day.date)

                bind.exSevenDateText.setTextColor(view.context.getColorCompat(if (day.date == selectedDate) R.color.grey_80 else R.color.black))
                bind.exSevenSelectedView.isVisible = day.date == selectedDate

                var plan = plans[day.date]
                if(plan != null) {

                    Log.d("planDetailRec", "init, getDayPlaninfo setRec 시작 smallSeq ${plan!![0].smallplanSeq} middleTitle ${plan!![0].midplanTitle}")
                    Log.d("planDaily1", "bind 소플랜 존재, 중플랜 이름 ${plan!![0].midplanTitle}")
                    if(plan[0].midplanColor != null && plan[0].midplanColor != " ") {
                        bind.exSevenDateText.setTextColor(Color.parseColor(plan[0].midplanColor))
                    }
                } else {
                    Log.d("planDaily1", "bind소플랜 없음, 중플랜 이름 없음")

                }
            }
        }

        binding.planDailyCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
        }

        val currentMonth = YearMonth.now()
        // Value for firstDayOfWeek does not matter since inDates and outDates are not generated.
        binding.planDailyCalendar.setup(currentMonth, currentMonth.plusMonths(3), DayOfWeek.values().random())
//        binding.planDailyCalendar.scrollToDate(LocalDate.now())
        binding.planDailyCalendar.scrollToDate(startDayLocal)

        Log.d("planDaily1","init Calendar 끝")

    }

    lateinit var todoAdapter: DailyTodoAdapter
    private fun setRecyclerView() {
        Log.d("planDaily1Rec","setRecyclerView 시작 tasks : ${viewModel.littlePlanInfo.value!!.plantaskList}")

        if(isInit) {
            todoAdapter = DailyTodoAdapter(this)
            binding.rcTasks.layoutManager = LinearLayoutManager(this)
            binding.rcTasks.adapter = todoAdapter
            Log.d("planDaily1Rec", "Recycle list 값 ${todoAdapter.list}")
            todoAdapter.update(viewModel.taskList.value!!)
            Log.d("planDaily1Rec", "update Recycle list 값 ${todoAdapter.list}")
            Log.d("planDaily1Rec", "setOnClickEvent ")
            setOnClickEvent()
            isInit = false
        } else {
            todoAdapter.update(viewModel.taskList.value!!)
        }
    }



    private fun setOnClickEvent() {
        todoAdapter.setDetailClickListener(object: DailyTodoAdapter.DetailClickListener {
            override fun onClick(view: View, position: Int, taskSeq: Long, isDone: Boolean) {

                // 태스크 추가
                val bundle = Bundle()
                bundle.putInt("taskPositon", position)
                val taskDetailFragment = TaskDetailFragment(applicationContext).apply {
                    arguments = bundle
                }
                Log.d("taskdetail", "Edit task 화면 띄우기 시작 ${position}")
                taskDetailFragment.show(supportFragmentManager, taskDetailFragment.tag)

            }
        })

        todoAdapter.setItemCheckBoxClickListener(object: DailyTodoAdapter.ItemCheckBoxClickListener {
            override fun onClick(view: View, position: Int, taskSeq: Long, isDone: Boolean) {
                    if(isDone) { // 이미 달성된 상태면
                        todoAdapter.notifyDataSetChanged()
                        return
                    } else { // 아직 달성하지 않은 상태면
                        val builder = AlertDialog.Builder(this@PlanDailyActivity)
//                            .setTitle("플랜 삭제")
                            .setMessage("정말 할 일을 완료할까요?")
                            .setPositiveButton("확인",
                                DialogInterface.OnClickListener{ dialog, which ->
                                        viewModel.checkTask(taskSeq.toInt(), position)
                                        todoAdapter.updateTaskChecked(position)
                                })
                            .setNegativeButton("취소",
                                DialogInterface.OnClickListener { dialog, which ->
                                    todoAdapter.notifyDataSetChanged()

                                })
                        builder.show()

                    }

            }
        })

    }

}
@RequiresApi(Build.VERSION_CODES.O)
fun stringToLocaldate(date: String): LocalDate{
    val date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
    return date
}

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)
internal val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)


//@RequiresApi(Build.VERSION_CODES.O)
//class DailyTasksAdapter(var tasks: MutableList<Task>) : RecyclerView.Adapter<DailyTasksAdapter.DailyTasksViewHolder>() {
//
//
////    private val formatter = DateTimeFormatter.ofPattern("EEE'\n'dd MMM'\n'HH:mm")
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyTasksViewHolder {
//        Log.d("planDetailRec","onCreateViewHolder")
//
//        return DailyTasksViewHolder(
//            DailyTasksItemViewBinding.inflate(parent.context.layoutInflater, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(viewHolder: DailyTasksViewHolder, position: Int) {
//        viewHolder.bind(tasks[position])
//        Log.d("planDetailRec","onBindViewHolder")
//
//        viewHolder.binding.tvTaskContent.text = tasks[position].title
//
//
//    }
//
//    override fun getItemCount(): Int = tasks.size
//
//    inner class DailyTasksViewHolder(val binding: DailyTasksItemViewBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(task: Task) {
//            binding.tvTaskContent.apply {
//                text = task.title
//            }
//            Log.d("planDetailRec","adpater task ${task}")
//
//            if(task.memo == null) binding.imgMemo.visibility = View.INVISIBLE
//            if(task.img == null) binding.imgMemo.visibility = View.INVISIBLE
//            if(task.alramtime == null) binding.imgMemo.visibility = View.INVISIBLE
//
//        }
//    }
//}