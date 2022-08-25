package com.ssafy.hajo.competition

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.entity.GrandPlan
import com.ssafy.hajo.entity.Match
import com.ssafy.hajo.entity.Request
import com.ssafy.hajo.repository.dao.CompetitionRepository
import com.ssafy.hajo.repository.dto.response.GrandPlanResponse
import com.ssafy.hajo.repository.dto.response.UserResponse
import com.ssafy.hajo.util.GlobalApplication.Companion.userPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class CompetitionViewModel : ViewModel(){
    private val competitionRepository by lazy {
        CompetitionRepository()
    }

    private var token : String
    private var userId : String

    private val matchList = mutableListOf<Match>()
    private val requestList = mutableListOf<Request>()
    private val planList = mutableListOf<GrandPlan>()

    private val _user = MutableLiveData<UserResponse>()
    val user : LiveData<UserResponse>
        get() = _user

    private val _match = MutableLiveData<MutableList<Match>>()
    val match: LiveData<MutableList<Match>>
        get() = _match

    private val _request = MutableLiveData<MutableList<Request>>()
    val request: LiveData<MutableList<Request>>
        get() = _request

    private val _plans = MutableLiveData<MutableList<GrandPlan>>()
    val plans: LiveData<MutableList<GrandPlan>>
     get() = _plans

    init {
        token = userPrefs.getString("jwt","")!!
        userId = userPrefs.getString("uid","")!!

        // 현재 유저 정보 불러오기
        viewModelScope.launch(Dispatchers.Main) {

        }

        // 현재 진행 중인 경쟁전 불러오기
        viewModelScope.launch(Dispatchers.Main) {

        }

//        matchList.add(Match("대플랜이름1", "2022-08-09", 3, "N"))
//        _match.value = matchList


        // 신청 받은 경쟁전 리스트 불러오기
        viewModelScope.launch(Dispatchers.Main) {

        }
    }

    fun changeOnMatch(flag: Boolean) {
        val tmp = _user.value!!
        tmp.userOnmatch = flag
        _user.value = tmp
    }

    fun getNowMatch() {
        matchList.clear()
        runBlocking {
            val res = competitionRepository.getMatchNow(token,userId)

            if(res.isSuccessful && res.body() != null) {
                Log.d("tttt", "body: ${res.body()}")
                res.body()!!.forEach {
                    val endDate = LocalDate.parse(it.matchEnddate.substring(0,10), DateTimeFormatter.ISO_DATE)
                    val startDate = LocalDate.parse(it.matchStartdate.substring(0,10), DateTimeFormatter.ISO_DATE)
                    val today = LocalDate.now()
                    val duration = Period.between(startDate,endDate).days + 1
                    val dayDiff = Period.between(startDate, today).days

//                    if(dayDiff >= 0) {
//                        matchList.add(Match(it.nowSeq,it.grandplanTitle, it.matchStartdate, duration, it.matchResult))
//                    }
                    matchList.add(Match(it.nowSeq,it.grandplanTitle, it.matchStartdate, duration, it.matchResult))
                }
                _match.value = matchList
            }
            else {
                Log.d("CompetitionViewModel", "현재 진행중인 경쟁전 불러오기 오류")
            }
        }
    }


    fun deleteMatch(pos: Int) {
        matchList.removeAt(pos)
        _match.value = matchList
    }

    fun deleteRequest(pos: Int) {
        requestList.removeAt(pos)
        _request.value = requestList
    }

    fun getPlans() {

        // 현재 경쟁전 참여 가능한 대플랜 목록 불러오기
        runBlocking {
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

    fun getUser() {
        runBlocking {
            val res = competitionRepository.getUserInfo(token, userId)
            if(res.isSuccessful && res.body() != null) {
                Log.d("CompetitionViewModel", "유저 정보: ${res.body()!!}")
                _user.value = res.body()!!
            }
        }
    }

    fun getMatchRequest() {
        requestList.clear()
        runBlocking {
            val res = competitionRepository.getMatchReceived(token, userId)
            Log.d("CompetitionViewModel", "${res.body()}: ")
            if(res.isSuccessful && res.body() != null) {
                Log.d("CompetitionViewModel", "신청 받은 경쟁전 : ${res.body()}")
                res.body()!!.forEach {
                    requestList.add(Request(it.pschallSeq, it.userUid, it.matchUserUid, it.matchUserNickname,
                        it.matchUserExp, it.matchUserTitle, it.matchGrandplanTitle,it.grandplanTitle,it.matchPeriod))
                }
                _request.value = requestList
            }
            else {
                Log.d("CompetitionViewModel", "신청 받은 경쟁전 리스트 불러오기 오류")
            }
        }
    }
}