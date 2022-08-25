package com.ssafy.hajo.repository.dto.response

data class UserResponse(
    var titleName: String,
    val userComment: String,
    val userDraw: Int,
    val userExp: Int,
    val userLose: Int,
    val userNickname: String,
    var userOnmatch: Boolean,
    val userUid: String,
    val userWin: Int,
    val userWinwin: Int,
    val userImg: String
)