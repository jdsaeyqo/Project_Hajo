package com.ssafy.hajo.competition

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.entity.GrandPlan
import com.ssafy.hajo.repository.dao.CompetitionRepository
import com.ssafy.hajo.repository.dto.response.GrandPlanResponse
import com.ssafy.hajo.repository.dto.response.ProfileFormResponse
import com.ssafy.hajo.repository.dto.response.RequestFormResponse
import com.ssafy.hajo.repository.dto.response.UserResponse
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.GlobalApplication.Companion.userPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MatchFormViewModel: ViewModel() {
    private val competitionRepository by lazy {
        CompetitionRepository()
    }

    private var token : String
    private var userId : String

    private val requestList = mutableListOf<RequestFormResponse>()
    private val myRequestList = mutableListOf<RequestFormResponse>()
    private val profileList = mutableListOf<UserResponse>()
    private val planList = mutableListOf<GrandPlan>()

    private val _plans = MutableLiveData<MutableList<GrandPlan>>()
    val plans: LiveData<MutableList<GrandPlan>>
        get() = _plans

    private val _requestForms = MutableLiveData<MutableList<RequestFormResponse>>()
    val requestForms: LiveData<MutableList<RequestFormResponse>>
        get() = _requestForms

    private val _myRequestForms = MutableLiveData<MutableList<RequestFormResponse>>()
    val myRequestForms: LiveData<MutableList<RequestFormResponse>>
        get() = _myRequestForms

    private val _profileForms = MutableLiveData<MutableList<UserResponse>>()
    val profileForms: LiveData<MutableList<UserResponse>>
        get() = _profileForms

    init {
        token = userPrefs.getString("jwt","")!!
        userId = userPrefs.getString("uid","")!!


        viewModelScope.launch(Dispatchers.Main) {
            val res = competitionRepository.getGrandPlans(token,userId)
            Log.d("CompetitionViewModel", "getGrandPlans : ${res.body()}")
            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!
                _plans.value = body
            }
            else {
                Log.d("CompetitionViewModel", "getGrandPlans 실패")
            }
        }
    }

    fun deleteMyRequest(index : Int) {
        myRequestList.removeAt(index)
        _myRequestForms.value = myRequestList
    }

    fun deleteRequest(index : Int) {
        requestList.removeAt(index)
        _requestForms.value = requestList
    }

    fun deleteProfile(index : Int) {
        profileList.removeAt(index)
        _profileForms.value = profileList
    }

    fun getGrandPlans(uid: String) {
        // uid를 갖고 해당 유저의 경쟁전 가능한 대플랜 목록 가져오기
    }

    fun getRequests() {
        requestList.clear()
        runBlocking {
            val res = competitionRepository.getRequests(token)
            if(res.isSuccessful && res.body() != null) {
                res.body()!!.forEach {
                    if(it.matchUserUid != userId) {
                        requestList.add(it)
                    }
                }
                _requestForms.value = requestList
            }
        }
    }

    fun getMyRequests() {
        myRequestList.clear()
        runBlocking {
            val res = competitionRepository.getMyRequest(token, userId)
            if(res.isSuccessful && res.body() != null) {
                myRequestList.clear()
                myRequestList.addAll(res.body()!!)
                _myRequestForms.value = myRequestList
            }
        }
    }

    fun getProfiles() {

        profileList.clear()
        runBlocking {
            val res = competitionRepository.getProfiles(token)
            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!
                Log.d("MatchFormViewModel", "body: $body")
                body.forEach {
                    if(it.userUid != userId) {
                        profileList.add(it)
                    }
                }
                Log.d("MatchFormViewModel", "getProfiles: $profileList")
                _profileForms.value = profileList
            }
            else {
                Log.d("MatchFormViewModel", "경쟁전 프로필 불러오기 실패")
            }
        }
    }
}