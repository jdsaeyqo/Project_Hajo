package com.ssafy.hajo.board.boast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.repository.dao.BoardRepository
import com.ssafy.hajo.repository.dto.response.BoastMoreResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class BoastMoreViewModel : ViewModel() {

    private val _boastMoreList = MutableLiveData<MutableList<BoastMoreResponse>>()
    val boastMoreList: LiveData<MutableList<BoastMoreResponse>>
        get() = _boastMoreList

    private val boardRepository by lazy {
        BoardRepository()
    }
    val uid = GlobalApplication.userPrefs.getString("uid", "")
    val jwt = GlobalApplication.userPrefs.getString("jwt", "")

    fun getBoastMoreList(uid: String, type: String) {

        val hm = HashMap<String, String>()
        hm["uid"] = uid
        hm["type"] = type
        viewModelScope.launch(Dispatchers.Main) {
            val res = boardRepository.getBoastAll(jwt!!, hm)

            if (res.isSuccessful && res.body() != null) {
                Log.d("BoastMoreViewModel", "${res.body()}")
                val list = res.body()
                _boastMoreList.postValue(list)
            }
        }

    }

}