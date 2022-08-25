package com.ssafy.hajo.network.api

import Diary
import DiaryHome
import DiaryObject
import LocatedObj
import TextObjectInfo
import com.ssafy.hajo.plan.BPlanSpinner
import com.ssafy.hajo.repository.dto.response.GrandPlanHomeResponse
import com.ssafy.hajo.repository.dto.response.TaskResponse
import retrofit2.Response
import retrofit2.http.*

interface DiaryApi {

    // 다이어리 액티비티
    @GET("diary/{seq}")
    suspend fun getDiaryInfo(@Header("X-ACCESS-TOKEN")token : String,@Path("seq") seq : Int) : Response<Diary>

    // 다이어리 위치 수정
    @PUT("diary/obj/loc")
    suspend fun saveLocation(@Header("X-ACCESS-TOKEN") token: String, @Body objs : MutableList<LocatedObj>) : Response<Int>

    // 오브젝트 추가
    @POST("diary/obj")
    suspend fun addObj(@Header("X-ACCESS-TOKEN") token: String, @Body newObj: DiaryObject): Response<Int>

    // 오브젝트 삭제
    @DELETE("diary/obj/{seq}") //todo 수정하기
    suspend fun deleteObj(@Header("X-ACCESS-TOKEN")token : String,@Path("seq") seq : Int) : Response<Int>

    // 오브젝트 이미지 수정
    @PUT("diary/obj/pic")
    suspend fun updateObjImg(@Header("X-ACCESS-TOKEN") token: String, @Body contents : HashMap<String, String>) : Response<Int>

    // 오브젝트 텍스트 수정
    @PUT("diary/obj/text")
    suspend fun updateObjText(@Header("X-ACCESS-TOKEN") token: String, @Body contents : TextObjectInfo) : Response<Int>




    // 다이어리 홈
    @GET("diary/home/{uid}")
    suspend fun getUserDiary(@Header("X-ACCESS-TOKEN") token: String,
                                    @Path("uid") uid: String) : Response<MutableList<DiaryHome>>

    @DELETE("diary/{seq}")
    suspend fun deleteDiary(@Header("X-ACCESS-TOKEN") token: String,
                             @Path("seq") diarySeq: Int) : Response<Int>

    @POST("diary/")
    suspend fun addDiary(@Header("X-ACCESS-TOKEN") token: String,
                         @Body diary: Map<String, String>) : Response<Int>
}