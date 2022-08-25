package com.ssafy.hajo.board.boast

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityBoardAddBinding
import com.ssafy.hajo.repository.dto.response.BoastDetailResponse
import com.ssafy.hajo.repository.dto.response.BoastMoreResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoastAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardAddBinding
    private val boastViewModel: BoastAddViewModel by viewModels()

    private lateinit var imageUri: Uri
    var modifyImageUri = ""
    private var WRITE_EXTERNAL_STORAGE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_add)

        val mode = intent?.extras?.getInt("mode")
        val boastDetail = intent?.extras?.get("boast")
        val boastMore = intent?.extras?.get("boastMore")

        var boastSeq = 0L

        Log.d("BoastAddActivity_공통", "$mode\n$boastDetail")


        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.vm = boastViewModel
        binding.lifecycleOwner = this

        if (boastDetail != null) {
            val boast = boastDetail as BoastDetailResponse
            Log.d("BoastAddActivity_공통", "$boast")
            boastSeq = boast.boastSeq
            boastViewModel.titleInputText.value = boast.boastTitle
            boastViewModel.contentInputText.value = boast.boastContent
            boastViewModel.updateImage("image")
            Glide.with(this).load(boast.boastImg).into(binding.ivAddImage)
            modifyImageUri = boast.boastImg

        }

        if (boastMore != null) {
            val boast = boastMore as BoastMoreResponse
            Log.d("BoastAddActivity_공통", "$boast")
            boastSeq = boast.boastSeq
            boastViewModel.titleInputText.value = boast.boastTitle
            boastViewModel.contentInputText.value = boast.boastContent
            boastViewModel.updateImage("image")
            Glide.with(this).load(boast.boastImg).into(binding.ivAddImage)
            modifyImageUri = boast.boastImg
        }

        Log.d("BoardAddActivity", "onCreate: ${boastViewModel.title}  ${boastViewModel.content} ")


        checkGalleryPermission()

        binding.ivAddImage.setOnClickListener {
            if (WRITE_EXTERNAL_STORAGE != 1) {
                checkGalleryPermission()
            } else {
                openGallery()
            }
        }

        boastViewModel.validate.observe(this) {
            if (boastViewModel.validate.value == false) {
                Toast.makeText(this, "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show()
            } else {
                if(::imageUri.isInitialized){
                    boastViewModel.updateFireBaseStorage(imageUri,mode,modifyImageUri)
                }else{
                    boastViewModel.updateImage(modifyImageUri)
                }

            }
        }

        boastViewModel.imageUri.observe(this) {

            if (it != "image") {
                boastViewModel.upload(mode!!, boastSeq)
            }

        }
        //뷰모델에서 서버와 통신 성공했을 시
        boastViewModel.result.observe(this) {
            if (boastViewModel.result.value != null) {
                if (boastViewModel.result.value!!) {
                    Toast.makeText(this, "게시 완료", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        //서버에 작업 중인 상황일 때
        boastViewModel.onProgress.observe(this) {
            if (boastViewModel.onProgress.value != null) {
                if (boastViewModel.onProgress.value!!) {
                    binding.pbAddImage.visibility = View.VISIBLE
                }

            }
        }

    }

    private fun openGallery() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)
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

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            imageUri = it.data?.data!!
            Log.d("imageUri", "$imageUri")

            binding.ivAddImage.setImageURI(imageUri)
            boastViewModel.updateImage("image")


        }
    }


}