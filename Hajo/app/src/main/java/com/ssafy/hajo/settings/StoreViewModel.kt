package com.ssafy.hajo.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ssafy.hajo.repository.dao.DiaryRepository
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class StoreViewModel : ViewModel() {

    private val diaryRepository by lazy { DiaryRepository() }

    fun addDiary(diaryType: String, diaryTitle: String, price : Int) {
        Log.d("diaryViewmodel","addDiary 시작")

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("diaryViewmodel", "addDiaryCoroutineScope 시작")
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!

            var hash = HashMap<String, String>()
            hash.set("uid", userId)
            hash.set("diaryType", diaryType)
            hash.set("diaryTitle", diaryTitle)
            hash.set("price", price.toString())

            val res: Response<Int>
            withContext(Dispatchers.IO) {
                Log.d("diaryViewmodel", "withContext addDiary  시작")

                Log.d("diaryViewmodel", "서버에서 addDiary 위한 통신 시작 diary ${hash}")
                res = diaryRepository.addDiary(token, hash) // todo user 바꾸기
                Log.d("diaryViewmodel", "서버에서 addDiary 위한 통신 끝")

                Log.d("diaryViewmodel", "addDiary 반환 값 ${res.body()}")

                Log.d("diaryViewmodel", "withContext addDiary  끝")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("diaryViewmodel", "addDiary 성공 ${res.body()}")

                } else {
                    Log.d("diaryViewmodel", "addDiary 실패 또는 없음 ${res.body()}")

                }

                Log.d("diaryViewmodel", "addDiary CoroutineScope 끝")

            }
        }
        Log.d("diaryViewmodel","addDiary 끝")

    }
}