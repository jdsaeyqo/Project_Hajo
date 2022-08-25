package com.ssafy.hajo.board.share

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.repository.dao.BoardRepository
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.response.ShareResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShareBoardViewModel : ViewModel() {

    val uid = GlobalApplication.userPrefs.getString("uid","")
    val jwt = GlobalApplication.userPrefs.getString("jwt","")

    private val boardRepository : BoardRepository by lazy {
        BoardRepository()
    }

    private val _sharePopularList = MutableLiveData<ArrayList<ShareResponse>>()
    val sharePopularList: LiveData<ArrayList<ShareResponse>>
        get() = _sharePopularList

    private val _shareRecentList = MutableLiveData<ArrayList<ShareResponse>>()
    val shareRecentList: LiveData<ArrayList<ShareResponse>>
        get() = _shareRecentList

    private val _shareLikeList = MutableLiveData<ArrayList<ShareResponse>>()
    val shareLikeList: LiveData<ArrayList<ShareResponse>>
        get() = _shareLikeList

    private val _shareMyList = MutableLiveData<ArrayList<ShareResponse>>()
    val shareMyList: LiveData<ArrayList<ShareResponse>>
        get() = _shareMyList

    fun getShareTop5() {
        Log.d("ShareBoardViewModel", "first: $uid")
        viewModelScope.launch(Dispatchers.Main) {
            Log.d("ShareBoardViewModel", "second: ")
            val res = boardRepository.getShareTop5(jwt!!, uid!!)
            if (res.isSuccessful && res.body() != null) {
                Log.d("ShareBoardViewModel", "getShareTop5: ${res.body()}")

                val list = res.body()!!
                _sharePopularList.postValue(list.popularList)
                _shareRecentList.postValue(list.recentList)
                _shareLikeList.postValue(list.likeList)
                _shareMyList.postValue(list.myList)

            }else{
                Log.d("BoastViewModel", "err: ${res.errorBody()?.string()} ")
            }
        }
    }

}