package com.ssafy.hajo.repository.dao

import com.ssafy.hajo.network.BoardService
import com.ssafy.hajo.repository.dto.request.BoastAddRequest
import com.ssafy.hajo.repository.dto.request.BoastDetailRequest
import com.ssafy.hajo.repository.dto.request.BoastModifyRequest
import com.ssafy.hajo.repository.dto.request.ShareAddRequest
import com.ssafy.hajo.repository.dto.response.*
import retrofit2.Response
import retrofit2.http.Body

class BoardRepository {

    suspend fun getBoastTop5(
        token: String,
        uid: String
    ): Response<BoastHomeHeader> {
        return BoardService.retrofitService.getBoastTop5(token, uid)
    }

    suspend fun getBoastAll(
        token: String,
        request: Map<String, String>
    ): Response<MutableList<BoastMoreResponse>> {
        return BoardService.retrofitService.getBoastAll(token, request)
    }

    suspend fun getBoastDetail(
        token: String,
        request: Map<String, String>
    ): Response<BoastDetailResponse> {
        return BoardService.retrofitService.getBoastDetail(token, request)
    }

    suspend fun addBoast(
        token: String,
        request: BoastAddRequest
    ): Response<Map<String, Any>> {
        return BoardService.retrofitService.addBoast(token, request)
    }

    suspend fun modifyBoast(
        token: String,
        request: BoastModifyRequest
    ): Response<Map<String, Any>> {
        return BoardService.retrofitService.modifyBoast(token, request)
    }

    suspend fun deleteBoast(
        token: String,
        seq: Long
    ): Response<Map<String, Any>> {
        return BoardService.retrofitService.deleteBoast(token, seq)
    }

    suspend fun getShareTop5(
        token: String,
        uid: String
    ): Response<ShareHomeHeader> {
        return BoardService.retrofitService.getShareTop5(token, uid)
    }

    suspend fun getShareMore(
        token: String,
        request: Map<String, String>
    ): Response<ArrayList<ShareResponse>> {
        return BoardService.retrofitService.getShareMore(token, request)
    }

    suspend fun getShareDetail(
        token: String,
        request: Map<String, String>
    ): Response<ShareDetailResponse> {
        return BoardService.retrofitService.getShareDetail(token, request)
    }

    suspend fun addShare(
        token: String,
        request: ShareAddRequest
    ): Response<Map<String, Any>> {
        return BoardService.retrofitService.addShare(token, request)
    }

    suspend fun modifyShare(
        token: String,
        request: ShareAddRequest
    ): Response<Map<String, Any>> {
        return BoardService.retrofitService.modifyShare(token, request)
    }

    suspend fun deleteShare(
        token: String,
        shareSeq: Long
    ): Response<Map<String, Any>> {
        return BoardService.retrofitService.deleteShare(token, shareSeq)
    }

    suspend fun boastUpLike(
        token: String,
        request: HashMap<String, String>
    ): Response<Map<String, String>> {
        return BoardService.retrofitService.boastUpLike(token, request)
    }

    suspend fun boastDownLike(
        token: String,
        request: HashMap<String, String>
    ): Response<Map<String, String>> {
        return BoardService.retrofitService.boastDownLike(token, request)
    }

    suspend fun shareUpLike(
        token: String,
        request: HashMap<String, String>
    ): Response<Map<String, String>> {
        return BoardService.retrofitService.shareUpLike(token, request)
    }

    suspend fun shareDownLike(
        token: String,
        request: HashMap<String, String>
    ): Response<Map<String, String>> {
        return BoardService.retrofitService.shareDownLike(token, request)
    }

    suspend fun updBookMark(
        token: String,
        request: HashMap<String, String>
    ): Response<Map<String, String>> {
        return BoardService.retrofitService.upBookMark(token, request)
    }

    suspend fun downBookMark(
        token: String,
        request: HashMap<String, String>
    ): Response<Map<String, String>> {
        return BoardService.retrofitService.downBookMark(token, request)
    }

    suspend fun boastUpdateReport(
        token: String,
        request: HashMap<String, String>
    ): Response<Map<String, String>> {
        return BoardService.retrofitService.boastUpdateReport(token, request)
    }

    suspend fun shareUpdateReport(
        token: String,
        request: HashMap<String, String>
    ): Response<Map<String, String>> {
        return BoardService.retrofitService.shareUpdateReport(token, request)
    }
}