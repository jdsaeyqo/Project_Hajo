package com.ssafy.hajo.util


import com.ssafy.hajo.repository.dto.MemoDto
import org.json.JSONArray

class MemoPrefUtil {
    companion object {
        private val sharedPreferences = GlobalApplication.memoPrefs

        fun getSharedPreferenceToList(): MutableList<MemoDto> {
            val getSharedMemo = sharedPreferences.getString("message", "")
            val resultArr = mutableListOf<MemoDto>()
            return if (getSharedMemo == "") {
                resultArr
            } else {
                val arrJson = JSONArray(getSharedMemo)

                for (i in 0 until arrJson.length()) {
                    val msg = arrJson.optString(i)
                    resultArr.add(MemoDto(msg))
                }
                resultArr
            }


        }

        fun setListToSharedPreference(memoList: MutableList<MemoDto>) {
            val jsonArray = JSONArray()
            for (i in memoList.indices) {
                jsonArray.put(memoList[i].memo)
            }
            val result = jsonArray.toString()
            sharedPreferences.edit().putString("message", result).apply()
        }
    }


}