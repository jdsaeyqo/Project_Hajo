package com.ssafy.hajo.registration

import android.content.Intent
import android.util.Log
import com.ssafy.hajo.MainActivity
import com.ssafy.hajo.network.api.GoogleLoginApi
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.request.LoginGoogleRequestModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoogleLoginRepository {

    private val getAccessTokenBaseUrl = "https://www.googleapis.com"

    private val userRepository: UserRepository by lazy {
        UserRepository()
    }

    var tok = ""
    fun getAccessToken(authCode: String): String {



        Log.d("getAccessToken", "returntok: $tok")
        return tok

    }
}