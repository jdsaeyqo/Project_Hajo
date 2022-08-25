package com.ssafy.hajo.repository.dao

import android.util.Log
import com.ssafy.hajo.entity.GrandPlan
import com.ssafy.hajo.network.CompetitionService
import com.ssafy.hajo.repository.dto.request.MatchRequest
import com.ssafy.hajo.repository.dto.response.*
import retrofit2.Response

class CompetitionRepository {
    // 본인의 진행 중인 경쟁전 불러오기
    suspend fun getMatchNow(token: String, userUid: String): Response<MutableList<MatchNowResponse>> {
        return CompetitionService.retrofitService.getMatchNow(token, userUid)
    }

    // 신청 받은 경쟁전 불러오기
    suspend fun getMatchReceived(token: String, userUid: String): Response<MutableList<MatchReceivedResponse>> {
        return CompetitionService.retrofitService.getMatchReceived(token, userUid)
    }

    // 신청한 경쟁전 불러오기
    suspend fun getMatchSent(token: String, userUid: String): Response<MutableList<MatchReceivedResponse>> {
        return CompetitionService.retrofitService.getMatchSent(token, userUid)
    }

    // 제안서 등록하기
    suspend fun registerRequest(token: String, request: HashMap<String, Any>): Response<HashMap<String, String>> {
        return CompetitionService.retrofitService.registerRequest(token, request)
    }

    // 전적 불러오기
    suspend fun getHistory(token: String, userUid: String): Response<MutableList<HistoryResponse>> {
        return CompetitionService.retrofitService.getHistory(token, userUid)
    }

    // 매칭하기에서 경쟁전 제안서 신청하기
    suspend fun sendRequest(token: String, request: MatchRequest): Response<HashMap<String,String>> {
        return CompetitionService.retrofitService.sendRequest(token, request)
    }

    // 신청 받은 경쟁전 거절
    suspend fun rejectMatch(token: String, seq: Long): Response<HashMap<String,String>> {
        return CompetitionService.retrofitService.rejectMatch(token, seq)
    }

    // 현재 유저 정보 불러오기
    suspend fun getUserInfo(token: String, userUid: String): Response<UserResponse> {
        return CompetitionService.retrofitService.getUserInfo(token, userUid)
    }

    // 경쟁전 프로필 불러오기
    suspend fun getProfiles(token: String): Response<MutableList<UserResponse>> {
        return CompetitionService.retrofitService.getProfiles(token)
    }

    // 경쟁 참여 설정 변경하기
    suspend fun changeStatus(token: String, request: HashMap<String, Any>): Response<HashMap<String,String>> {
        return CompetitionService.retrofitService.changeStatus(token, request)
    }

    // 경쟁중이지 않는 대플랜 불러오기
    suspend fun getGrandPlans(token: String, userUid: String): Response<MutableList<GrandPlan>> {
        return CompetitionService.retrofitService.getGrandPlans(token, userUid)
    }

    // 제안서 삭제하기
    suspend fun deleteRequest(token: String, seq: Long): Response<HashMap<String,String>> {
        return CompetitionService.retrofitService.deleteRequest(token, seq)
    }

    // 경쟁전 결과 업데이트
    suspend fun updateResult(token: String, request: HashMap<String, Any>): Response<HashMap<String,String>> {
        return CompetitionService.retrofitService.updateResult(token, request)
    }

    // 신청 받은 경쟁전 수락
    suspend fun acceptMatch(token: String, request: HashMap<String, Any>): Response<HashMap<String, String>> {
        return CompetitionService.retrofitService.acceptMatch(token, request)
    }

    // 제안서 목록 불러오기
    suspend fun getRequests(token: String): Response<MutableList<RequestFormResponse>> {
        return CompetitionService.retrofitService.getRequests(token)
    }

    // 내 제안서 목록 불러오기
    suspend fun getMyRequest(token: String, userUid: String): Response<MutableList<RequestFormResponse>> {
        return CompetitionService.retrofitService.getMyRequests(token, userUid)
    }
}