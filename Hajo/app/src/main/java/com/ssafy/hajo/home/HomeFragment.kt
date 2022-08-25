package com.ssafy.hajo.home

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.DialogMemoBinding
import com.ssafy.hajo.databinding.FragmentHomeBinding
import com.ssafy.hajo.diary.DiaryHomeActivity
import com.ssafy.hajo.plan.BPlanSpinner
import com.ssafy.hajo.plan.PlanDetailActivity
import com.ssafy.hajo.repository.dto.MemoDto
import com.ssafy.hajo.repository.dto.TaskDto
import com.ssafy.hajo.repository.dto.response.HomeResponse
import com.ssafy.hajo.settings.SettingsActivity
import com.ssafy.hajo.util.*
import java.util.*

class HomeFragment : Fragment(){

    private lateinit var binding: FragmentHomeBinding

    private lateinit var ctx: Context

    private var memoList: MutableList<MemoDto> = mutableListOf()

    private lateinit var memoAdapter: MemoAdapter

    private lateinit var todoAdapter: TodoAdapter

    private val homeViewModel : HomeViewModel by viewModels()

    lateinit var grandPlanStringList  :  MutableList<String>
    private var grandPlanSeqList = mutableListOf<Int>()

    private var fbClick = false
    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.getTodoList()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.fbAdd.setOnClickListener {
            onAddButtonClicked()
        }

        binding.fbPlan.setOnClickListener {
            val intent = Intent(ctx,PlanDetailActivity::class.java)
            intent.putExtra("planSeq",0)
            startActivity(intent)

        }

        binding.fbDiary.setOnClickListener {
            startActivity(Intent(requireContext(), DiaryHomeActivity::class.java))
        }

        binding.fbMemo.setOnClickListener {

            val memoBinding = DialogMemoBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(requireActivity(), R.style.MemoDialogTheme)
                .setView(memoBinding.root)
                .setPositiveButton("등록") { dialog, id ->
                    val memo = memoBinding.etDialogMemo.text.toString()
                    if (memo == "") {
                        Toast.makeText(ctx, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }else if(memo.length > 18){
                        Toast.makeText(ctx, "글자수는 18자 제한입니다", Toast.LENGTH_SHORT).show()
                    }else{
                        memoList.add(MemoDto(memo))
                        MemoPrefUtil.setListToSharedPreference(memoList)
                        initMemoAdapter()
                        dialog.dismiss()
                    }
                }
                .setNegativeButton("취소") { dialog, id ->
                    dialog.dismiss()

                }
            dialog.show()

        }

        initTodoAdapter()

        homeViewModel.todoList.observe(viewLifecycleOwner){
            if(homeViewModel.todoList != null){

                val list = homeViewModel.todoList.value!!
                todoAdapter.setList(list)

                grandPlanStringList = mutableListOf("전체")
                grandPlanSeqList.clear()

                list.forEach {
                    grandPlanStringList.add(it.grandplanTitle)
                    grandPlanSeqList.add(it.grandplanSeq.toInt())
                }

                if(list.size == 0){
                    binding.tvNothingTask.visibility = View.VISIBLE
                }else{
                    binding.tvNothingTask.visibility = View.GONE
                }
                initSpinnerAdapter()
            }
        }

        initMemoAdapter()

    }

