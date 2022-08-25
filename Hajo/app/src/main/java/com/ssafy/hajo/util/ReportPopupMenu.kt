package com.ssafy.hajo.util

import android.content.Context
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import com.ssafy.hajo.R

class ReportPopupMenu(
    val context: Context,
    val view: View,
    val boardReportListener: (Long) -> Unit,
    val boardBlockListener: (Long) -> Unit,
    val userReportListener: (String) -> Unit,
    val userBlockListener: (String) -> Unit

) : PopupMenu(context, view) {

    fun showPopup() {
        val menuInflater = MenuInflater(context)
        menuInflater.inflate(R.menu.option_menu, menu)
        show()
        setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.board_report -> {
                    boardReportListener(0L)
                    return@setOnMenuItemClickListener true
                }
                R.id.board_block -> {
                    boardBlockListener(0L)
                    return@setOnMenuItemClickListener true
                }
                R.id.user_report -> {
                    userReportListener("")
                    return@setOnMenuItemClickListener true
                }
                R.id.user_block -> {
                    userBlockListener("")
                    return@setOnMenuItemClickListener true
                }

                else -> {
                    return@setOnMenuItemClickListener false
                }

            }
        }

    }
}