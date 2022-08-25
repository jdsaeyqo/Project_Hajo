package com.ssafy.hajo.entity

import android.os.Parcelable
import com.ssafy.hajo.repository.dto.response.MyPageSmallPlanResponse
import kotlinx.parcelize.Parcelize
import java.util.*

data class User(
    val uid: String,
    val nickName: String,
    val level: Int,
    val title: String,
    val winRate: Int
)

data class Request(
    val matchSeq: Long,
    val userUid: String,
    val matchUserUid: String,
    val matchUserNickName: String,
    val matchUserLevel: Int,
    val matchUserTitle: String,
    val matchUserPlanName: String,
    val myPlanName: String,
    val duration: Int
)

data class Match(
    val nowSeq: Long,
    val name: String,
    val startDate: String,
    val duration: Int,
    val result: String
)

data class WeeklyPlan(val smallPlans: List<MyPageSmallPlanResponse>)

data class Theme(
    val tid: Int,
    val name: String,
    val startDate: String,
    val endDate: String,
    var using: Boolean
)

data class Title(val seq: Long, val name: String, var using: Boolean)

//BOAST_SEQ int AI PK
//USER_UID varchar(45)
//USER_NICKNAME varchar(45)
//BOAST_TITLE varchar(45)
//BOAST_CONTENT text
//BOAST_IMG varchar(200)
//BOAST_LIKECOUNT int
//BOAST_AGRCOUNT int
//BOAST_WRTTIME timestamp
//@Parcelize
//data class BoardMore(
//    val userUid : String,
//    val profileImage: String,
//    val nickName: String,
//    val image: String,
//    val title: String,
//    val date: String,
//    val content: String,
//    var like: Boolean,
//    var report: Int
//) : Parcelable
//
//@Parcelize
//data class SharePlan(
//    val title: String,
//    val grandPlan: String,
//    var userTitle: String,
//    val userLevel: String,
//    val nickName: String,
//    val spendingTime: Int,
//    val category: String,
//    val content: String,
//    var like: Int,
//    var bookmark: Int
//) : Parcelable

data class FAQ(
    val idx : Int,
    val title : String,
    val body : String,
    var clicked : Boolean
)

data class Tutorial(
    val img : Int,
    val text : String
)

data class Quest(
    val name : String,
    val entire : Int,
    val doing : Int,
    val done : Boolean
)

data class GrandPlan(
    val planSeq : Long,
    val planTitle : String
)

data class UserTask(
    val userDdtask: String,
    val userDltask: String,
    val userTdtask: String,
    val userTttask: String
)