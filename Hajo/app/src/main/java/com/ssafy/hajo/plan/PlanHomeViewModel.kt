package com.ssafy.hajo.plan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.repository.dao.PlanDetailRepository
import com.ssafy.hajo.repository.dto.response.GrandPlanHomeResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanHomeViewModel : ViewModel() {

    private val userPrefs = GlobalApplication.userPrefs

    private val planDetailRepository: PlanDetailRepository by lazy {
        PlanDetailRepository()
    }

    private val _planHomeList = MutableLiveData<MutableList<GrandPlanHomeResponse>>()
    val planHomeList: LiveData<MutableList<GrandPlanHomeResponse>>
        get() = _planHomeList

    fun getPlanHomeList(){
        val uid = userPrefs.getString("uid", "")
        val jwt = userPrefs.getString("jwt", "")
        Log.d("PlanHomeViewModel", "jwt: $jwt ")
        viewModelScope.launch(Dispatchers.Main) {

            val res = planDetailRepository.getPlanHome(jwt!!,uid!!)
            Log.d("PlanHomeViewModel", "getPlanHomeList: ${res.body()}")

            if(res.isSuccessful && res.body()!=null){
                _planHomeList.postValue(res.body())
            }

        }
    }
}