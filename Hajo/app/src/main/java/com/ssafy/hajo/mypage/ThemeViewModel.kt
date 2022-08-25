package com.ssafy.hajo.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.hajo.entity.Theme
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.util.GlobalApplication
import java.util.*

class ThemeViewModel: ViewModel() {
    private val _themes = MutableLiveData<MutableList<Theme>>()
    val themes: LiveData<MutableList<Theme>>
        get() = _themes

    lateinit var theme: Theme

    private val token = GlobalApplication.userPrefs.getString("jwt","")!!
    private val userId = GlobalApplication.userPrefs.getString("uid","")!!

    private val userRepository by lazy {
        UserRepository()
    }

    init {
        val lists = mutableListOf<Theme>()
        lists.add(Theme(1,"테마1", "22.07.20", "22.07.27", true))
        lists.add(Theme(1,"테마2", "22.07.23", "22.07.30", false))
        lists.add(Theme(1,"테마3", "22.08.01", "22.08.07", false))
        _themes.value = lists
        Log.d("tttt", "init: ")
    }

    fun changeTheme(before: Int, after: Int) {
        val beforeTheme = themes.value?.get(before)!!
        beforeTheme.using = false
        val afterTheme = themes.value?.get(after)!!
        afterTheme.using = true
        themes.value?.set(before, beforeTheme)
        themes.value?.set(after, afterTheme)
        theme = afterTheme

        val hm = HashMap<String, Any>()
        hm["userUid"] = userId
        hm["titleSeq"] = theme.tid
    }
}