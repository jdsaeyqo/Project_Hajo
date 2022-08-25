package com.ssafy.hajo.repository.dto.response

data class MyQuestResponse(
    val userArchiveSeq: Long,
    val archiveName: String,
    val archiveCondparam: String,
    val cond: Int,
    val usercond: Int,
    val archiveRewardtype: String,
    val archiveRewardseq: Long,
    val rewardName: String,
    val userArchiveIsdone: Boolean,
    val userArchiveIsreceived: Boolean

)
