package com.ssafy.hajo.network.api

import com.ssafy.hajo.plan.*
import com.ssafy.hajo.repository.dto.response.*
import retrofit2.Response
import retrofit2.http.*

interface PlanDetailApi {
    @GET("plangrands/{uid}")
    suspend fun getBPlanForSpinners(@Header("X-ACCESS-TOKEN") token: String,
                                    @Path("uid") uid: String) : Response<MutableList<BPlanSpinner>>

    @GET("plangrands/{uid}")
    suspend fun getGrandplans(@Header("X-ACCESS-TOKEN") token: String,
                                    @Path("uid") uid: String) : Response<MutableList<GrandPlanResponse>>

    @GET("plangrands/detail/{seq}")
    suspend fun getBPlanInfo(@Header("X-ACCESS-TOKEN") token: String,
                                    @Path("seq") grandSeq: Int) : Response<BPlan>

    @GET("plansmalls/{seq}") // todo 루트 바꾸기
    suspend fun getDayPlanInfo(@Header("X-ACCESS-TOKEN") token: String,
                             @Path("seq") planSeq: Int) : Response<LittlePlanInfo>

    @GET("plansmalls/plantasks/{seq}") // todo 루트 바꾸기
    suspend fun getTaskDetail(@Header("X-ACCESS-TOKEN") token: String,
                                  @Path("seq") planSeq: Int) : Response<Task>


    @PUT("plangrands")
    suspend fun updateBPlanTitle(@Header("X-ACCESS-TOKEN") token: String, @Body bPlan: HashMap<String, Any>) : Response<Int>


    @PUT("plangrands")
    suspend fun updateBPlanDescription(@Header("X-ACCESS-TOKEN") token: String, @Body bPlan: HashMap<String, Any>) : Response<Int>

    @PUT("plangrands")
    suspend fun updateBPlanStartday(@Header("X-ACCESS-TOKEN") token: String, @Body bPlan: HashMap<String, Any>) : Response<Int>

    @PUT("plangrands")
    suspend fun updateBPlanEndday(@Header("X-ACCESS-TOKEN") token: String, @Body bPlan: HashMap<String, Any>) : Response<Int>

    @PUT("plangrands")
    suspend fun updateBPlanColor(@Header("X-ACCESS-TOKEN") token: String, @Body bPlan: HashMap<String, Any>) : Response<Int>


    @PUT("planmids")
    suspend fun updateMPlan(@Header("X-ACCESS-TOKEN") token: String, @Body mPlan: HashMap<String, String>) : Response<Int>

    @PUT("plantasks")
    suspend fun updateTask(@Header("X-ACCESS-TOKEN") token: String, @Body task: Task) : Response<Int>


    @POST("plangrands")
    suspend fun addBPlan(@Header("X-ACCESS-TOKEN") token: String, @Body grandPlan: HashMap<String,String>) : Response<BPlan>

    @POST("planmids")
    suspend fun addMPlan(@Header("X-ACCESS-TOKEN") token: String, @Body mPlan: HashMap<String, String>) : Response<MPlan>

    @POST("plantasks")
    suspend fun addTask(@Header("X-ACCESS-TOKEN") token: String, @Body task: Task) : Response<Int>

    @GET("plangrands/home/{uid}")
    suspend fun getPlanHome(@Header("X-ACCESS-TOKEN")token : String,@Path("uid") uid : String) : Response<MutableList<GrandPlanHomeResponse>>

    @DELETE("plangrands/{planSeq}") //todo 수정하기
    suspend fun deleteBPlan(@Header("X-ACCESS-TOKEN")token : String,@Path("planSeq") planSeq : Int) : Response<Int>

    @DELETE("planmids/{planSeq}") //todo 수정하기
    suspend fun deleteMPlan(@Header("X-ACCESS-TOKEN")token : String,@Path("planSeq") planSeq : Int) : Response<Int>

    @DELETE("plantasks/{planSeq}") //todo 수정하기
    suspend fun deleteTask(@Header("X-ACCESS-TOKEN")token : String,@Path("planSeq") planSeq : Int) : Response<Int>

    @PUT("plantasks/check/{taskSeq}")
    suspend fun checkTask(@Header("X-ACCESS-TOKEN") token: String, @Path("taskSeq") taskSeq : Int) : Response<HashMap<String, String>>

    @POST("plantasks/bydate")
    suspend fun getTasks(@Header("X-ACCESS-TOKEN") token: String, @Body request: HashMap<String, String>): Response<TaskResponse>

    @POST("plansmalls/bydate")
    suspend fun getTaskCount(@Header("X-ACCESS-TOKEN") token: String, @Body request: HashMap<String, String>): Response<MutableList<MyPageSmallPlanResponse>>

}