    private fun initSpinnerAdapter(){
        val spinnerList = grandPlanStringList
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.item_home_spinner, spinnerList)
        binding.homeSpinner.adapter = spinnerAdapter
        binding.homeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //필터 코드
                when(spinnerList[position]){
                    "전체" -> {
                        val list = homeViewModel.todoList.value!!
                        todoAdapter.setList(list)
                    }
                    else -> {
                        val list = homeViewModel.todoList.value!!
                        val seq = grandPlanSeqList[position-1]
                        val todo = mutableListOf<HomeResponse>()
                        list.forEach {
                            if(it.grandplanSeq.toInt() == seq){
                                todo.add(it)
                                return@forEach
                            }
                        }

                        todoAdapter.setList(todo)
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }
    private fun initMemoAdapter() {
        memoList = MemoPrefUtil.getSharedPreferenceToList()
        binding.rvMemo.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        memoAdapter = MemoAdapter(memoList)
        memoAdapter.onRemoveCheckClick = object : MemoAdapter.OnRemoveCheckClick {
            override fun onCheckClick(position: Int, cb : CheckBox) {
                val builder = AlertDialog.Builder(ctx)
                builder.setMessage("메모를 정말 삭제할까요?")
                    .setPositiveButton("완료"){ dialog, id ->
                        memoList.removeAt(position)
                        MemoPrefUtil.setListToSharedPreference(memoList)
                        initMemoAdapter()
                        dialog.dismiss()
                    }
                    .setNegativeButton("취소"){dialog , id ->
                        cb.isChecked = false
                        dialog.dismiss()
                    }
                val alertDialog = builder.create()
                alertDialog.show()

            }
        }
        binding.rvMemo.adapter = memoAdapter

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initTodoAdapter() {

        val checkListener : (TaskDto, Boolean, CheckBox) -> Unit = {task, isChecked, checkBox ->
            if(isChecked){
                val builder = AlertDialog.Builder(ctx)
                builder.setMessage("태스크를 정말 완료하셨습니까?")
                    .setPositiveButton("완료"){ dialog, id ->
                        checkBox.paintFlags = checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        homeViewModel.completeTask(task.taskSeq)

                        dialog.dismiss()
                    }
                    .setNegativeButton("취소"){dialog , id ->
                        checkBox.isChecked = false
                        dialog.dismiss()
                    }
                val alertDialog = builder.create()
                alertDialog.show()
            }
        }

        val clickListener : (Long) -> Unit = { planSeq ->
            val intent = Intent(ctx,PlanDetailActivity::class.java)
            Log.d("HomeFragment", "initTodoAdapter: $planSeq")
            intent.putExtra("planSeq",planSeq.toInt())
            startActivity(intent)
        }

        todoAdapter = TodoAdapter(checkListener,clickListener){ smallPlanSeq ->
            val intent = Intent(ctx, TaskAddPopupActivity::class.java)

            intent.putExtra("smallPlanSeq",smallPlanSeq)
           startActivity(intent)


        }

        binding.rvHomeTodo.layoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        binding.rvHomeTodo.adapter = todoAdapter

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun onAddButtonClicked() {
        setAnimation()
        fbClick = !fbClick

    }

    private fun setAnimation() {
        if (!fbClick) {
            binding.fbDiary.startAnimation(
                AnimationUtils.loadAnimation(
                    ctx,
                    R.anim.floating_up_anim
                )
            )
            binding.fbPlan.startAnimation(
                AnimationUtils.loadAnimation(
                    ctx,
                    R.anim.floating_up_anim
                )
            )
            binding.fbMemo.startAnimation(
                AnimationUtils.loadAnimation(
                    ctx,
                    R.anim.floating_up_anim
                )
            )
            binding.fbAdd.startAnimation(
                AnimationUtils.loadAnimation(
                    ctx,
                    R.anim.floating_open_anim
                )
            )

        } else {
            binding.fbDiary.startAnimation(
                AnimationUtils.loadAnimation(
                    ctx,
                    R.anim.floating_down_anim
                )
            )
            binding.fbPlan.startAnimation(
                AnimationUtils.loadAnimation(
                    ctx,
                    R.anim.floating_down_anim
                )
            )
            binding.fbMemo.startAnimation(
                AnimationUtils.loadAnimation(
                    ctx,
                    R.anim.floating_down_anim
                )
            )
            binding.fbAdd.startAnimation(
                AnimationUtils.loadAnimation(
                    ctx,
                    R.anim.floating_close_anim
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "onResume: ")
        homeViewModel.getTodoList()
    }

}