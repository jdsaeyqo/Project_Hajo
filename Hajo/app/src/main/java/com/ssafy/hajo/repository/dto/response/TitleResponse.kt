package com.ssafy.hajo.repository.dto.response

import java.io.Serializable

data class TitleResponse(
    val titleSeq: Int,
    val userTitleSeq: Int,
    val userUid: String,
    val titleName: String,
    var equipped: Boolean
) : Serializable