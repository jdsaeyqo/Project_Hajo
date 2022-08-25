package com.ssafy.hajo.network.api

import com.ssafy.hajo.repository.dto.request.TaskAddRequest
import com.ssafy.hajo.repository.dto.response.HomeResponse
import retrofit2.Response
import retrofit2.http.*

interface HomeApi {

    @POST("home")
    suspend fun getHomeList(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: HashMap<String, String>
    ): Response<MutableList<HomeResponse>>

    @PUT("plantasks/check/{taskSeq}")
    suspend fun taskCompleted(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("taskSeq") taskSeq : Long
    ) : Response<Map<String,String>>

    @POST("plantasks")
    suspend fun addTask(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: TaskAddRequest
    ) : Response<Int>
}