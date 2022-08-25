package com.ssafy.hajo.repository.dto.response


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class BoastMoreResponse(
    val wrtUid : String,
    val wrtNickname : String,
    val wrtLevel : Int,
    val wrtTitle : String,
    val wrtImg : String,
    val boastSeq : Long,
    val boastImg : String,
    val boastTitle : String,
    val boastContent : String,
    val boastWrttime : String,
    var boastLikecount : Int,
    var userIslike : Boolean
) : Parcelable
