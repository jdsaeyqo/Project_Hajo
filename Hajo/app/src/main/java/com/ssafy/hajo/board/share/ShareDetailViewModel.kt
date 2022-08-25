package com.ssafy.hajo.board.share

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.repository.dao.BoardRepository
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.response.ShareDetailResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class ShareDetailViewModel : ViewModel() {

    private val _shareDetail = MutableLiveData<ShareDetailResponse>()
    val shareDetail: LiveData<ShareDetailResponse>
        get() = _shareDetail

    private val boardRepository by lazy { BoardRepository() }
    private val userRepository: UserRepository by lazy {
        UserRepository()
    }

    private val uid = GlobalApplication.userPrefs.getString("uid", "")
    private val jwt = GlobalApplication.userPrefs.getString("jwt", "")

    fun getShareDetail(sharePlanSeq: Long) {
        //통신
        val req = HashMap<String, String>()
        req["uid"] = uid!!
        req["seq"] = sharePlanSeq.toString()
        viewModelScope.launch(Dispatchers.Main) {

            val res = boardRepository.getShareDetail(jwt!!, req)

            if (res.isSuccessful && res.body() != null) {
                val detail = res.body()
                Log.d("ShareDetailViewModel", "getShareDetail: $detail")
                _shareDetail.postValue(detail)
            }
        }


    }

    fun deleteShare(sharePlanSeq: Long) {
        Log.d("ShareDetailViewModel", "deleteShare:${sharePlanSeq}")
        viewModelScope.launch(Dispatchers.Main) {
            val res = boardRepository.deleteShare(jwt!!, sharePlanSeq)

            if (res.isSuccessful && res.body() != null) {
                Log.d("ShareDetailViewModel", "deleteShare:${res.body()}")
            } else {
                Log.d("ShareDetailViewModel", "deleteShare:${res.errorBody()?.string()}")
            }

        }

    }

    fun shareUpdateLike(shareSeq: Long, mode: Int) {
        val req = HashMap<String, String>()
        req["userUid"] = uid!!
        req["sharePlanSeq"] = shareSeq.toString()

        when (mode) {
            0 -> {
                viewModelScope.launch(Dispatchers.Main) {
                    val res = boardRepository.shareDownLike(jwt!!, req)

                    if (res.isSuccessful && res.body() != null) {
                        Log.d("ShareDetailViewModel", "shareDownLike: ${res.body()}")
                    }
                }
            }
            1 -> {
                viewModelScope.launch(Dispatchers.Main) {
                    val res = boardRepository.shareUpLike(jwt!!, req)

                    if (res.isSuccessful && res.body() != null) {
                        Log.d("ShareDetailViewModel", "shareUpLike: ${res.body()}")
                    }
                }
            }
        }
    }

    fun shareUpdateBmk(shareSeq: Long, mode: Int) {
        val req = HashMap<String, String>()
        req["userUid"] = uid!!
        req["sharePlanSeq"] = shareSeq.toString()

        when (mode) {
            0 -> {
                viewModelScope.launch(Dispatchers.Main) {
                    val res = boardRepository.downBookMark(jwt!!, req)

                    if (res.isSuccessful && res.body() != null) {
                        Log.d("ShareDetailViewModel", "downBookMark: ${res.body()}")
                    }
                }
            }
            1 -> {
                viewModelScope.launch(Dispatchers.Main) {
                    val res = boardRepository.updBookMark(jwt!!, req)

                    if (res.isSuccessful && res.body() != null) {
                        Log.d("ShareDetailViewModel", "updBookMark: ${res.body()}")
                    }
                }
            }
        }
    }

    fun reportShare(sharePlanSeq: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val hm = HashMap<String, String>()
            hm["userUid"] = uid!!
            hm["sharePlanSeq"] = sharePlanSeq.toString()
            val res = boardRepository.shareUpdateReport(jwt!!, hm)

            if (res.isSuccessful && res.body() != null) {
                Log.d("ShareDetailViewModel", "reportShare: ${res.body()}")
            }
        }
    }

    fun blockShare(boastSeq: Long) {
        if (boastSeq == -1L) {
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

    fun reportUser(wrtUid: String) {
        if (wrtUid == "") {
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

    fun blockUser(wrtUid: String) {
        Log.d("BoastViewModel", "blockUser: $wrtUid")
        if (wrtUid == "") {
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