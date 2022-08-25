package com.ssafy.hajo.repository.dto.response

data class BoastHomeHeader(
    val popularList: ArrayList<BoastHomeResponse>,
    val recentList: ArrayList<BoastHomeResponse>,
    val myList: ArrayList<BoastHomeResponse>,
    val likeList: ArrayList<BoastHomeResponse>
)
