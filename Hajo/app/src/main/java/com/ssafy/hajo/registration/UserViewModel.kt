package com.ssafy.hajo.registration

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.ssafy.hajo.entity.User
import com.ssafy.hajo.entity.UserTask
import com.ssafy.hajo.repository.dao.CompetitionRepository
import com.ssafy.hajo.repository.dao.PlanDetailRepository
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.UserDto
import com.ssafy.hajo.repository.dto.response.GrandPlanResponse
import com.ssafy.hajo.repository.dto.response.UserResponse
import com.ssafy.hajo.repository.dto.response.UserTaskResponse
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.GlobalApplication.Companion.userPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

class UserViewModel : ViewModel() {
    private val userRepository: UserRepository by lazy {
        UserRepository()
    }

    private val competitionRepository by lazy {
        CompetitionRepository()
    }

    private val planDetailRepository by lazy {
        PlanDetailRepository()
    }

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse>
        get() = _user

    private val _winRate = MutableLiveData<Int>()
    val winRate: LiveData<Int>
        get() = _winRate

    private var _task = MutableLiveData<UserTask>()
    val task: LiveData<UserTask>
        get() = _task

    private var _grandplans = MutableLiveData<MutableList<GrandPlanResponse>>()
    val grandplans: LiveData<MutableList<GrandPlanResponse>>
        get() = _grandplans

    private var token : String
    private var userId : String

    init {
        token = userPrefs.getString("jwt","")!!
        userId = userPrefs.getString("uid","")!!

    }

    fun getUserInfo() {

        Log.d("UserViewModel_공통", "token : $token")
        Log.d("UserViewModel_공통", "userId : $userId")
        // 코루틴을 사용하여 repository를 통해 서버에서 데이터를 가져온다.
        // 현재 유저 정보 불러오기
        runBlocking {
            val res = competitionRepository.getUserInfo(token, userId)
            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!
                Log.d("UserViewModel_공통", "body : $body")
                val winCount = (body.userWin + body.userWinwin).toDouble()
                val matchCount = winCount + body.userDraw + body.userLose

                if(matchCount != 0.0){
                    _winRate.value = ((winCount / matchCount) * 100).roundToInt()
                    _user.value = body
                }else{
                    _winRate.value = 0
                    _user.value = body
                }
            }
            else {
                Log.d("UserViewModel", "getUserInfo Failed")
            }
        }
    }

    fun getTasks() {
        viewModelScope.launch(Dispatchers.Main) {
            val res = userRepository.getTasks(token, userId)
            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!

                _task.value = UserTask(body.userDdtask.toString(), body.userDltask.toString(), body.userTdtask.toString(), body.userTttask.toString())
                Log.d("UserViewModel", "getTasks: ${_task.value}")
            }
        }
    }

    fun getGrandplans() {
        runBlocking {
            val res = planDetailRepository.getGrandplans(token, userId)
            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!
                _grandplans.value = body
                Log.d("UserViewModel", "getGrandplans: ${_grandplans.value}")
            }
        }
    }
}