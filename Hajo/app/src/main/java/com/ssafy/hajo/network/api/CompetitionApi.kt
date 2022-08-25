package com.ssafy.hajo.network.api

import com.ssafy.hajo.entity.GrandPlan
import com.ssafy.hajo.repository.dto.request.MatchRequest
import com.ssafy.hajo.repository.dto.response.*
import retrofit2.Response
import retrofit2.http.*

interface CompetitionApi {
    @GET("match/now/{uid}")
    suspend fun getMatchNow(@Header("X-ACCESS-TOKEN") token: String,
                            @Path("uid") userUid: String): Response<MutableList<MatchNowResponse>>

    @GET("match/pschall/{uid}")
    suspend fun getMatchReceived(@Header("X-ACCESS-TOKEN") token: String,
                                 @Path("uid") userUid: String): Response<MutableList<MatchReceivedResponse>>

    @GET("match/pschall/send/{uid}")
    suspend fun getMatchSent(@Header("X-ACCESS-TOKEN") token: String,
                             @Path("uid") userUid: String): Response<MutableList<MatchReceivedResponse>>

    @POST("match/pbchall")
    suspend fun registerRequest(@Header("X-ACCESS-TOKEN") token: String,
                                @Body request : HashMap<String,Any>) : Response<HashMap<String, String>>

    @GET("histories/{uid}")
    suspend fun getHistory(@Header("X-ACCESS-TOKEN") token: String,
                           @Path("uid") userUid: String): Response<MutableList<HistoryResponse>>

    @POST("match/pschall")
    suspend fun sendRequest(@Header("X-ACCESS-TOKEN") token: String,
                            @Body request : MatchRequest): Response<HashMap<String,String>>

    @DELETE("match/pschall/{pschallSeq}")
    suspend fun rejectMatch(@Header("X-ACCESS-TOKEN") token: String,
                            @Path("pschallSeq") seq : Long): Response<HashMap<String,String>>

    @GET("match/onmatch/{uid}")
    suspend fun getUserInfo(@Header("X-ACCESS-TOKEN") token: String,
                            @Path("uid") userUid: String): Response<UserResponse>

    @GET("match/onmatch")
    suspend fun getProfiles(@Header("X-ACCESS-TOKEN") token: String): Response<MutableList<UserResponse>>

    @PUT("match/onmatch")
    suspend fun changeStatus(@Header("X-ACCESS-TOKEN") token: String,
                             @Body request : HashMap<String,Any>): Response<HashMap<String,String>>

    @GET("match/plan/{uid}")
    suspend fun getGrandPlans(@Header("X-ACCESS-TOKEN") token: String,
                              @Path("uid") userUid: String): Response<MutableList<GrandPlan>>

    @DELETE("match/pbchall/{grandplanSeq}")
    suspend fun deleteRequest(@Header("X-ACCESS-TOKEN") token: String,
                              @Path("grandplanSeq") seq: Long): Response<HashMap<String,String>>

    @PUT("match/result")
    suspend fun updateResult(@Header("X-ACCESS-TOKEN") token: String,
                             @Body request : HashMap<String,Any>): Response<HashMap<String,String>>

    @POST("match/pschall/accept")
    suspend fun acceptMatch(@Header("X-ACCESS-TOKEN") token: String,
                            @Body request : HashMap<String,Any>): Response<HashMap<String,String>>

    @GET("match/pbchall")
    suspend fun getRequests(@Header("X-ACCESS-TOKEN") token: String): Response<MutableList<RequestFormResponse>>

    @GET("match/pbchall/{uid}")
    suspend fun getMyRequests(@Header("X-ACCESS-TOKEN") token: String,
                              @Path("uid") userUid: String): Response<MutableList<RequestFormResponse>>
}