package com.ssafy.hajo.repository.dto.response

data class TaskResponse(
    val grandPlanName: String,
    val midPlanName: String,
    val tasks: List<String>
)