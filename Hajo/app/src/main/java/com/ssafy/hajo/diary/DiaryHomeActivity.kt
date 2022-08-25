package com.ssafy.hajo.diary

import DiaryViewModel
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityDiaryHomeBinding
import com.ssafy.hajo.plan.PlanDetailViewModel

class DiaryHomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityDiaryHomeBinding
    private lateinit var viewModel: DiaryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.diary_left_to_righit_anim, R.anim.none)

        binding = ActivityDiaryHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DiaryViewModel::class.java)

        initObserve()
        initUI()


    }

    private fun initObserve() {
        // 다이어리 리스트 가져오면 적용하기
        viewModel.diaryList.observe(this) { //todo paging? 5개 제한? recycler?
            var diary : List<ImageButton> = mutableListOf(binding.btnDiary1, binding.btnDiary2, binding.btnDiary3,
            binding.btnDiary4, binding.btnDiary5)
            var diaryTitle : List<TextView> = mutableListOf(binding.tvDiary1, binding.tvDiary2, binding.tvDiary3,
                binding.tvDiary4, binding.tvDiary5)

            Log.d("diaryActivity", "obseve viewModel.diaryList ${viewModel.diaryList.value}")
            val diarys = viewModel.diaryList.value!!


            diarys.forEachIndexed { index, value ->
                if (value.diaryTitle != null && value.diaryTitle != "") {
                    var charArr = value.diaryTitle.toCharArray()
                    Log.d("diaryActivity", "diaryTitle to charr ${charArr}")
                    var textArr: String = ""
                    charArr.forEachIndexed { index, c ->
                        if (index == 0) {
                            textArr = textArr + c.toString()
                        } else {
                            textArr = textArr + "\n" + c.toString()
                        }
                    }
                    if (index <= 4) {
                        Log.d("diaryActivity", "diaryTitle vertical ${value.diaryTitle}")
                        diaryTitle[index].text = textArr
                    }
                }
                if (index <= 4) {

                    diary[index].setOnClickListener {
                        Log.d("diaryHome", "to Diary ${value.diarySeq} type ${value.diaryType}")
                        startActivity(Intent(this, DiaryActivity::class.java).apply {
                            val seq: Int = value.diarySeq
                            putExtra("diarySeq", seq)
                            putExtra("diaryType", if(value.diaryType == null) "0" else value.diaryType)
                        })
                    }
                }
                if(index <= 4) {

                    diary[index].setOnLongClickListener {
                        Log.d("diaryHome", "delete diary ${value.diarySeq}")
                        val builder = AlertDialog.Builder(this)
                            .setTitle("다이어리 삭제")
                            .setMessage("정말 삭제하시겠습니까?")
                            .setPositiveButton("확인",
                                DialogInterface.OnClickListener { dialog, which ->
                                    viewModel.deleteDiary(value.diarySeq)
                                })
                            .setNegativeButton("취소",
                                DialogInterface.OnClickListener { dialog, which ->
                                    Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
                                })

                        builder.show()

                        return@setOnLongClickListener true
                    }
                }

            }

        }

        viewModel.addDiary.observe(this) {
            if(viewModel.addDiary.value == 200) {
                viewModel.getUserDiary()
            } else {
                Toast.makeText(this, "다이어리를 띄우지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.diaryDelete.observe(this) {
            if(viewModel.diaryDelete.value == 200) {
                Log.d("diaryHome", "diaryDelete.observe 성공")
                viewModel.getUserDiary()
            } else {
                Toast.makeText(this, "다이어리를 삭제하지 못했습니다. ", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun initUI() {
        // 화면에 다이어리 리스트 띄우기
        viewModel.getUserDiary()

    }
}