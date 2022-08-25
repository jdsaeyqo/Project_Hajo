package com.ssafy.hajo.repository.dto.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShareResponse(
    val grandplanTitle: String,
    val shrplanBmkcount: Int,
    val shrplanCategory: String,
    val shrplanLikecount: Int,
    val shrplanSeq: Long,
    val shrplanTitle: String,
    val wrtLevel: Int,
    val wrtNickname: String,
    var wrtTitle: String,
    val wrtUid: String
) : Parcelable