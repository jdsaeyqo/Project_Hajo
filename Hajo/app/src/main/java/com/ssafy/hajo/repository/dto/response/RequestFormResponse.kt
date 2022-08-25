package com.ssafy.hajo.repository.dto.response

data class RequestFormResponse(
    val matchGrandPlanName: String,
    val matchGrandPlanSeq: Long,
    val matchUserDraw: Int,
    val matchUserExp: Int,
    val matchUserImg: String,
    val matchUserLose: Int,
    val matchUserNickname: String,
    val matchUserTitle: String,
    val matchUserUid: String,
    val matchUserWin: Int,
    val matchUserWinwin: Int,
    val pbchallSeq: Long
)