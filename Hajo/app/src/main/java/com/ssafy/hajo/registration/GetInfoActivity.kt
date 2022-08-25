package com.ssafy.hajo.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.hajo.MainActivity
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityGetInfoBinding
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.request.UserInfoRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityGetInfoBinding
    private val userRepository: UserRepository by lazy {
        UserRepository()
    }

    lateinit var uid : String
    lateinit var loginType : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slideAnim = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        binding.clIntro.startAnimation(slideAnim)


        val slideAnimSecond = AnimationUtils.loadAnimation(this, R.anim.side_slide_second)
        binding.clGetEmail.startAnimation(slideAnimSecond)

        uid = intent?.extras?.get("userUid").toString()
        loginType = intent?.extras?.get("loginType").toString()

        Log.d("로그인 정보", uid)
        Log.d("로그인 정보", loginType)

        binding.btnDone.setOnClickListener {


            if ( binding.etEmail.text.toString().trim() == "") {
                binding.tvEmailWarning.text = "빈 칸을 채워주세요"
                binding.tvEmailWarning.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (!binding.etEmail.text.toString().trim().contains(".") || !binding.etEmail.text.toString().trim().contains("@") ) {
                binding.tvEmailWarning.text = "이메일 형식이 잘못 되었습니다\n예시) hajo@naver.com"
                binding.tvEmailWarning.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if(binding.etNickName.text.toString().trim() == ""){
                binding.tvNicknameWarning.text = "빈 칸을 채워주세요"
                binding.tvNicknameWarning.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val email = binding.etEmail.text.toString().trim()
            val nickName = binding.etNickName.text.toString().trim()

            CoroutineScope(Dispatchers.Main).launch {

                val newUser = UserInfoRequest(userUid = uid, userEmail = email, userLogintype = loginType, userNickname = nickName)
                Log.d("SignUpTest", "new User : $newUser")
                val res = userRepository.signUp(newUser)
                if(res.isSuccessful && res.body()!= null){
                    Log.d("SignUpTest", "${res.body()}")

                    val result = res.body()!!

                    if(result["message"] == "signup success!!"){
                        val jwt = result["accessToken"]
                        val intent = Intent(this@GetInfoActivity,MainActivity::class.java)
                        intent.putExtra("userUid",uid)
                        intent.putExtra("jwt",jwt)
                        startActivity(intent)
                        finish()
                    }
                }else{
                    Log.d("SignUpTest", "${res.errorBody()?.string()}")
                }

            }



        }

    }
}