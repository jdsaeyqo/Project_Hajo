package com.ssafy.hajo.repository.dto.response

import com.ssafy.hajo.repository.dto.TaskDto

data class HomeResponse(
    val grandplanSeq: Long,
    val grandplanTitle: String,
    val isMatch: Boolean,
    val midplanSeq : Long,
    val midplanTitle: String,
    val smallplanSeq: Long,
    val subDto: List<TaskDto>
)
