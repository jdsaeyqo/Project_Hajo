package com.ssafy.hajo.repository.dao

import Diary
import DiaryHome
import DiaryObject
import LocatedObj
import TextObjectInfo
import android.util.Log
import com.ssafy.hajo.network.DiaryService
import com.ssafy.hajo.network.PlanDetailService
import com.ssafy.hajo.plan.BPlan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class DiaryRepository {

    // 다이어리
    suspend fun getDiaryInfo (token:String, diarySeq: Int): Response<Diary> { // 특정 다이어리 조회
        val result = withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "getDiaryInfo 서버 시작")
            val diaryList = DiaryService.retrofitService.getDiaryInfo(token, diarySeq)
            Log.d("DiaryRepository", "getDiaryInfo 서버 통신 후")
            diaryList
        }
        return result
    }

    // 다이어리 오브젝트 위치 수정
    suspend fun saveLocation (token:String, objs: MutableList<LocatedObj>): Response<Int> {
        val result = withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "saveLocation 서버 시작")
            val diaryList = DiaryService.retrofitService.saveLocation(token, objs)
            Log.d("DiaryRepository", "saveLocation 서버 통신 후")
            diaryList
        }
        return result
    }

    // 오브젝트 추가
    suspend fun addObj (token:String, newObj: DiaryObject): Response<Int> {
        val result = withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "addObj 서버 시작")
            val diaryList = DiaryService.retrofitService.addObj(token, newObj)
            Log.d("DiaryRepository", "addObj 서버 통신 후")
            diaryList
        }
        return result
    }

    // 오브젝트 삭제
    suspend fun deleteObj (token:String, objSeq: Int): Response<Int> {
        val result = withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "deleteObj 서버 시작")
            val diaryList = DiaryService.retrofitService.deleteObj(token, objSeq)
            Log.d("DiaryRepository", "deleteObj 서버 통신 후")
            diaryList
        }
        return result
    }

    // 오브젝트 img 업데이트
    suspend fun updateObjImg (token:String, hash: HashMap<String, String>): Response<Int> {
        val result = withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "updateObjImg 서버 시작")
            val diaryList = DiaryService.retrofitService.updateObjImg(token, hash)
            Log.d("DiaryRepository", "updateObjImg 서버 통신 후")
            diaryList
        }
        return result
    }

    // 오브젝트 text 업데이트
    suspend fun updateObjText (token:String, obj: TextObjectInfo): Response<Int> {
        val result = withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "updateObjText 서버 시작")
            val diaryList = DiaryService.retrofitService.updateObjText(token, obj)
            Log.d("DiaryRepository", "updateObjText 서버 통신 후")
            diaryList
        }
        return result
    }






    // 다이어리 홈
    suspend fun getUserDiary (token:String, uid:String): Response<MutableList<DiaryHome>> {
        val result = withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "getUserDiary 서버 시작")
            val diaryList = DiaryService.retrofitService.getUserDiary(token, uid)
            Log.d("DiaryRepository", "getUserDiary 서버 통신 후")
            diaryList
        }
        return result
    }

    suspend fun addDiary (token:String, hash: HashMap<String, String>): Response<Int> {
        val result = withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "addDiary 서버 시작")
            val diaryList = DiaryService.retrofitService.addDiary(token, hash)
            Log.d("DiaryRepository", "addDiary 서버 통신 후")
            diaryList
        }
        return result
    }

    suspend fun deleteDiary (token:String, diarySeq: Int): Response<Int> {
        val result = withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "deleteDiary 서버 시작")
            val diaryList = DiaryService.retrofitService.deleteDiary(token, diarySeq)
            Log.d("DiaryRepository", "deleteDiary 서버 통신 후")
            diaryList
        }
        return result
    }
}