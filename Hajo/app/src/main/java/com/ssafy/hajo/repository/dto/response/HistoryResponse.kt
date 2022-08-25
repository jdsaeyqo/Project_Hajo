package com.ssafy.hajo.repository.dto.response

data class HistoryResponse(
    val historySeq: Long,
    val matchResult: String,
    val matchUserExp: Int,
    val matchUserNickname: String,
    val matchUserTitleName: String,
    val userUid: String
)