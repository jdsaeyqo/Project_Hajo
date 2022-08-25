package com.ssafy.hajo.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.entity.Title
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.response.TitleResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TitleViewModel: ViewModel() {

    private val _titles = MutableLiveData<MutableList<TitleResponse>>()
    val titles : LiveData<MutableList<TitleResponse>>
        get() = _titles

    private val token = GlobalApplication.userPrefs.getString("jwt","")!!
    private val userId = GlobalApplication.userPrefs.getString("uid","")!!

    lateinit var title : TitleResponse

    private val userRepository by lazy {
        UserRepository()
    }

    fun changeTitle(before: Int, after: Int) {
        val beforeTitle = titles.value?.get(before)!!
        beforeTitle.equipped = false
        val afterTitle = titles.value?.get(after)!!
        afterTitle.equipped = true
        titles.value?.set(before, beforeTitle)
        titles.value?.set(after, afterTitle)
        title = afterTitle

        val hm = HashMap<String, Any>()
        hm["userUid"] = userId
        hm["titleSeq"] = title.titleSeq

        viewModelScope.launch(Dispatchers.IO) {
            val res = userRepository.updateUser(token, hm)
            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!
                if(body != null) {
                    Log.d("TitleViewModel", "changeTitle : $body")
                }
                else {
                    Log.d("TitleViewModel", "changeTitle 실패")
                }
            }
        }
    }

    fun getTitles() {
        runBlocking {
            val res = userRepository.getTitles(token, userId)
            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!
                body.forEach {
                    if(it.equipped) {
                        title = it
                    }
                }
                _titles.value = body
            }
        }
    }
}