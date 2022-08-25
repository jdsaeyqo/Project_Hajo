package com.ssafy.hajo.repository.dao

import android.util.Log
import com.ssafy.hajo.network.PlanDetailService
import com.ssafy.hajo.plan.*
import com.ssafy.hajo.repository.dto.response.*


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class PlanDetailRepository {
    suspend fun getBPlanForSpinners(token: String, userId: String): MutableList<BPlanSpinner> {
        Log.d("planDetailRepository", "getBPlanForSpinners 시작")
        var result: MutableList<BPlanSpinner>

        result = withContext(Dispatchers.IO) {
            Log.d("planDetailRepository", "getBPlanForSpinners withContext 시작")
            val response = PlanDetailService.retrofitService.getBPlanForSpinners(token, userId)
            val spinnerItems = if (response.code() == 200) {
                Log.d("planDetailRepository", "getBPlanForSpinners 통신 성공")
                response.body() as MutableList<BPlanSpinner>
            } else {
                Log.d("planDetailRepository", "getBPlanForSpinners 통신 에러")
                mutableListOf()
            }
            Log.d("planDetailRepository", "getBPlanForSpinners 통신 결과 ${spinnerItems}")
            Log.d("planDetailRepository", "getBPlanForSpinners withContext 끝")
            spinnerItems
        }
        Log.d("planDetailRepository", "getBPlanForSpinners 끝")
        return result
    }

    suspend fun getBPlanInfo(token: String, bPlanId: Int): Response<BPlan> {
        val result = withContext(Dispatchers.IO) {
            Log.d("planDetailRepository", "getBPlanInfo 서버 시작")
            val bplan = PlanDetailService.retrofitService.getBPlanInfo(token, bPlanId)
            Log.d("planDetailRepository", "getBPlanInfo 서버 통신 후 Response<BPlan> 반환")
            bplan
        }
        return result
    }

    suspend fun getDayPlanInfo(token: String, planSeq: Int): Response<LittlePlanInfo> {
        val result = withContext(Dispatchers.IO) {
            Log.d("planDetailViewModel1", "getDayPlanInfo withContext 시작")
            val lPlan = PlanDetailService.retrofitService.getDayPlanInfo(token, planSeq)
            Log.d(
                "planDetailViewModel1",
                "getDayPlanInfo withContext 통신 후 Response<LittlePlanInfo> 반환}"
            )
            lPlan
        }
        return result
    }

    suspend fun getTaskDetail(token: String, planSeq: Int): Response<Task> {
        val result = withContext(Dispatchers.IO) {
            Log.d("planDetailRepository", "getTaskDetail 서버 시작")
            val task = PlanDetailService.retrofitService.getTaskDetail(token, planSeq)
            Log.d("planDetailRepository", "getTaskDetail 서버 통신 후 Response<Task> 반환")
            task
        }
        return result
    }

    suspend fun updateBPlanTitle(token: String, hm: HashMap<String, Any>): Response<Int> {
        Log.d("planDetailRepository", "updateBPlanTitle}")

        return PlanDetailService.retrofitService.updateBPlanTitle(token, hm)
    }


    suspend fun updateBPlanDescription(token: String, hm: HashMap<String, Any>): Response<Int> {
        Log.d("planDetailRepository", "updateBPlanDescription}")

        return PlanDetailService.retrofitService.updateBPlanDescription(token, hm)
    }


    suspend fun updateBPlanStartday(token: String, hm: HashMap<String, Any>): Response<Int> {
        Log.d("planDetailRepository", "updateBPlanStartday}")

        return PlanDetailService.retrofitService.updateBPlanStartday(token, hm)
    }


    suspend fun updateBPlanEndday(token: String, hm: HashMap<String, Any>): Response<Int> {
        Log.d("planDetailRepository", "updateBPlanEndday}")

        return PlanDetailService.retrofitService.updateBPlanEndday(token, hm)
    }

    suspend fun updateBPlanColor(token: String, hm: HashMap<String, Any>): Response<Int> {
        Log.d("planDetailRepository", "updateBPlanColor}")

        return PlanDetailService.retrofitService.updateBPlanColor(token, hm)
    }

    suspend fun updateMPlan(token: String, hm: HashMap<String, String>): Response<Int> {
        Log.d("planDetailRepository", "updateMPlan}")

        return PlanDetailService.retrofitService.updateMPlan(token, hm)
    }


    suspend fun updateTask (token: String, updateTask: Task): Response<Int> {
        Log.d("taskdetailupdateRepo", "updateTask")

        return PlanDetailService.retrofitService.updateTask(token, updateTask)
    }

    suspend fun addBPlan(token: String, newBPlan: HashMap<String, String>): Response<BPlan> {
        Log.d("planDetailRepository", "addBPlan")

        return PlanDetailService.retrofitService.addBPlan(token, newBPlan)
    }

    suspend fun addMPlan(token: String, newMPlan: HashMap<String, String>): Response<MPlan> {
        Log.d("planDetailRepository", "addMPlan")

        return PlanDetailService.retrofitService.addMPlan(token, newMPlan)
    }

    suspend fun addTask(token: String, newTask: Task): Response<Int> {
        Log.d("planDetailRepository", "addTask")

        return PlanDetailService.retrofitService.addTask(token, newTask)
    }

    suspend fun deleteBPlan(token: String, planSeq: Int): Response<Int> {
        Log.d("planDetailRepository", "deleteBPlan")

        return PlanDetailService.retrofitService.deleteBPlan(token, planSeq)
    }

    suspend fun deleteMPlan(token: String, planSeq: Int): Response<Int> {
        Log.d("planDetailRepository", "deleteMPlan")

        return PlanDetailService.retrofitService.deleteMPlan(token, planSeq)
    }

    suspend fun deleteTask(token: String, planSeq: Int): Response<Int> {
        Log.d("planDetailRepository", "deleteTask")

        return PlanDetailService.retrofitService.deleteTask(token, planSeq)
    }


    suspend fun getPlanHome(
        token: String,
        uid: String
    ): Response<MutableList<GrandPlanHomeResponse>> {
        return PlanDetailService.retrofitService.getPlanHome(token, uid)
    }


    suspend fun checkTask(token : String, taskSeq : Int) : Response<HashMap<String, String>> {
        return PlanDetailService.retrofitService.checkTask(token, taskSeq)
    }


        suspend fun getGrandplans(
            token: String,
            uid: String
        ): Response<MutableList<GrandPlanResponse>> {
            return PlanDetailService.retrofitService.getGrandplans(token, uid)
        }

        suspend fun getTasks(
            token: String,
            request: HashMap<String, String>
        ): Response<TaskResponse> {
            return PlanDetailService.retrofitService.getTasks(token, request)
        }

        suspend fun getTaskCount(
            token: String,
            request: HashMap<String, String>
        ): Response<MutableList<MyPageSmallPlanResponse>> {
            return PlanDetailService.retrofitService.getTaskCount(token, request)

        }
    }
