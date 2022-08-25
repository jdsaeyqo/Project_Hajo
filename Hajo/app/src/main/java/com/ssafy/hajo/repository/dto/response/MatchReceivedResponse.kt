package com.ssafy.hajo.repository.dto.response

data class MatchReceivedResponse(
    val grandplanTitle: String,
    val matchGrandplanTitle: String,
    val matchPeriod: Int,
    val matchUserExp: Int,
    val matchUserNickname: String,
    val matchUserTitle: String,
    val matchUserUid: String,
    val userUid: String,
    val pschallSeq: Long
)