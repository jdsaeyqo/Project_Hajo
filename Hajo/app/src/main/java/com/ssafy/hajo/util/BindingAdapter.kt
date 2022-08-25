package com.ssafy.hajo.util

import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@BindingAdapter("app:winRate")
fun setWinRate(textView: TextView, rate: Int) {
    textView.setText("승률 $rate%")
}

@BindingAdapter("app:level")
fun setLevel(textView: TextView, exp: Int) {
    textView.setText("Lv.${exp / 100 + 1}")
}

@BindingAdapter("app:duration")
fun setDuration(textView: TextView, duration: Int) {
    textView.setText("${duration}일")
}

@BindingAdapter("app:title")
fun setTitle(textView: TextView, title: String) {
    textView.setText("<$title>")
}

@BindingAdapter("app:history")
fun setHistory(textView: TextView, history: String) {
    val arr = history.split(",")
    textView.setText("${arr.get(0)}윈윈 ${arr.get(1)}승 ${arr.get(2)}패")
}

@BindingAdapter("app:setPlanName")
fun setPlanName(textView: TextView, name: String) {
    textView.setText("• $name")
}