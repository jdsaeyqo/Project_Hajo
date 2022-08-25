package com.ssafy.hajo.repository.dto.request

data class ShareAddRequest(
    val wrtUid : String,
    var grandplanSeq: Long,
    val shrplanSeq : Long,
    var shrplanTitle: String,
    var shrplanContent: String,
    var shrplanCategory : String,
    var shrplanPeriod : Int,
    var shrplanSummary : String
)
