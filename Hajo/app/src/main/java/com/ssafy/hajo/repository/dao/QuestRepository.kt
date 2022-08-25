package com.ssafy.hajo.repository.dao

import com.ssafy.hajo.network.SettingService
import com.ssafy.hajo.repository.dto.response.MyQuestResponse
import com.ssafy.hajo.repository.dto.response.QuestResponse
import retrofit2.Response

class QuestRepository {

    suspend fun getQuestList(token: String): Response<MutableList<QuestResponse>> {
        return SettingService.retrofitService.getQuestList(token)
    }

    suspend fun getMyQuest(token: String, uid: String): Response<MutableList<MyQuestResponse>> {
        return SettingService.retrofitService.getMyQuest(token, uid)
    }

    suspend fun getReward(
        token: String,
        request: Map<String, Long>
    ): Response<Map<String, String>> {
        return SettingService.retrofitService.getReward(token, request)
    }
}