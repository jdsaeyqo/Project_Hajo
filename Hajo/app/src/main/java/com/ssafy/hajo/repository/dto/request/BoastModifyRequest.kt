package com.ssafy.hajo.repository.dto.request

data class BoastModifyRequest(
    val wrtUid : String,
    val boastSeq : Long,
    val boastTitle : String,
    val boastContent : String,
    val boastImg : String
) {
}