package com.ssafy.hajo.repository.dto.request

data class TaskAddRequest(
    val plansmallSeq: Long,
    val title: String,
    val memo: String,
    var img: String,
    val alarmTime: String
)


