package com.ssafy.hajo.repository.dto.response

data class MatchNowResponse(
    val nowSeq: Long,
    val grandplanSeq: Int,
    val grandplanTitle: String,
    val matchEnddate: String,
    val matchGrandplanSeq: Int,
    val matchPlanStatus: String,
    val matchResult: String,
    val matchStartdate: String,
    val matchUserNickname: String,
    val matchUserProgress: String,
    val matchUserUid: String,
    val planStatus: String,
    val userNickname: String,
    val userProgress: String,
    val userUid: String
)