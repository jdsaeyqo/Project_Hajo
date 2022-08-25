package com.ssafy.hajo.repository.dto.response

data class ShareHomeHeader(
    val popularList : ArrayList<ShareResponse>,
    val recentList : ArrayList<ShareResponse>,
    val myList : ArrayList<ShareResponse>,
    val likeList : ArrayList<ShareResponse>,
)
