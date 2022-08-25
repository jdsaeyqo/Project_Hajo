package com.ssafy.hajo.repository.dto

data class UserDto(
    val skinSeq: Int = 0,
    val titleSeq: Int = 0,
    val userAttend: Int = 0,
    val userAttendstreak: Int = 0,
    val userAttendtoday: Boolean = false,
    val userCpoint: Int = 0,
    val userDdtask: Int = 0,
    val userDltask: Int = 0,
    val userDraw: Int = 0,
    val userEmail: String,
    val userExp: Int = 0,
    val userImg: String ="",
    val userLogintype: String,
    val userLose: Int = 0,
    val userNickname: String,
    val userPoint: Int = 0,
    val userTdtask: Int = 0,
    val userTttask: Int = 0,
    val userUid: String,
    val userWin: Int = 0,
    val userWinstreak: Int = 0,
    val userWinwin: Int = 0,
    val userOnmatch : Boolean = false,
    val userComment : String = ""
)