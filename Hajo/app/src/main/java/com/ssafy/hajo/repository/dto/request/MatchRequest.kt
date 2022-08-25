package com.ssafy.hajo.repository.dto.request

data class MatchRequest(
    val grandplanSeq: Long,
    val matchGrandplanSeq: Long,
    val matchPeriod: Int,
    val matchUserUid: String,
    val userUid: String
)