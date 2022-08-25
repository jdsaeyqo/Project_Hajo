package com.ssafy.hajo.board.share

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.hajo.entity.GrandPlan
import com.ssafy.hajo.plan.BPlan
import com.ssafy.hajo.plan.BPlanSpinner
import com.ssafy.hajo.repository.dao.BoardRepository
import com.ssafy.hajo.repository.dao.PlanDetailRepository
import com.ssafy.hajo.repository.dto.request.ShareAddRequest
import com.ssafy.hajo.repository.dto.response.ShareDetailResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShareAddViewModel : ViewModel() {


    val uid = GlobalApplication.userPrefs.getString("uid", "")
    val jwt = GlobalApplication.userPrefs.getString("jwt", "")
    private val planDetailRepository: PlanDetailRepository by lazy {
        PlanDetailRepository()
    }

    private val boardRepository by lazy {
        BoardRepository()
    }

    private val _sharedGrandPlanList = MutableLiveData<MutableList<BPlanSpinner>>()

    val sharedGrandPlanList: LiveData<MutableList<BPlanSpinner>>
        get() = _sharedGrandPlanList

    private val _sharedGrandPlanTitle = MutableLiveData<String>()
    val shareGrandPlanTitle: LiveData<String>
        get() = _sharedGrandPlanTitle

    private val _sharedSpendingPeriod = MutableLiveData<Int>()
    val shareSpendingPeriod: LiveData<Int>
        get() = _sharedSpendingPeriod

    private val _shareGrandSummary = MutableLiveData<String>()
    val shareGrandSummary: LiveData<String>
        get() = _shareGrandSummary

    private val _sharePlanCategory = MutableLiveData<String>()
    val sharePlanCategory: LiveData<String>
        get() = _sharePlanCategory

    private val _sharePlanTitle = MutableLiveData<String>()
    val sharePlanTitle: LiveData<String>
        get() = _sharePlanTitle

    private val _sharePlanContent = MutableLiveData<String>()
    val sharePlanContent: LiveData<String>
        get() = _sharePlanContent

    private var _validate = MutableLiveData<Boolean>()
    val validate: LiveData<Boolean>
        get() = _validate

    val tvShareGradPlanTitle = MutableLiveData<String>()
    val etShareSpendingPeriod = MutableLiveData<String>()
    val etGrandPlanExplain = MutableLiveData<String>()
    val tvSharedContentCategory = MutableLiveData<String>()
    val etSharedContentTitle = MutableLiveData<String>()
    val etSharedContentBody = MutableLiveData<String>()

    init {
        tvShareGradPlanTitle.value = "대플랜을 선택해주세요"
        tvSharedContentCategory.value = "카테고리"
        _sharedGrandPlanTitle.value = ""
        _sharedSpendingPeriod.value = 0
        _shareGrandSummary.value = ""
        _sharePlanCategory.value = ""
        _sharePlanTitle.value = ""
        _sharePlanContent.value = ""
    }

    fun upload(share : ShareAddRequest,mode : Int){

        if(mode == 0){
            //신규
            Log.d("ShareAddViewModel", "share0 : $share ")
            Log.d("ShareAddViewModel", "mode0 : $mode ")
            viewModelScope.launch(Dispatchers.Main) {
                val res = boardRepository.addShare(jwt!!,share)

                if(res.isSuccessful && res.body() != null){
                    Log.d("ShareAddViewModel", "addShare:${res.body()}")
                }else{
                    Log.d("ShareAddViewModel", "err:${res.errorBody()?.string()}")

                }
            }
        }else{
            //수정
            Log.d("ShareAddViewModel", "share1 : $share ")
            Log.d("ShareAddViewModel", "mode1 : $mode ")
            viewModelScope.launch(Dispatchers.Main) {
                val res = boardRepository.modifyShare(jwt!!,share)

                if(res.isSuccessful && res.body() != null){
                    Log.d("ShareAddViewModel", "modifyShare:${res.body()}")
                }
            }
        }
    }

    fun getGrandPlanList() {


        Log.d("ShareAddViewModel", "getGrandPlanList: $uid\n$jwt")

        viewModelScope.launch(Dispatchers.Main) {
            val res = planDetailRepository.getBPlanForSpinners(jwt!!, uid!!)


            Log.d("ShareAddViewModel", "${res}")
            _sharedGrandPlanList.postValue(res)

        }
    }

    fun getUpdate() {
        val updatedGrandPlanTitle = tvShareGradPlanTitle.value.toString()
        val updatedPeriod = etShareSpendingPeriod.value.toString()
        val updatedExplain = etGrandPlanExplain.value.toString()
        val updatedContentCategory = tvSharedContentCategory.value.toString()
        val updatedContentTitle = etSharedContentTitle.value.toString()
        val updatedContentBody = etSharedContentBody.value.toString()

        Log.d(
            "getUpdate",
            "$updatedPeriod $updatedExplain $updatedContentTitle $updatedContentBody"
        )

        if (updatedPeriod == "null" || updatedPeriod == "" || updatedExplain == "null" || updatedExplain == ""
            || updatedGrandPlanTitle == "" || updatedGrandPlanTitle == "null" || updatedContentCategory == ""
            || updatedContentCategory == "카테고리" || updatedContentTitle == "" || updatedContentTitle == "null"
            || updatedContentBody == "" || updatedContentBody == "null"
        ) {
            _validate.value = false
            return
        }

        _sharedGrandPlanTitle.value = updatedGrandPlanTitle
        _sharedSpendingPeriod.value = updatedPeriod.toInt()
        _shareGrandSummary.value = updatedExplain
        _sharePlanCategory.value = updatedContentCategory
        _sharePlanTitle.value = updatedContentTitle
        _sharePlanContent.value = updatedContentBody

        _validate.value = true

    }

    fun setData(share : ShareDetailResponse){
        tvShareGradPlanTitle.value = share.grandplanTitle
        etShareSpendingPeriod.value = share.shrplanPeriod.toString()
        etGrandPlanExplain.value = share.shrplanSummary
        tvSharedContentCategory.value = share.shrplanCategory
        etSharedContentTitle.value = share.shrplanTitle
        etSharedContentBody.value = share.shrplanContent
    }

}