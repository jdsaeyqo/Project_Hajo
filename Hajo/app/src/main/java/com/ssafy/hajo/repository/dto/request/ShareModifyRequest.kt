package com.ssafy.hajo.repository.dto.request

data class ShareModifyRequest(
    val grandPlanSeq: Long,
    val shareGrandSummary: String,
    val sharePlanCategory: String,
    val sharePlanContent: String,
    val sharePlanTitle: String,
    val shareSpendingPeroid: Int,
    val sharplanSeq: Long,
    val wrtUid: String
)