package com.ssafy.hajo.network.api

import com.ssafy.hajo.repository.dto.UserDto
import com.ssafy.hajo.repository.dto.request.UserInfoRequest
import com.ssafy.hajo.repository.dto.response.TitleResponse
import com.ssafy.hajo.repository.dto.response.UserResponse
import com.ssafy.hajo.repository.dto.response.UserTaskResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*
import java.util.HashMap

interface UserApi {
    @GET("users/{userUid}")
    suspend fun getUser(
        @Header("X-ACCESS-TOKEN") token: String,@Path("userUid") userUid: String
    ): Response<UserDto>

    @POST("users/social")
    suspend fun socialLogin(@Body token:  Map<String,String>) : Response<Map<String,String>>

    @POST("users/social/signup")
    suspend fun signUp(@Body user: UserInfoRequest) : Response<Map<String,String>>

    @PUT("users")
    suspend fun updateUser(@Header("X-ACCESS-TOKEN") token: String, @Body user: HashMap<String, Any>) : Response<UserDto>

    @GET("users/titles/{uid}")
    suspend fun getTitles(@Header("X-ACCESS-TOKEN") token: String,@Path("uid") userUid: String): Response<MutableList<TitleResponse>>

    @GET("users/tasks/{uid}")
    suspend fun getTasks(@Header("X-ACCESS-TOKEN") token: String,@Path("uid") userUid: String): Response<UserTaskResponse>

    @POST("users/block")
    suspend fun blockUser(@Header("X-ACCESS-TOKEN") token: String, @Body user: HashMap<String, String>) : Response<Map<String,String>>
}