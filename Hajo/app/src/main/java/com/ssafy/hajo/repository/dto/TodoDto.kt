package com.ssafy.hajo.repository.dto

data class TodoDto(val largePlan : String, val mediumPlan : String, val smallPlan : String, val taskList : MutableList<TaskDto>)

