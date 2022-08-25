package com.ssafy.hajo.repository.dto

import com.google.android.gms.tasks.Tasks

data class MPlanDto(
    val bPlanId: String, // 소속된 대플랜
    val startDay: String, // 중플랜 시작일
    val endDay: String, // 중플랜 끝 일
    val mPlanName: String, // 중플랜 이름
    val mPlanDescription: String, // 중플랜 설명
    val mPlanColor: String,
    val dayTasks: List<TaskDto> // 해당 일자에 중플랜에 등록된 task들(체크여부, 내용, 알람,메모,사진)
)