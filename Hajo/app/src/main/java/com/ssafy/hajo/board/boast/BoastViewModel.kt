package com.ssafy.hajo.board.boast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.repository.dao.BoardRepository
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.response.BoastHomeResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoastViewModel : ViewModel() {

    private val _boastPopularList = MutableLiveData<ArrayList<BoastHomeResponse>>()
    val boastPopularList: LiveData<ArrayList<BoastHomeResponse>>
        get() = _boastPopularList

    private val _boastRecentList = MutableLiveData<ArrayList<BoastHomeResponse>>()
    val boastRecentList: LiveData<ArrayList<BoastHomeResponse>>
        get() = _boastRecentList

    private val _boastLikeList = MutableLiveData<ArrayList<BoastHomeResponse>>()
    val boastLikeList: LiveData<ArrayList<BoastHomeResponse>>
        get() = _boastLikeList

    private val _boastMyList = MutableLiveData<ArrayList<BoastHomeResponse>>()
    val boastMyList: LiveData<ArrayList<BoastHomeResponse>>
        get() = _boastMyList

    private val jwt = GlobalApplication.userPrefs.getString("jwt", "")
    private val uid = GlobalApplication.userPrefs.getString("uid", "")

    private val boardRepository by lazy {
        BoardRepository()
    }

    private val userRepository by lazy {
        UserRepository()
    }

    fun getBoastTop5() {
        viewModelScope.launch(Dispatchers.Main) {
            val res = boardRepository.getBoastTop5(jwt!!, uid!!)
            if (res.isSuccessful && res.body() != null) {
                Log.d("BoastViewModel", "getBoastTop5: ${res.body()}")

                val list = res.body()!!
                _boastPopularList.postValue(list.popularList)
                _boastRecentList.postValue(list.recentList)
                _boastLikeList.postValue(list.likeList)
                _boastMyList.postValue(list.myList)

            }
        }
    }


    fun deleteBoast(boastSeq: Long) {

        viewModelScope.launch(Dispatchers.Main) {
            val res = boardRepository.deleteBoast(jwt!!, boastSeq)

            if (res.isSuccessful && res.body() != null) {
                Log.d("BoastMoreViewModel", "deleteBoast: ${res.body()}")
            }
        }

    }

    fun boastUpdateLike(boastSeq: Long, mode : Int) {
        val req = HashMap<String, String>()
        req["userUid"] = uid!!
        req["boastSeq"] = boastSeq.toString()

        when(mode) {
            0 -> {
                viewModelScope.launch(Dispatchers.Main) {
                    val res = boardRepository.boastDownLike(jwt!!, req)

                    if (res.isSuccessful && res.body() != null) {
                        Log.d("BoastMoreViewModel", "boastDownLike: ${res.body()}")
                    }
                }
            }
            1 -> {
                viewModelScope.launch(Dispatchers.Main) {
                    val res = boardRepository.boastUpLike(jwt!!, req)

                    if (res.isSuccessful && res.body() != null) {
                        Log.d("BoastMoreViewModel", "boastUpLike: ${res.body()}")
                    }
                }
            }
        }

    }

    fun reportBoast(boastSeq: Long) {
        if(boastSeq == -1L){
            return
        }
        val req = HashMap<String, String>()
        req["userUid"] = uid!!
        req["boastSeq"] = boastSeq.toString()

        viewModelScope.launch(Dispatchers.Main) {
            val res = boardRepository.boastUpdateReport(jwt!!, req)

            if (res.isSuccessful && res.body() != null) {
                Log.d("BoastMoreViewModel", "reportBoast: ${res.body()}")
            }
        }
    }
    fun blockBoast(boastSeq: Long) {
        if(boastSeq == -1L){
            return
        }
//        val req = HashMap<String, String>()
//        req["userUid"] = uid!!
//        req["boastSeq"] = boastSeq.toString()
//
//        viewModelScope.launch(Dispatchers.Main) {
//            val res = boardRepository.boastUpdateReport(jwt!!, req)
//
//            if (res.isSuccessful && res.body() != null) {
//                Log.d("BoastMoreViewModel", "reportBoast: ${res.body()}")
//            }
//        }
    }
    fun reportUser(wrtUid : String) {
        if(wrtUid == ""){
            return
        }
//        val req = HashMap<String, String>()
//        req["userUid"] = uid!!
//        req["boastSeq"] = boastSeq.toString()
//
//        viewModelScope.launch(Dispatchers.Main) {
//            val res = boardRepository.boastUpdateReport(jwt!!, req)
//
//            if (res.isSuccessful && res.body() != null) {
//                Log.d("BoastMoreViewModel", "reportBoast: ${res.body()}")
//            }
//        }
    }
    fun blockUser(wrtUid : String) {
        Log.d("BoastViewModel", "blockUser: $wrtUid")
        if(wrtUid == ""){
            return
        }
        val req = HashMap<String, String>()
        req["blockUid"] = wrtUid

        viewModelScope.launch(Dispatchers.Main) {
            val res = userRepository.blockUser(jwt!!, req)

            if (res.isSuccessful && res.body() != null) {
                Log.d("BoastViewModel", "blockUser: ${res.body()}")
            }
        }
    }

}