package com.ssafy.hajo.settings.quest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.repository.dao.QuestRepository
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.response.MyQuestResponse
import com.ssafy.hajo.repository.dto.response.QuestResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class QuestViewModel : ViewModel() {

    private val questRepository: QuestRepository by lazy { QuestRepository() }

    val jwt = GlobalApplication.userPrefs.getString("jwt", "")
    val uid = GlobalApplication.userPrefs.getString("uid", "")

    private val _questList = MutableLiveData<MutableList<QuestResponse>>()
    val questList: LiveData<MutableList<QuestResponse>>
        get() = _questList

    private val _myQuestList = MutableLiveData<MutableList<MyQuestResponse>>()
    val myQuestList: LiveData<MutableList<MyQuestResponse>>
        get() = _myQuestList



    fun getQuestList() {

        viewModelScope.launch(Dispatchers.Main) {

            val res = questRepository.getQuestList(jwt!!)

            if (res.isSuccessful && res.body() != null) {
                Log.d("QuestViewModel", "getQuestList: ${res.body()}")
                val list = res.body()
                _questList.postValue(list)
            }
        }
    }

    fun getMyQuest() {
        viewModelScope.launch(Dispatchers.Main) {
            Log.d("QuestViewModel", "jwt: $jwt uid : $uid")


            val res = questRepository.getMyQuest(jwt!!, uid!!)
            if (res.isSuccessful && res.body() != null) {
                Log.d("QuestViewModel", "getMyQuest: ${res.body()}")
                val list = res.body()
                _myQuestList.postValue(list)
            }else{
                Log.d("QuestViewModel", "getMyQuest: ${res.errorBody()?.string()}")
            }
        }
    }

    fun getReward(userArchiveSeq: Long) {
        val request = HashMap<String, Long>()
        request["userArchiveSeq"] = userArchiveSeq
        viewModelScope.launch(Dispatchers.Main) {

            val res = questRepository.getReward(jwt!!, request)
            if (res.isSuccessful && res.body() != null) {
                Log.d("QuestViewModel", "getQuestList: ${res.body()}")

            }
        }
    }

}