package com.ssafy.hajo.repository.dto.response

data class QuestResponse(
    val archiveCondparam: String,
    val archiveName: String,
    val archiveRewardseq: Int,
    val archiveRewardtype: String,
    val archiveSeq: Int,
    val cond: Int,
    val rewardName: String
)