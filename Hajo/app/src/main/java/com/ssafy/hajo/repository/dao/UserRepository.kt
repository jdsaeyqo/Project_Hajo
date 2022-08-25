package com.ssafy.hajo.repository.dao

import android.util.Log
import com.ssafy.hajo.network.RegistrationService
import com.ssafy.hajo.repository.dto.UserDto
import com.ssafy.hajo.repository.dto.request.UserInfoRequest
import com.ssafy.hajo.repository.dto.response.TitleResponse
import com.ssafy.hajo.repository.dto.response.UserResponse
import com.ssafy.hajo.repository.dto.response.UserTaskResponse
import retrofit2.Response

class UserRepository {

    suspend fun updateUser(token: String, user: HashMap<String, Any>) : Response<UserDto>{
        Log.d("tttt", "updateUser: ")
        return RegistrationService.retrofitService.updateUser(token,user)
    }

    suspend fun getUser(jwt : String, uid: String): Response<UserDto> {
        Log.d("tttt", "getUser: ")
        return RegistrationService.retrofitService.getUser(jwt,uid)
    }

    suspend fun socialLogin(token : HashMap<String,String>) : Response<Map<String,String>>{
        return RegistrationService.retrofitService.socialLogin(token)
    }

    suspend fun signUp(user : UserInfoRequest) : Response<Map<String,String>>{
        return RegistrationService.retrofitService.signUp(user)
    }

    suspend fun getTitles(token: String, userUid: String): Response<MutableList<TitleResponse>> {
        return RegistrationService.retrofitService.getTitles(token, userUid)
    }

    suspend fun getTasks(token: String, userUid: String): Response<UserTaskResponse> {
        return RegistrationService.retrofitService.getTasks(token, userUid)
    }

    suspend fun blockUser(token: String, userUid : HashMap<String,String>) : Response<Map<String,String>>{
        return RegistrationService.retrofitService.blockUser(token,userUid)
    }
}