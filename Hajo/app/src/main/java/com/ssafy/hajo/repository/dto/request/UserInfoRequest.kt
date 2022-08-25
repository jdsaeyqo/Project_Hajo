package com.ssafy.hajo.repository.dto.request

data class UserInfoRequest(
    val skinSeq: Long = 1L,
    val titleSeq: Long = 1L,
    val userAttend: Int = 0,
    val userAttendstreak: Int = 0,
    val userAttendtoday: Boolean = false,
    val userComment: String = "",
    val userCpoint: Int = 0,
    val userDdtask: Int = 0,
    val userDltask: Int = 0,
    val userDraw: Int = 0,
    val userEmail: String,
    val userExp: Int = 0,
    val userImg: String = "https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/hajo_icon.png?alt=media&token=5e8f5069-805b-4c3e-9930-30d4a7e44893",
    val userLogintype: String = "",
    val userLose: Int = 0,
    val userNickname: String = "",
    val userOnmatch: Boolean = false,
    val userPoint: Int = 0,
    val userTdtask: Int = 0,
    val userTttask: Int = 0 ,
    val userUid: String = "",
    val userWin: Int = 0,
    val userWinstreak: Int = 0,
    val userWinwin: Int = 0
)