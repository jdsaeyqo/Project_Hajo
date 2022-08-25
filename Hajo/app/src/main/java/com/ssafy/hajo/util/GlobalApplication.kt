package com.ssafy.hajo.util

import android.app.Application
import android.content.SharedPreferences
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.ssafy.hajo.R
import com.ssafy.hajo.entity.User

class GlobalApplication : Application() {

    companion object {
        lateinit var memoPrefs: SharedPreferences
        lateinit var userPrefs : SharedPreferences
        var User = User("testuid1", "닉네임 1", 1, "성실한", 40)
    }

    override fun onCreate() {
        super.onCreate()
        userPrefs = getSharedPreferences("userInfo", MODE_PRIVATE)
        memoPrefs = getSharedPreferences("Memo", MODE_PRIVATE)

        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        NaverIdLoginSDK.initialize(
            this,
            getString(R.string.naver_client_id),
            getString(R.string.naver_client_secret),
            getString(
                R.string.naver_client_name
            )
        )
    }
}