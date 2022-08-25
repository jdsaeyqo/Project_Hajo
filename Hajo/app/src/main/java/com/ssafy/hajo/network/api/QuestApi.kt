package com.ssafy.hajo.network.api

import com.ssafy.hajo.repository.dto.response.MyQuestResponse
import com.ssafy.hajo.repository.dto.response.QuestResponse
import retrofit2.Response
import retrofit2.http.*

interface QuestApi {

    @GET("users/archive")
    suspend fun getQuestList(@Header("X-ACCESS-TOKEN") token: String): Response<MutableList<QuestResponse>>

    @GET("users/archive/{uid}")
    suspend fun getMyQuest(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("uid") uid: String
    ): Response<MutableList<MyQuestResponse>>

    @PUT("users/archive")
    suspend fun getReward(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: Map<String, Long>
    ): Response<Map<String, String>>


}