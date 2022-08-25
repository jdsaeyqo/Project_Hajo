package com.ssafy.hajo.plan

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import androidx.lifecycle.ViewModelProvider
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityPlanDetailBinding
import com.ssafy.hajo.databinding.PlanDetailCalendarDayBinding
import com.ssafy.hajo.databinding.PlanDetailCalendarHeaderBinding
import com.ssafy.hajo.diary.DiaryHomeActivity
import com.ssafy.hajo.repository.dao.PlanDetailRepository
import okhttp3.internal.notify
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*


data class Plan(val name: String, val color: Int, var startDay: LocalDateTime, val endDay: LocalDateTime) {
}   // 임시 중플랜 정보(이름, 색상, 시작 날짜, 종료 날짜)


@RequiresApi(Build.VERSION_CODES.O)
class PlanDetailActivity : AppCompatActivity() {

    //첫 캘린더 설정인지 확인하는 변수
    private var isInit = true

    //캘린더 변수
    private var selectedDate: LocalDate? = null // 기존 선택된 날짜
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM") //현재 달
//    private val plans = mutableListOf<Plan>().apply {
//        add(Plan("중플랜1", R.color.p_red, YearMonth.now().atDay(15).atTime(1,1), YearMonth.now().atDay(18).atTime(1,1)))
//        add(Plan("중플랜2", R.color.p_yellow, java.time.YearMonth.now().atDay(7).atTime(1,1), java.time.YearMonth.now().atDay(11).atTime(1,1)))
//        add(Plan("중플랜3", R.color.p_blue, java.time.YearMonth.now().atDay(20).atTime(1,1), java.time.YearMonth.now().atDay(27).atTime(1,1)))
//    }.groupBy { it.startDay.toLocalDate() }
    private var plans = mutableListOf<LowPlanInfoUse>().groupBy {it.smallplanDate}
    fun stringToLocaldate(date: String): LocalDate{
        val date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
        return date
    }
    //대플랜 정보
    var bPlanId: Int = 0


    public lateinit var viewModel : PlanDetailViewModel
    private lateinit var binding: ActivityPlanDetailBinding
    lateinit var spinner: Spinner

    private val planRepository: PlanDetailRepository by lazy {
        PlanDetailRepository()
    }

    //유저 id >> 대플랜 id, 이름 리스트를 받습니다. -> 먼저 대플랜 정보만 받아온다. 이후 ... 스피너 아이템 클릭 시 대플랜 별로 소플랜들 다 가져오기.
    //spinner list에 세팅하고 특정 id 의 대플랜 조회 리스트 갱신해서 보여줌.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo 지우기
        binding.btnDiary.setOnClickListener {
            startActivity(Intent(this, DiaryHomeActivity::class.java))
        }

        //view 모델 객체 생성
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(PlanDetailViewModel::class.java)

