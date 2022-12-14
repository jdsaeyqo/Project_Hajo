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
                        Log.e("LoginActivity", "????????? ?????? ???(?????? ??????)")
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Log.e("LoginActivity", "???????????? ?????? ???")
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Log.e("LoginActivity", "?????? ????????? ???????????? ?????? ????????? ??? ?????? ??????")
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Log.e("LoginActivity", "?????? ???????????? ??????")
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Log.e("LoginActivity", "???????????? ?????? scope ID")
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Log.e("LoginActivity", "????????? ???????????? ??????(android key hash)")
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Log.e("LoginActivity", "?????? ?????? ??????")
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Log.e("LoginActivity", "?????? ?????? ????????? ??????")
                    }
                    else -> { // Unknown
                        Log.e("LoginActivity", "?????? ?????? : ${error.message}")
                    }
                }
            } else if (token != null) {

                Log.d("LoginActivity", "token: $token")
                Toast.makeText(this, "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show()

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

        /** OAuthLoginCallback??? authenticate() ????????? ?????? ??? ??????????????? ??????????????? NidOAuthLoginButton ????????? ???????????? ????????? ???????????? ?????? ????????? ??? ????????????. */
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // ????????? ????????? ????????? ???????????? ??? ????????? ?????? ??????
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

    // firebase authentication ??????
    private var mAuth: FirebaseAuth? = null
    var mGoogleSignInClient: GoogleSignInClient? = null

    private fun initAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // default_web_client_id ?????? build ????????? values.xml ????????? ?????? ??????
            .requestIdToken(getString(R.string.default_web_client_id))      //??????????????? id??? ?????? ??????
            .requestServerAuthCode("305281391229-v3pk1ibluht8jn5muuvk78k8ko34d9pi.apps.googleusercontent.com")
            .requestEmail() // ?????? ??????: gmail
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        // Google?????? ???????????? signInIntent??? ???????????? ?????? ??????
        val signInIntent = mGoogleSignInClient!!.signInIntent

        //???????????? ????????? launch
        requestActivity.launch(signInIntent)
    }

    // ?????? ?????? ?????? ?????? ??? ?????? ??????
    private val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        Log.d(
            "GoogleLogin",
            "firebaseAuthWithGoogle: Activity.RESULT_OK): ${RESULT_OK}, activityResult.resultCode:${activityResult.resultCode}"
        )
        if (activityResult.resultCode == Activity.RESULT_OK) {

            // ?????? ?????? ??????
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

    // ?????? ?????? ????????? ?????? ?????? ??????
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {

            Log.d("GoogleLogin", "updateUI: ?????? : ${user.displayName}")
            Log.d("GoogleLogin", "updateUI: ???????????? uid: ${user.uid}")

            val uid = user.uid
            val name = user.displayName


            //uid??? ?????? ??????

            //?????? ?????? ?????? ????????? email?????? ?????? MainActivity???
            //val email = ???????????? ????????? ?????????
            //loginPrefs.edit().putString("email", email).apply()
            //val intent = Intent(this@LoginActivity, MainActivity::class.java)

            //????????? ????????? ?????? ?????????
            val intent = Intent(this@LoginActivity, GetInfoActivity::class.java)
            startActivity(intent)
            finish()


        } else {
            Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show()
        }
    }
}