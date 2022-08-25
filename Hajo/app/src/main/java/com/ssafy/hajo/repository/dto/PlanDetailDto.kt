package com.ssafy.hajo.repository.dto

data class PlanDetailDto(
    val userUid: String,
    val bPlanStartday: String, // 이전은 회색, 현재 날짜 이후면 StartDay 보여주기
    val bPlanEndday: String, // 이후는 회색, 선택 안 됨
    val bPlanColor: String,
    val bPlanDescription: String,
    val daysOfMPlans: List<String> // Middle Plan이 존재하는 날짜, 컬러, 태스크 존재 여부
)
