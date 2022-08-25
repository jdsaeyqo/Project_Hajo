package com.ssafy.hajo.competition

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.repository.dao.CompetitionRepository
import com.ssafy.hajo.repository.dto.response.HistoryResponse
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.GlobalApplication.Companion.userPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HistoryViewModel: ViewModel() {
    private val historyList = mutableListOf<HistoryResponse>()

    private val _history = MutableLiveData<MutableList<HistoryResponse>>()
    val history: LiveData<MutableList<HistoryResponse>>
        get() = _history

    private var token : String
    private var userId : String

    private val competitionRepository by lazy {
        CompetitionRepository()
    }

    init {
        Log.d("HistoryViewModel", "HistoryViewModel init")
        token = userPrefs.getString("jwt","")!!
        userId = userPrefs.getString("uid","")!!

        // 서버로부터 전적을 불러온다
        runBlocking {
            val res = competitionRepository.getHistory(token, userId)

            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!
                body.forEach {
                    historyList.add(it)
                }
                _history.value = historyList
            }
            else {
                Log.d("HistoryViewModel", "전적 불러오기 실패")
            }
        }
    }
}