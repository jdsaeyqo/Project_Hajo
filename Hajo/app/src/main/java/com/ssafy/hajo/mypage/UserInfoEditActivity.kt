package com.ssafy.hajo.mypage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityUserInfoEditBinding
import com.ssafy.hajo.entity.Theme
import com.ssafy.hajo.entity.Title
import com.ssafy.hajo.registration.UserViewModel
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.response.TitleResponse
import com.ssafy.hajo.repository.dto.response.UserResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserInfoEditActivity : AppCompatActivity() {
    private val binding: ActivityUserInfoEditBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_user_info_edit)
    }
    private val userRepository: UserRepository by lazy {
        UserRepository()
    }

    private val userViewModel by viewModels<UserViewModel>()
    private val titleViewModel by viewModels<TitleViewModel>()
    lateinit var user: UserResponse

    private lateinit var imageUri: Uri
    private lateinit var userImageUrl: String
    private var WRITE_EXTERNAL_STORAGE = 0

    private lateinit var token : String
    private lateinit var userId : String

    private val userHashMap = HashMap<String, Any>()

    private lateinit var selectedTitle : TitleResponse
    private lateinit var selectedSkin : Theme
    val RESULT_TITLE_OK = 3000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_edit)

        // 화면이 위로 올라오는 Animation 효과 적용
        overridePendingTransition(R.anim.translate_left, R.anim.none)

        //userViewModel.setUser(UserResponse("testuid1","유저닉네임","유저닉네임",91,"성실한",3,4,4,3, 3,"상태메시지"))

        initView()
    }


    override fun finish() {
        super.finish()
        // 화면이 아래로 내려가는 Animation 효과 적용
        overridePendingTransition(R.anim.none,R.anim.translate_right)
    }

    fun initView() {
        //user = Gson().fromJson(intent.getStringExtra("user"), UserResponse::class.java)
//        this.userViewModel.getUser("testuid1")

        token = GlobalApplication.userPrefs.getString("jwt","")!!
        userId = GlobalApplication.userPrefs.getString("uid","")!!


        this@UserInfoEditActivity.userViewModel.getUserInfo()
        userImageUrl = this@UserInfoEditActivity.userViewModel.user.value!!.userImg

        checkGalleryPermission()

        binding.apply {

            Glide.with(this@UserInfoEditActivity)
                .load(this@UserInfoEditActivity.userViewModel.user.value!!.userImg).fitCenter()
                .into(ivUserImage)

            ivBack.setOnClickListener {
                finish()
            }

            tvSave.setOnClickListener {
                // 저장 버튼 클릭 리스너
                // 서버 통신
                runBlocking {
                    userHashMap["userImg"] = userImageUrl
                    userHashMap["userUid"] = userId
                    userHashMap["userNickname"] = binding.etName.text.toString()
                    userHashMap["userComment"] = binding.etMessageContent.text.toString()
                    Log.d("UserInfoEditActivity", "정보 수정 : $userHashMap")
                    val res = userRepository.updateUser(token, userHashMap)
                    if(res.isSuccessful && res.body() != null) {
                        val body = res.body()!!
                        if(body != null) {
                            Log.d("UserInfoEditActivity", "updateUser : $body")
                        }
                        else {
                            Log.d("UserInfoEditActivity", "updateUser : 실패")
                        }
                    }
                }
                finish()
            }

            cvTheme.setOnClickListener {
                startActivity(Intent(this@UserInfoEditActivity, ThemeEditActivity::class.java))
            }

            cvTitle.setOnClickListener {
                val intent = Intent(this@UserInfoEditActivity, TitleEditActivity::class.java)
                resultLauncher.launch(intent)
            }

            ivEditUserPicture.setOnClickListener {
                openGallery()
            }

            userViewModel = this@UserInfoEditActivity.userViewModel
            lifecycleOwner = this@UserInfoEditActivity
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

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            imageUri = it.data?.data!!
            Log.d("imageUri", "$imageUri")
            binding.ivUserImage.setImageURI(imageUri)
            updateFireBaseStorage(imageUri)
        }
        else if(it.resultCode == RESULT_TITLE_OK) {
            val str = it.data?.getStringExtra("title")
            selectedTitle = Gson().fromJson(str, TitleResponse::class.java)
            this@UserInfoEditActivity.userViewModel.user.value!!.titleName = selectedTitle.titleName
            Log.d("TAG", ": $selectedTitle")
        }
    }

    fun updateFireBaseStorage(imageUrl : Uri) {
        val storageRef = FirebaseStorage.getInstance().reference.child("UserImage")
                .child(this@UserInfoEditActivity.userViewModel.user.value!!.userUid)


        storageRef.putFile(imageUrl)
            .continueWithTask {
                return@continueWithTask storageRef.downloadUrl
            }.addOnSuccessListener { u ->
                userImageUrl = u.toString()
                Log.d("TaskAddViewModel", "taskAdd: $u ")
            }
    }
}