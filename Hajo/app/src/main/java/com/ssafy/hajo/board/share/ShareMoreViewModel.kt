package com.ssafy.hajo.board.share

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.repository.dao.BoardRepository
import com.ssafy.hajo.repository.dto.response.ShareResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShareMoreViewModel : ViewModel() {

    private val _shareBookMarkList = MutableLiveData<ArrayList<ShareResponse>>()
    val shareBookMareList: LiveData<ArrayList<ShareResponse>>
        get() = _shareBookMarkList

    private val boardRepository by lazy { BoardRepository() }

    val uid = GlobalApplication.userPrefs.getString("uid","")
    val jwt = GlobalApplication.userPrefs.getString("jwt","")

    fun getShareBookMarkList(){
        // 서버에서 북마크 가져오기

        val req = HashMap<String,String>()
        req["uid"] = uid!!
        req["type"] = "bmk"

        viewModelScope.launch(Dispatchers.Main) {

            val res = boardRepository.getShareMore(jwt!!,req)

            if(res.isSuccessful && res.body() != null){
                val list = res.body()
                _shareBookMarkList.postValue(list)
                Log.d("ShareMoreViewModel", "getShareBookMarkList: $list")
            }else{
                Log.d("ShareMoreViewModel", "getShareBookMarkList: ${res.errorBody()?.string()}")
            }

        }



    }
}