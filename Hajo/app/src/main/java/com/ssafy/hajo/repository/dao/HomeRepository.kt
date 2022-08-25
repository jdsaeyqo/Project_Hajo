package com.ssafy.hajo.repository.dao

import com.ssafy.hajo.network.BoardService
import com.ssafy.hajo.network.HomeService
import com.ssafy.hajo.repository.dto.request.TaskAddRequest
import com.ssafy.hajo.repository.dto.response.BoastHomeResponse
import com.ssafy.hajo.repository.dto.response.HomeResponse
import retrofit2.Response

class HomeRepository {

    suspend fun getHomeList(
        token: String,
        request: HashMap<String, String>
    ): Response<MutableList<HomeResponse>> {
        return HomeService.retrofitService.getHomeList(token, request)
    }

    suspend fun taskCompleted(
        token: String,
        taskSeq: Long
    ): Response<Map<String,String>> {
        return HomeService.retrofitService.taskCompleted(token, taskSeq)
    }

    suspend fun taskAdd(
        token: String,
        request : TaskAddRequest
    ) : Response<Int>{
        return HomeService.retrofitService.addTask(token,request)
    }
}