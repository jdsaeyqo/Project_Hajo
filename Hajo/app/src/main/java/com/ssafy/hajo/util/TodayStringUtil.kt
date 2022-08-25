package com.ssafy.hajo.util

import java.text.SimpleDateFormat
import java.util.*

object TodayStringUtil {

    private val longNow = System.currentTimeMillis()
    private val tDate = Date(longNow)

    val tDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko", "KR"))
    val today = tDateFormat.format(tDate)

}