        //액티비티 진입 시 추가인지, 조회인지 확인
        bPlanId = intent.getIntExtra("planSeq", 0)
        Log.d("planDetailActivity","bPlanId : $bPlanId")
        //초기 설정
        initActivity()
//        if(bPlanId == 0) {
//            Log.d("planDetailActivity", "initCalendar")
//            initCalendar()
//        }

    }

    private fun initActivity() {
        initObserve() // UI Observe 설정
        initUI()
        initBtn() // 버튼 설정


    }

    private fun initUI() { // 서버에서 대플랜 정보를 가져와서 화면에 그린다.

        // 서버에서 해당 유저의 모든 대플랜 리스트 가져오기
        Log.d("planDetailActivity","spinner list 가져오기 init setSpinnerList")
        viewModel.setSpinnerList()




    }

    private fun initObserve() {
        //UI observe 설정
        viewModel.bPlan1.observe(this) {
            Log.d("planDetailActivity","observe 현재 BPlan ============= 변경 감지됨 ${viewModel.bPlan1.value}")
            binding.tvPlanDetailBigplanStart.text = it.grandplanStart
            binding.tvPlanDetailBigplanEnd.text = it.grandplanEnd
            if(bPlanId != null && bPlanId == 0) {
                binding.tvBplanAdd.callOnClick()
            }
            Log.d("planDetailActivity","observe 현재 BPlan ============= 변경 감지됨 bPlanId 변경 값 ${viewModel.bPlan1.value!!.grandplanSeq}")
            bPlanId = viewModel.bPlan1.value!!.grandplanSeq

            var planRaw = viewModel.bPlan1.value!!.subDto
            var planUse = mutableListOf<LowPlanInfoUse>()
            for(i in planRaw) {
                planUse.add(LowPlanInfoUse(i.midplanSeq,i.smallplanSeq,stringToLocaldate(i.smallplanDate),
                i.midplanTitle,i.midplanColor,stringToLocaldate(i.midplanStart),i.hasTask))
            }
            plans = planUse.groupBy{it.smallplanDate} //todo 캘린더 init 해줘야하나?
            Log.d("planDetailActivity","observe 현재 BPlan plans 값 ${plans}")

            viewModel.setLowPlanUseList(planUse)

//            if(isInit) {
//                initCalendar()
//            } else {
//                binding.planDetailCalendar.
//            }
            initCalendar()

        }

        viewModel.planSeq.observe(this) { // 대플랜 추가 후 변경할 대플랜 seq
            Log.d("planDetailActivity","planSeq.observe에서 setSpinner 동작 planSeq는 ${viewModel.planSeq.value!!}")

            if(viewModel.planSeq.value != null){
                Log.d("planDetailActivity","planSeq.observe에서 setSpinner 동작 planSeq는 ${viewModel.planSeq.value!!}")
                bPlanId = viewModel.planSeq.value!!
                viewModel.setSpinnerList()
            }

        }

        viewModel.startDay.observe(this) {
            binding.tvPlanDetailBigplanStart.text = viewModel.startDay.value
        } // todo 날짜 없데이트 시 Bplan 최신화 또는 날짜 최신화 함께 하기

        viewModel.endDay.observe(this) {
            binding.tvPlanDetailBigplanStart.text = viewModel.endDay.value
        } // todo 날짜 없데이트 시 Bplan 최신화 또는 날짜 최신화 함께 하기

        viewModel.bPlansForSpinner.observe(this) { // spinner itemlist 갱신, 비어있으면 추가 창 띄움
            Log.d("planDetailActivity","observer에서 setSpinner 동작 ${viewModel.bPlansForSpinner.value}")
            setSpinner()
        }

    }

    private fun initBtn() {
        binding.addB.setOnClickListener {
            val bundle1 = Bundle()
            val planAddFragment1 = PlanAddFragment(applicationContext).apply {
                arguments = bundle1
                bundle1.putInt("mode", 0) // 중플랜 추가 1, 대플랜 추가 0
                show(supportFragmentManager, this.tag)
            }
        }

        binding.tvPlanDetailBigplanStart.setOnClickListener { // 시작일 설정
            val cal = Calendar.getInstance()
            var dateString: String = viewModel.bPlan1.value!!.grandplanStart
            // 가져온 시작 날짜를 long으로 변환 한다.

            var dateStartLong = 0L
            if(dateString.equals("시작 날짜")) {
                dateStartLong = stringToLong(dateString)
            } else {
                dateStartLong = cal.timeInMillis
            }


            // 달력 클릭 시 이벤트
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->


                var monthS = if(month+1 < 10)  "0" + (month+1) else month+1
                var dayOfMonthS = if(dayOfMonth < 10) "0" + dayOfMonth else dayOfMonth



                dateString = "${year}-${monthS}-${dayOfMonthS}" // 달력의 날짜를 string으로 변환
                viewModel.updateBPlanStartday(dateString) // 서버에 변경된 날짜 전달, planb1의 startDay에 값 저장
                binding.tvPlanDetailBigplanStart.text = dateString
            }

            // 시작 일 설정
            val dialog = DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH))
                .apply {
                    if((viewModel.bPlan1.value as BPlan).subDto.size != 0) {
                        datePicker.maxDate =
                            stringToLong((viewModel.bPlan1.value as BPlan).subDto[0].smallplanDate)
                    }
                    // 시작 날짜로 달력을 설정한다.
                    cal.time = Date(dateStartLong)
                }
            dialog.show()

        }

        binding.tvPlanDetailBigplanEnd.setOnClickListener { // 종료일 설정
            val cal = Calendar.getInstance()
            var dateString: String = viewModel.bPlan1.value!!.grandplanEnd
            // 가져온 시작 날짜를 long으로 변환 한다.
            var dateEndLong = 0L

            if(dateString.equals("시작 날짜")) {
                dateEndLong = stringToLong(dateString)
            } else {
                dateEndLong = cal.timeInMillis
            }

            // 달력 클릭 시 이벤트
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                var monthS = if(month+1 < 10)  "0" + (month+1) else month+1
                var dayOfMonthS = if(dayOfMonth < 10) "0" + dayOfMonth else dayOfMonth



                dateString = "${year}-${monthS}-${dayOfMonthS}" // 달력의 날짜를 string으로 변환
                viewModel.updateBPlanEndday(dateString) // 서버에 변경된 날짜 전달, planb1의 startDay에 값 저장
                binding.tvPlanDetailBigplanEnd.text = dateString
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH))
                .apply {
                    if((viewModel.bPlan1.value as BPlan)
                            .subDto.size != 0) {
                        datePicker.minDate = stringToLong(
                            (viewModel.bPlan1.value as BPlan)
                                .subDto[(viewModel.bPlan1.value as BPlan).subDto.size - 1].smallplanDate
                        )
                        // 시작 날짜로 달력을 설정한다.
                        cal.time = Date(dateEndLong)
                    }
                }.show()
        }

        // 대플랜 설명
        binding.btnBigplanDesc.setOnClickListener {
            showDescription()
        }

        // 플랜 추가
        val bundle = Bundle()
        val planAddFragment = PlanAddFragment(applicationContext).apply {
            arguments = bundle
        }
        binding.tvBplanAdd.setOnClickListener { // 대플랜 추가 버튼

            bundle.putInt("mode", 0) // 중플랜 추가 1, 대플랜 추가 0
            planAddFragment.show(supportFragmentManager, planAddFragment.tag)
        }
        binding.imgAddPlan.setOnClickListener { // 중플랜 추가 버튼
            bundle.putInt("mode", 1) // 중플랜 추가 1, 대플랜 추가 0
            planAddFragment.show(supportFragmentManager, planAddFragment.tag)

        }

        binding.tvBplanDetail.setOnClickListener { // 대플랜 편집
            val bundleD = Bundle()
            val bPlanDetailFragment = BPlanDetailFragment(applicationContext).apply {
                arguments = bundleD
            }
            bundle.putInt("mode", 0) // todo 지우기
            bPlanDetailFragment.show(supportFragmentManager, bPlanDetailFragment.tag)


        }
    }

    private lateinit var sIdList: MutableList<Int>
    private lateinit var sNameList: MutableList<String>
    private fun setSpinner() { // 스피너 설정
        Log.d("planDetailActivity","setSpinner 동작 spinnerlist ${viewModel.bPlansForSpinner.value}")
        spinner = binding.planDetailSpinner // 스피너 객체

        sIdList = mutableListOf<Int>() // 스피너 대플랜들의 id
        sNameList = mutableListOf<String>() // 스피너 대플랜들의 name, 스피너 아이템 리스트

        var viewList = viewModel.bPlansForSpinner.value!! // 비어있으면 size 0으로 온다. // / todo spinner array 비어 있으면 어떻게 되지?
        for(i in viewList) {
            sIdList.add(i.planSeq)
            sNameList.add(i.planTitle.toString())
        } //todo spinner array 비어 있으면 안되면 초기값 추가하기

        Log.d("planDetailActivity","spinner items NameList ${sNameList}")

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sNameList)

        //select 이벤트 작성
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener { // 클릭해서 변경 시
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // spinner.selectedItem.toString() // 이거 그냥 써둔건가?

                // 클릭한 대플랜 id로 뷰 모델의 _bPlan1(BPlan) 정보 변경, observer 감지
                Log.d("planDetailActivity","selection에서 position ${position} sIdList[position]  ${sIdList[position]} 으로 BPlanInfo 시작")
                viewModel.getBplanInfo(sIdList[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        if(sNameList.size == 0) { // 유저의 대플랜이 존재하지 않을 경우
            Log.d("planDetailActivity","spinner item size 0 ${sNameList}")
            bPlanNotExist()
        } else {
            Log.d("planDetailActivity","spinner item size 0 아님 ${sNameList}")
            bPlanExist() // 유저의 대플랜이 존재할 경우 0번 또는 선택 한 번호 띄우기
        }
    }

    private fun bPlanNotExist() { // 유저의 대플랜이 존재하지 않을 경우
        binding.tvBplanDetail.setEnabled(false);
        binding.tvBplanDetail.visibility = View.INVISIBLE
        binding.planDetailSpinner.isEnabled = false
        binding.btnBigplanDesc.visibility = View.GONE
        binding.cExist.visibility = View.GONE
        binding.cNotExist.visibility = View.VISIBLE
        binding.imgAddPlan.isEnabled= false
        binding.imgAddPlan.visibility = View.INVISIBLE
        binding.tvPlanDetailBigplanStart.visibility = View.INVISIBLE
        binding.tvPlanDetailBigplanEnd.visibility = View.INVISIBLE
        binding.planDetailSpinner.visibility = View.GONE
        binding.tvPlanDetailWave.visibility = View.GONE
        binding.tvBplanAdd.visibility = View.GONE

        binding.tvBplanAdd.callOnClick()
    }

    private fun bPlanExist() { // 유저의
        Log.d("planDetailActivity","bPlanExist 동작") // 대플랜이 존재할경우
        binding.tvBplanDetail.setEnabled(true);
        binding.tvBplanDetail.visibility = View.VISIBLE
        binding.planDetailSpinner.isEnabled = true
        binding.btnBigplanDesc.visibility = View.GONE
        binding.cExist.visibility = View.VISIBLE
        binding.cNotExist.visibility = View.GONE
        binding.imgAddPlan.isEnabled= true
        binding.imgAddPlan.visibility = View.VISIBLE
        binding.tvPlanDetailBigplanStart.visibility = View.VISIBLE
        binding.tvPlanDetailBigplanEnd.visibility = View.VISIBLE
        binding.planDetailSpinner.visibility = View.VISIBLE
        binding.tvPlanDetailWave.visibility = View.VISIBLE
        binding.tvBplanAdd.visibility = View.VISIBLE


        Log.d("planDetailActivity","bPlanExist b plan id ${bPlanId}") // 대플랜이 존재할경우

        if(bPlanId == 0) { //todo 0이 아니지만 bplan에 값이 이미 있을 경우 spinner 설정
            Log.d("planDetailActivity","setSelection(0) 동작")
            binding.planDetailSpinner.setSelection(0)
//            Log.d("planDetailActivity","setSelection 0 후 ${sIdList[0]}으로 BPlan 정보 통신 시작")
//            viewModel.getBplanInfo(sIdList[3])
//            Log.d("planDetailActivity","setSelection 0 후 ${sIdList[0]}으로 BPlan 정보 통신 후 ${viewModel.bPlan1.value}")

        } else {
            var selectionIndex = 0
            Log.d("planDetailActivity","sIdList ${sIdList}")
            sIdList.forEachIndexed { index, i ->
                if(i  == bPlanId) {
                    selectionIndex = index
                    Log.d("planDetailActivity","selectionIndex ${selectionIndex}")
                }
            }
            Log.d("planDetailActivity","bPlanExist ${selectionIndex}로 세팅하기")
            binding.planDetailSpinner.setSelection(selectionIndex, false) //todo 데이터 갱신 되면 성공 아니면.. 데이터 불러 주기
//            Log.d("planDetailActivity","setSelection ${selectionIndex} 후 ${bPlanId}으로 BPlan 정보 통신 시작")
//            viewModel.getBplanInfo(bPlanId)
//            Log.d("planDetailActivity","setSelection ${selectionIndex} 후 ${bPlanId}으로 BPlan 정보 통신 후")

        }
        Log.d("planDetailActivity","bPlanExist 끝") // 대플랜이 존재할경우

    }


    private fun showDescription() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.plan_description_dialog, null)
        val des = view.findViewById<EditText>(R.id.et_plan_detail)
        des.setText(viewModel.bPlan1.value!!.grandplanDesc)

        val alertDialog = AlertDialog.Builder(this)
            .setPositiveButton("확인") { dialog, which ->
                // 대플랜 설명 저장
                viewModel.updateBPlanDescription(bPlanId, des.text.toString().trim())
            }
            .setNeutralButton("취소", null)
            .create()

        //  여백 눌러도 창 안없어지게
        alertDialog.setCancelable(false)

        alertDialog.setView(view)
        alertDialog.show()
    }

    private fun initCalendar() {
        // 캘린더 셋팅
        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now() // 현재 달

        //달력 +, - 10개월 출력
        binding.planDetailCalendar.setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
        binding.planDetailCalendar.scrollToMonth(currentMonth) // 초기 : 현재 달 출력

        // 일 별 내용 세팅, 이벤트 처리
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = PlanDetailCalendarDayBinding.bind(view)
            init {
                view.setOnClickListener { // 각 일자 클릭 시 이벤트 처리
                    val plans1 = plans[day.date]
                    if (plans1 != null) {
                        Log.d("planDetailActivityNoHas","소플랜이 없음") // 대플랜이 존재할경우

                        startActivity(Intent(this@PlanDetailActivity, PlanDailyActivity::class.java).apply {
                            putExtra("smallplanSeq", plans1!![0].smallplanSeq)
                            putExtra("smallplanDate", plans1!![0].smallplanDate)
                            putExtra("gradplanSeq", viewModel.bPlan1.value!!.grandplanSeq)
                            putExtra("startDate", day.date.toString())
                            Log.d("planDetailActivityNoHas","===== 일자 보기로 대플랜 ${viewModel.bPlan1.value!!.grandplanSeq} 소플랜 ${plans1!![0].smallplanSeq} 전달") // 대플랜이 존재할경우
                        })
                    } else {
                        Log.d("planDetailActivityNoHas","소플랜이 없음") // 대플랜이 존재할경우
                        Toast.makeText(this@PlanDetailActivity,"해당 날짜에 중플랜을 생성하세요!",Toast.LENGTH_SHORT).show()
                        Log.d("planDetailActivityNoHas","소플랜 없음 토스트 메시지 후") // 대플랜이 존재할경우
                        if (day.owner == DayOwner.THIS_MONTH) { //이번 달을 클릭하면
                            if (selectedDate != day.date) { //선택 날이 현재 선택된 날과 다르면
                                val oldDate = selectedDate //이전 선택된 날
                                selectedDate = day.date // 이번에 선택한 날
                                val binding = this@PlanDetailActivity.binding
                                binding.planDetailCalendar.notifyDateChanged(day.date) // 새 선택일 입력
                                oldDate?.let { binding.planDetailCalendar.notifyDateChanged(it)}
                            }

                        }
                        Log.d("planDetailActivityNoHas","날짜 화면 표시 후") // 대플랜이 존재할경우

                    }

                }
            }
        }

        binding.planDetailCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            @SuppressLint("ResourceAsColor")
            override fun bind(container: DayViewContainer, day: CalendarDay) { // 각 날 값 입력
                container.day = day
                val textView = container.binding.tvCalanderDay
                val layout = container.binding.planDayLayout
                val imgExist = container.binding.tvTaskExist
                textView.text = day.date.dayOfMonth.toString() // 날짜 텍스트 입력

                val planMarker = container.binding.vPalnMarker
                planMarker.background = null

                val today = LocalDate.now()


                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.setTextColorRes(R.color.black) // 날짜 색
                    layout.setBackgroundResource(if (selectedDate == day.date) R.drawable.plan_detail_selected_beige else 0)
                    if(container.day.date == today) {
                        textView.setTextColorRes(R.color.action) // 현재 날짜 색
                    }
                    val plans = plans[day.date]
                    if (plans != null) {
//                        planMarker.setBackgroundColor(this@PlanDetailActivity.getColorCompat(stringToColor(plans[0].midplanColor)))
                        if(plans[0].midplanColor != null && plans[0].midplanColor !="") {
                            planMarker.setBackgroundColor(Color.parseColor(plans[0].midplanColor))
                        } else {
                            planMarker.setBackgroundColor(R.color.primary)
                        }
                        if(plans[0].hasTask == true) {
                                imgExist.visibility = View.VISIBLE
                        }
                    }
                } else {
                    textView.setTextColorRes(androidx.appcompat.R.color.material_grey_50)
                    layout.background = null
                    //todo 선택 불가 처리
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) { // 달 별 정보, 헤더
            val legendLayout = PlanDetailCalendarHeaderBinding.bind(view).legendLayout1.root
        }

        binding.planDetailCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
                        tv.text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                            .toUpperCase(Locale.ENGLISH)
                        tv.setTextColorRes(android.R.color.black)
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    }
                    month.yearMonth
                }
                val planAddFragment = PlanAddFragment(applicationContext)
                container.legendLayout.setOnClickListener{
                    planAddFragment.show(supportFragmentManager, planAddFragment.tag)
                }
            }
        }

        binding.planDetailCalendar.monthScrollListener = { month -> // month 타이틀 리스너
            val title = "${monthTitleFormatter.format(month.yearMonth)} ${month.yearMonth.year}"
            binding.exFiveMonthYearText.text = title

            selectedDate?.let {
                // Clear selection if we scroll to a new month.
                selectedDate = null
                binding.planDetailCalendar.notifyDateChanged(it)
            }

            binding.exFiveNextMonthImage.setOnClickListener { // 화살표 클릭 시 이동
                binding.planDetailCalendar.findFirstVisibleMonth()?.let {
                    binding.planDetailCalendar.smoothScrollToMonth(it.yearMonth.next)
                }
            }

            binding.exFivePreviousMonthImage.setOnClickListener { // 화살표 클릭 시 이동
                binding.planDetailCalendar.findFirstVisibleMonth()?.let {
                    binding.planDetailCalendar.smoothScrollToMonth(it.yearMonth.previous)
                }
            }
        }
        isInit = false
    }

    fun stringToLong(date: String): Long { // 날짜 string을 long으로 변환
        val df = SimpleDateFormat("yyyy-MM-dd")
        val dateLong = df.parse(date).time
        return dateLong
    }

    // 주 정보 가져오기
    fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        var daysOfWeek = DayOfWeek.values()
        // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
        // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            daysOfWeek = rhs + lhs
        }
        return daysOfWeek
    }

    fun stringToColor(color: String): Int {
        return Color.parseColor(color)
    }

    // 색 가져오기
    internal fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))
    internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

    // 뷰 그룹 자식 요소 가져오기
    val ViewGroup.children: Sequence<View>
        get() = object : Sequence<View> {
            override fun iterator() = this@children.iterator() }

    val YearMonth.next: YearMonth
        get() = this.plusMonths(1)

    val YearMonth.previous: YearMonth
        get() = this.minusMonths(1)


    override fun onRestart() {
        super.onRestart()
//        viewModel.setSpinnerList() // 화면으로 돌아올 때 마다 화면 초기화
        viewModel.getBplanInfo(viewModel.bPlan1.value!!.grandplanSeq)

    }
}