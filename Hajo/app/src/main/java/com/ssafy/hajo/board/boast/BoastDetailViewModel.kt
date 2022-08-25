package com.ssafy.hajo.board.boast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.repository.dao.BoardRepository
import com.ssafy.hajo.repository.dto.response.BoastDetailResponse
import com.ssafy.hajo.repository.dto.response.BoastMoreResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class BoastDetailViewModel : ViewModel() {

    private val _boastDetail = MutableLiveData<BoastDetailResponse>()
    val boastDetail: LiveData<BoastDetailResponse>
        get() = _boastDetail

    private val boardRepository by lazy {
        BoardRepository()
    }

    val jwt = GlobalApplication.userPrefs.getString("jwt","")
    val uid = GlobalApplication.userPrefs.getString("uid","")

    fun getBoastDetail(boastSeq : Long){
        //서버 통신

        viewModelScope.launch(Dispatchers.Main) {
            val request = HashMap<String,String>()
            request["uid"] = uid!!
            request["seq"] = boastSeq.toString()
            Log.d("BoastDetailViewModel", "request: $request ")
            val res = boardRepository.getBoastDetail(jwt!!,request)
            if(res.isSuccessful && res.body() != null){
                val list = res.body()
                Log.d("BoastDetailViewModel", "getBoastDetail: $list ")
                _boastDetail.postValue(list)
            }
        }

    }


}