package com.ssafy.hajo.network.api

import com.ssafy.hajo.repository.dto.request.BoastAddRequest
import com.ssafy.hajo.repository.dto.request.BoastDetailRequest
import com.ssafy.hajo.repository.dto.request.BoastModifyRequest
import com.ssafy.hajo.repository.dto.request.ShareAddRequest
import com.ssafy.hajo.repository.dto.response.*
import retrofit2.Response
import retrofit2.http.*

interface BoardApi {

    @GET("boast/{userUid}")
    suspend fun getBoastTop5(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("userUid") userUid : String
    ): Response<BoastHomeHeader>

    @POST("boast/type")
    suspend fun getBoastAll(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request : Map<String,String>
    ): Response<MutableList<BoastMoreResponse>>

    @POST("boast/detail")
    suspend fun getBoastDetail(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: Map<String,String>
    ): Response<BoastDetailResponse>

    @POST("boast")
    suspend fun addBoast(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: BoastAddRequest
    ): Response<Map<String,Any>>

    @PUT("boast")
    suspend fun modifyBoast(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: BoastModifyRequest
    ) : Response<Map<String,Any>>

    @DELETE("boast/{seq}")
    suspend fun deleteBoast(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("seq") seq : Long
    ) : Response<Map<String,Any>>

    @GET("shrplan/{uid}")
    suspend fun getShareTop5(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("uid") userUid : String
    ): Response<ShareHomeHeader>


    @POST("shrplan/type")
    suspend fun getShareMore(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request : Map<String,String>
    ) : Response<ArrayList<ShareResponse>>

    @POST("shrplan/detail")
    suspend fun getShareDetail(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: Map<String, String>
    ): Response<ShareDetailResponse>

    @POST("shrplan")
    suspend fun addShare(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: ShareAddRequest
    ) : Response<Map<String,Any>>

    @PUT("shrplan")
    suspend fun modifyShare(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: ShareAddRequest
    ) : Response<Map<String,Any>>

    @DELETE("shrplan/{seq}")
    suspend fun deleteShare(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("seq") shareSeq: Long
    ) : Response<Map<String,Any>>

    @PUT("boast/like")
    suspend fun boastUpLike(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: HashMap<String, String>
    ) : Response<Map<String,String>>

    @POST("boast/like")
    suspend fun boastDownLike(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: HashMap<String, String>
    ) : Response<Map<String,String>>

    @PUT("shrplan/like")
    suspend fun shareUpLike(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: HashMap<String, String>
    ) : Response<Map<String,String>>

    @POST("shrplan/like")
    suspend fun shareDownLike(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: HashMap<String, String>
    ) : Response<Map<String,String>>

    @PUT("shrplan/bmk")
    suspend fun upBookMark(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: HashMap<String, String>
    ) : Response<Map<String,String>>

    @POST("shrplan/bmk")
    suspend fun downBookMark(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: HashMap<String, String>
    ) : Response<Map<String,String>>

    @PUT("boast/rpt")
    suspend fun boastUpdateReport(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: HashMap<String, String>
    ) : Response<Map<String,String>>

    @PUT("shrplan/rpt")
    suspend fun shareUpdateReport(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: HashMap<String, String>
    ) : Response<Map<String,String>>

}