package com.ssafy.hajo.registration

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.R
import com.ssafy.hajo.MainActivity
import com.ssafy.hajo.databinding.ActivityLoginBinding
import com.ssafy.hajo.network.RegistrationService
import com.ssafy.hajo.network.api.GoogleLoginApi
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.request.LoginGoogleRequestModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    private val userRepository: UserRepository by lazy {
        UserRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var keyHash = Utility.getKeyHash(this)
        Log.d("tttt", "onCreate: $keyHash")

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Log.e("LoginActivity", "접근이 거부 됨(동의 취소)")
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Log.e("LoginActivity", "유효하지 않은 앱")
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Log.e("LoginActivity", "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Log.e("LoginActivity", "요청 파라미터 오류")
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Log.e("LoginActivity", "유효하지 않은 scope ID")
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Log.e("LoginActivity", "설정이 올바르지 않음(android key hash)")
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Log.e("LoginActivity", "서버 내부 에러")
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Log.e("LoginActivity", "앱이 요청 권한이 없음")
                    }
                    else -> { // Unknown
                        Log.e("LoginActivity", "기타 에러 : ${error.message}")
                    }
                }
            } else if (token != null) {

                Log.d("LoginActivity", "token: $token")
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()

                val hm = HashMap<String, String>()
                hm["access_token"] = token.accessToken
                hm["login_type"] = "K"


                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("TokenTest", "onCreate: ss")
                    Log.d("TokenTest", "onCreate: $hm")
                    val res = userRepository.socialLogin(hm)

                    if (res.isSuccessful && res.body() != null) {
                        Log.d("TokenTest", "onCreate: ${res.body()}")
                        val result = res.body()!!
                        val message = result["message"]
                        if (message == "success") {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra("jwt",result["accessToken"])
                                intent.putExtra("userUid",result["uid"])
                            startActivity(intent)
                            finish()

                        } else {
                            val intent = Intent(this@LoginActivity, GetInfoActivity::class.java)
                            intent.putExtra("userUid", result["uid"])
                            intent.putExtra("loginType", result["loginType"])
                            startActivity(intent)
                            finish()

                        }
                    }
                }



            }
        }
        binding.btnKakaoLogin.setOnClickListener {

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        binding.btnNaverLogin.setOnClickListener {
            naverLogin()
        }

        binding.btnGoogleLogin.setOnClickListener {
            initAuth()
        }
    }

    private fun naverLogin() {
        var naverToken: String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {

            override fun onSuccess(response: NidProfileResponse) {

            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.e(
                    "LoginActivity", "errorCode: ${errorCode}\n" +
                            "errorDescription: ${errorDescription}"
                )
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        /** OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다. */
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

                Log.d("LoginActivity", "token: $naverToken")

                val hm = HashMap<String, String>()
                hm["access_token"] = naverToken.toString()
                hm["login_type"] = "N"


                CoroutineScope(Dispatchers.Main).launch {

                    Log.d("TokenTest", "onCreate: $hm")
                    val res = userRepository.socialLogin(hm)

                    if (res.isSuccessful && res.body() != null) {
                        Log.d("TokenTest", "onCreate: ${res.body()}")
                        val result = res.body()!!
                        val message = result["message"]
                        if (message == "success") {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("jwt",result["accessToken"])
                            intent.putExtra("userUid",result["uid"])
                            startActivity(intent)
                            finish()

                        } else {
                            val intent = Intent(this@LoginActivity, GetInfoActivity::class.java)
                            intent.putExtra("userUid", result["uid"])
                            intent.putExtra("loginType", result["loginType"])
                            startActivity(intent)
                            finish()

                        }

                    }
                }

            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.e(
                    "LoginActivity", "errorCode: ${errorCode}\n" +
                            "errorDescription: ${errorDescription}"
                )
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

    // firebase authentication 관련
    private var mAuth: FirebaseAuth? = null
    var mGoogleSignInClient: GoogleSignInClient? = null

    private fun initAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // default_web_client_id 값은 build 타임에 values.xml 파일에 자동 생성
            .requestIdToken(getString(R.string.default_web_client_id))      //클라이언트 id로 토큰 요청
            .requestServerAuthCode("305281391229-v3pk1ibluht8jn5muuvk78k8ko34d9pi.apps.googleusercontent.com")
            .requestEmail() // 인증 방식: gmail
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        // Google에서 제공되는 signInIntent를 이용해서 인증 시도
        val signInIntent = mGoogleSignInClient!!.signInIntent

        //콜백함수 부르며 launch
        requestActivity.launch(signInIntent)
    }

    // 구글 인증 결과 획득 후 동작 처리
    private val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        Log.d(
            "GoogleLogin",
            "firebaseAuthWithGoogle: Activity.RESULT_OK): ${RESULT_OK}, activityResult.resultCode:${activityResult.resultCode}"
        )
        if (activityResult.resultCode == Activity.RESULT_OK) {

            // 인증 결과 획득
            val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)

            try {
                val account = task.getResult(ApiException::class.java)

                Log.d("GoogleLogin", "firebaseAuthWithGoogle: account: ${account}")
                Log.d("serverAuthCode", "${account.serverAuthCode}")
                val token = account.serverAuthCode

                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("GoogleLoginRepository", "token: $token")
                    val res = GoogleLoginApi.loginRetrofit("https://www.googleapis.com").getAccessToken(
                        LoginGoogleRequestModel(
                            "authorization_code",
                            client_id = "305281391229-v3pk1ibluht8jn5muuvk78k8ko34d9pi.apps.googleusercontent.com",
                            client_secret = "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",
                            token!!
                        )
                    )

                    if (res.isSuccessful && res.body() != null) {
                        val accessToken = res.body()!!.access_token
                        Log.d("GoogleLoginRepository", "accessToken: $accessToken")
                        val hm = HashMap<String, String>()
                        hm["access_token"] = accessToken
                        hm["login_type"] = "G"

                        CoroutineScope(Dispatchers.Main).launch {

                            Log.d("GoogleLoginRepository", "onCreate: $hm")
                            val result = userRepository.socialLogin(hm)

                            if (result.isSuccessful && result.body() != null) {

                                val response = result.body()!!
                                Log.d("GoogleLoginRepository", "response: $response")
                                val message = response["message"]
                                if (message == "success") {
                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                      intent.putExtra("jwt",response["accessToken"])
                                      intent.putExtra("userUid",response["uid"])
                                    startActivity(intent)
                                    finish()

                                } else {
                                    val intent =
                                        Intent(this@LoginActivity, GetInfoActivity::class.java)
                                    intent.putExtra("userUid", response["uid"])
                                    intent.putExtra("loginType", response["loginType"])
                                    startActivity(intent)
                                    finish()

                                }
                            }
                        }

                    }


                }





//                firebaseAuthWithGoogle(account!!.idToken)
            } catch (e: ApiException) {
                Log.w("GoogleLogin", "google sign in failed: ", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        Log.d("GoogleLogin", "firebaseAuthWithGoogle: idToken: ${idToken}")
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth!!.currentUser

//                    updateUI(user)

                } else {
//                    updateUI(null)
                }
            }
    }

    // 인증 성공 여부에 따른 화면 처리
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {

            Log.d("GoogleLogin", "updateUI: 이름 : ${user.displayName}")
            Log.d("GoogleLogin", "updateUI: 사용자의 uid: ${user.uid}")

            val uid = user.uid
            val name = user.displayName


            //uid로 중복 확인

            //이미 가입 되어 있으면 email정보 받고 MainActivity로
            //val email = 서버에서 받아온 이메일
            //loginPrefs.edit().putString("email", email).apply()
            //val intent = Intent(this@LoginActivity, MainActivity::class.java)

            //아니면 이메일 입력 창으로
            val intent = Intent(this@LoginActivity, GetInfoActivity::class.java)
            startActivity(intent)
            finish()


        } else {
            Toast.makeText(this, "인증실패", Toast.LENGTH_SHORT).show()
        }
    }
}