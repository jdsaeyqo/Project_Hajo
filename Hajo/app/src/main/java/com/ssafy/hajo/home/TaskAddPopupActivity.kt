package com.ssafy.hajo.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ssafy.hajo.databinding.PopupTaskDetailBinding
import com.ssafy.hajo.repository.dto.request.TaskAddRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskAddPopupActivity : AppCompatActivity() {

    lateinit var binding: PopupTaskDetailBinding

    private lateinit var imageUri: Uri
    private var WRITE_EXTERNAL_STORAGE = 0

    private val homeViewModel: HomeViewModel by viewModels()

    lateinit var title : String
    lateinit var memo : String
    lateinit var alarm : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PopupTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dm = applicationContext.resources.displayMetrics
        val width = dm.widthPixels * 0.85
        val height = dm.heightPixels * 0.85

        window!!.attributes.width = width.toInt()
        window!!.attributes.height = height.toInt()


        val smallPlanSeq = intent?.extras?.getLong("smallPlanSeq")

        checkGalleryPermission()

        homeViewModel.imageUri.observe(this){
            if(homeViewModel.imageUri.value != null){
                homeViewModel.taskAdd(
                    TaskAddRequest(smallPlanSeq!!, title, memo, homeViewModel.imageUri.value!!, alarm)

                )
                finish()

            }
        }

        binding.tvCancle.setOnClickListener {
            finish()
        }

        binding.tvSave.setOnClickListener {

            title = binding.etContent.text.toString()
            memo = binding.etMemo.text.toString()
            alarm = binding.tvAlram.text.toString()

            if (title == "" || memo == "" || alarm == "" ) {
                Toast.makeText(this, "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!::imageUri.isInitialized){
                homeViewModel.updateImage("")
                Toast.makeText(this,"완료되었습니다",Toast.LENGTH_LONG).show()
            }else{
                homeViewModel.updateFireBaseStorage(imageUri)
                Toast.makeText(this,"잠시만 기다려 주세요",Toast.LENGTH_LONG).show()
            }

        }

        binding.imgPicture.setOnClickListener {
            if (WRITE_EXTERNAL_STORAGE != 1) {
                checkGalleryPermission()
            } else {
                openGallery()
            }
        }

    }

    private fun checkGalleryPermission() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = TedPermission.create()

                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDeniedMessage("권한을 허용해주세요.")
                .check()

            if (result.isGranted) {
                WRITE_EXTERNAL_STORAGE = 1
            }
        }
    }

    private fun openGallery() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_OUTSIDE) {
            return false
        }
        return true

    }


    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            imageUri = it.data?.data!!
            Log.d("imageUri", "$imageUri")
            binding.imgPicture.setImageURI(imageUri)

        }
    }


}


