package com.ssafy.hajo.repository.dto.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ShareDetailResponse(
    val shrplanSeq : Long,
    val wrtUid : String,
    val wrtNickname: String,
    val wrtLevel: Int,
    val wrtTitle: String,
    var wrtImg : String,
    var userIslike : Boolean,
    var userIsbmk : Boolean,
    val grandplanSeq : Long,
    val grandplanTitle: String,
    val shrplanTitle : String,
    val shrplanContent : String,
    val shrplanCategory : String,
    val shrplanPeriod : Int,
    val shrplanSummary : String,
    var shrplanLikecount : Int,
    var shrplanBmkcount : Int,
    val shrplanWrttime : String,

    ) : Parcelable
