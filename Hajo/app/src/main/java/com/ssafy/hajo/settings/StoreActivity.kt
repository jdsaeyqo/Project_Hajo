package com.ssafy.hajo.settings

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityStoreBinding
import com.ssafy.hajo.databinding.DialogMemoBinding
import com.ssafy.hajo.databinding.DialogStoreBinding
import com.ssafy.hajo.entity.Store
import com.ssafy.hajo.repository.dto.MemoDto
import com.ssafy.hajo.util.MemoPrefUtil

class StoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityStoreBinding
    private val storeViewModel : StoreViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val storeList = mutableListOf(
            Store("Old Page", "0", 200),
            Store("Night Sky", "1", 300),
            Store("Yellow", "2", 400),
            Store("Wall", "3", 500)
        )

        val adapter = StoreAdapter { store ->

            val storeBinding = DialogStoreBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(this)
                .setView(storeBinding.root)
                .setPositiveButton("등록") { dialog, id ->
                    val title = storeBinding.etDialogStore.text.toString()
                    if (title == "") {
                        Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }else if(title.length > 10){
                        Toast.makeText(this, "글자수는 10자 제한입니다", Toast.LENGTH_SHORT).show()
                    }else{
                        store.diaryTitle = title
                        Log.d("StoreActivity", "onCreate: $store")
                        storeViewModel.addDiary(store.diaryType,store.diaryTitle,store.price)
                        Toast.makeText(this,"구매가 완료 되었습니다",Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
                .setNegativeButton("취소") { dialog, id ->
                    dialog.dismiss()

                }
            dialog.show()



        }

        adapter.storeList = storeList
        binding.vpStore.adapter = adapter

        binding.circleIndicator.setViewPager(binding.vpStore)


    }
}