package com.ssafy.hajo.plan

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.hajo.databinding.FragmentPlanAddBinding
import com.ssafy.hajo.util.GlobalApplication
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class PlanAddFragment(context: Context) : BottomSheetDialogFragment() {
    private val mContext: Context = context
    private lateinit var binding: FragmentPlanAddBinding
    private var mode: Int = 0 // 대플랜 0, 중플랜 1
    private val viewModel: PlanDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mode = requireArguments().getInt("mode",0)

        initMode()

        binding.imgAddColor.setOnClickListener {
            val bundle = Bundle()
            val editColorFragment = EditColorFragment(requireActivity()).apply {
                arguments = bundle
            }
            Log.d("addPlanColor", "addPlanColor}")
            editColorFragment.show(requireActivity().supportFragmentManager, editColorFragment.tag)
        }


        viewModel.mPlanColor.observe(this) {
            if(viewModel.mPlanColor.value != null && viewModel.mPlanColor.value != "") {
                binding.imgAddColor.setBackgroundColor(Color.parseColor(viewModel.mPlanColor.value))
            }
        }

    }

    private fun initMode() {

        if (mode == 0) {
            binding.tvAddBplanTitle.text = "대플랜 생성"
            binding.tvAddPlanName.text = "대플랜 이름"
            binding.tvAddPlanDescription.text = "대플랜 설명"
            binding.etPlanAddTitle.hint = "대플랜 이름을 입력하세요."
            binding.etPlanAddDetail.hint = "대플랜 설명을 입력하세요."
            binding.imgAddColor.visibility = View.GONE

            binding.btnAddPlan.setOnClickListener {
                if (!(binding.etPlanAddTitle.text.toString() == "") && !(binding.etPlanAddDetail.text.toString() == "")
                    && !binding.tvStartDay.text.equals("시작 날짜") && !binding.tvEndDay.text.equals("종료 날짜")
                ) {

                    val newBplan = HashMap<String, String>()
                    val userId = GlobalApplication.userPrefs.getString("uid","")!!

                    newBplan.set("uid", userId) // todo userid 넣기
                    newBplan.set("grandplanTitle", binding.etPlanAddTitle.text.toString())
                    newBplan.set("grandplanDesc", binding.etPlanAddDetail.text.toString())
                    newBplan.set("grandplanStartdate", binding.tvStartDay.text.toString())
                    newBplan.set("grandplanEnddate", binding.tvEndDay.text.toString())
                    newBplan.set("grandplanColor", "#ffffff")

                    viewModel.addBPlan(newBplan)

                } else {
                    Toast.makeText(activity, "이름, 날짜, 설명을 입력하세요.", Toast.LENGTH_SHORT).show()
                }

                binding.etPlanAddTitle.setText("")
                binding.etPlanAddDetail.setText("")
                binding.tvStartDay.text = "시작 날짜"
                binding.tvEndDay.text = "종료 날짜"
                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();
            }

            var dateStartString: String = ""
            binding.tvStartDay.setOnClickListener { // 시작일 설정

                val cal = Calendar.getInstance()

                // 달력 클릭 시 이벤트
                val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    var monthS = if(month+1 < 10)  "0" + (month+1) else month+1
                    var dayOfMonthS = if(dayOfMonth < 10) "0" + dayOfMonth else dayOfMonth

                    dateStartString = "${year}-${monthS}-${dayOfMonthS}" // 달력의 날짜를 string으로 변환
                    binding.tvStartDay.text = dateStartString
                }

                // 시작 일 설정
                val dialog = DatePickerDialog(context as Activity, dateSetListener, cal.get(Calendar.YEAR),cal.get(
                    Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH))
                    .apply {

                    }
                dialog.show()

            }

            binding.tvEndDay.setOnClickListener { // 종료일 설정
                val cal = Calendar.getInstance()
                // 가져온 시작 날짜를 long으로 변환 한다.
                if(dateStartString.equals("")) {
                    Toast.makeText(activity,"시작 일을 입력해주세요.",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                var dateEndLong = stringToLong(dateStartString)

                // 달력 클릭 시 이벤트
                val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    var monthS = if(month+1 < 10)  "0" + (month+1) else month+1
                    var dayOfMonthS = if(dayOfMonth < 10) "0" + dayOfMonth else dayOfMonth

                    val dateEndString = "${year}-${monthS}-${dayOfMonthS}" // 달력의 날짜를 string으로 변환
                    binding.tvEndDay.text = dateEndString
                }
                DatePickerDialog(context as Activity, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(
                    Calendar.DAY_OF_MONTH))
                    .apply {
                        datePicker.minDate = stringToLong(dateStartString)
                    }.show()
            }
        } else {
            binding.tvAddBplanTitle.text = "중플랜 생성"
            binding.tvAddPlanName.text = "중플랜 이름"
            binding.tvAddPlanDescription.text = "중플랜 설명"
            binding.etPlanAddTitle.hint = "중플랜 이름을 입력하세요."
            binding.etPlanAddDetail.hint = "중플랜 설명을 입력하세요."
            binding.imgAddColor.visibility = View.VISIBLE


            binding.btnAddPlan.setOnClickListener {


                if (!binding.etPlanAddTitle.text.toString().equals("") && !binding.etPlanAddDetail.text.toString().equals(
                        ""
                    )
                    && !binding.tvStartDay.text.equals("시작 날짜") && !binding.tvEndDay.text.equals("종료 날짜")
                ) {

                    val newMplan = HashMap<String, String>()
                    val userId = GlobalApplication.userPrefs.getString("uid","")!!
                    newMplan.set("uid", userId) //
                    newMplan.set("grandplanSeq", viewModel.bPlan1.value!!.grandplanSeq.toString())
                    newMplan.set("midplanTitle", binding.etPlanAddTitle.text.toString())
                    newMplan.set("midplanDesc", binding.etPlanAddDetail.text.toString())
                    newMplan.set("midplanStartdate", binding.tvStartDay.text.toString())
                    newMplan.set("midplanEnddate", binding.tvEndDay.text.toString())
                    newMplan.set("midplanColor", viewModel.mPlanColor.value!!)

                    viewModel.addMPlan(newMplan)

                } else {
                    Toast.makeText(activity, "이름, 날짜, 설명을 입력하세요.", Toast.LENGTH_SHORT).show()
                }

                binding.etPlanAddTitle.setText("")
                binding.etPlanAddDetail.setText("")
                binding.tvStartDay.text = "시작 날짜"
                binding.tvEndDay.text = "종료 날짜"
                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();
            }

            var dateString: String = ""
            binding.tvStartDay.setOnClickListener { // 시작일 설정
                val cal = Calendar.getInstance()

                // 달력 클릭 시 이벤트
                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        var monthS = if(month+1 < 10)  "0" + (month+1) else month+1
                        var dayOfMonthS = if(dayOfMonth < 10) "0" + (dayOfMonth) else (dayOfMonth)

                        dateString = "${year}-${monthS}-${dayOfMonthS}" // 달력의 날짜를 string으로 변환
                        binding.tvStartDay.text = dateString
                    }

                // 시작 일 설정
                val dialog = DatePickerDialog(
                    context as Activity, dateSetListener, cal.get(Calendar.YEAR), cal.get(
                        Calendar.MONTH
                    ), cal.get(Calendar.DAY_OF_MONTH)
                )
                    .apply {

                    }
                dialog.show()

            }

            binding.tvEndDay.setOnClickListener { // 종료일 설정
                val cal = Calendar.getInstance()
                // 가져온 시작 날짜를 long으로 변환 한다.
                if (dateString.equals("")) {
                    Toast.makeText(activity, "시작 일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                var dateEndLong = stringToLong(dateString)

                // 달력 클릭 시 이벤트
                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        var monthS = if(month+1 < 10)  "0" + (month+1) else month+1
                        var dayOfMonthS = if(dayOfMonth < 10) "0" + dayOfMonth else dayOfMonth

                        binding.tvEndDay.text = "${year}-${monthS}-${dayOfMonthS}" // 달력의 날짜를 string으로 변환
                    }
                DatePickerDialog(
                    context as Activity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(
                        Calendar.DAY_OF_MONTH
                    )
                )
                    .apply {
                        datePicker.minDate = stringToLong(dateString)
                    }.show()
            }
        }

//            //확인 버튼 클릭 시 저장
//            binding.btnAddPlan.setOnClickListener {
//
//            }

    }

    fun stringToLong(date: String): Long { // 날짜 string을 long으로 변환
        val df = SimpleDateFormat("yyyy-MM-dd")
        val dateLong = df.parse(date).time
        return dateLong
    }
}




