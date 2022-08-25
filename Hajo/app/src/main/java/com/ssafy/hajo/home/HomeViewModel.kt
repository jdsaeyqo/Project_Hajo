package com.ssafy.hajo.home

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.ssafy.hajo.repository.dao.HomeRepository
import com.ssafy.hajo.repository.dto.TaskDto
import com.ssafy.hajo.repository.dto.request.TaskAddRequest
import com.ssafy.hajo.repository.dto.response.HomeResponse
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.TodayStringUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class HomeViewModel : ViewModel() {

    private val _todoList = MutableLiveData<MutableList<HomeResponse>>()
    val todoList: LiveData<MutableList<HomeResponse>>
        get() = _todoList

    private val _taskCompletedResult = MutableLiveData<String>()
    val taskCompletedResult: LiveData<String>
        get() = _taskCompletedResult

    private var _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String>
        get() = _imageUri

    private val userPrefs = GlobalApplication.userPrefs
    val jwt = userPrefs.getString("jwt", "")
    val uid = userPrefs.getString("uid", "")

    private val homeRepository by lazy {
        HomeRepository()
    }

    init {
        _taskCompletedResult.value = ""
    }

    fun updateImage(imageUri: String){
        _imageUri.value = imageUri
    }

    fun taskAdd(request: TaskAddRequest) {

        viewModelScope.launch(Dispatchers.Main) {
            Log.d("TaskAddViewModel", "imageUri: ${imageUri.value} ")
            val req = request
            req.img = imageUri.value!!

            Log.d("TaskAddViewModel", "taskAdd: $req ")

            val res = homeRepository.taskAdd(jwt!!, req)

            if (res.isSuccessful && res.body() != null) {
                Log.d("TaskAddViewModel", "taskAdd: ${res.body()}")
            }

        }

    }


    fun getTodoList() {

        //서버에서 홈화면 채울 리스트 가져옴

        val today = TodayStringUtil.today

        viewModelScope.launch(Dispatchers.Main) {

            val req = HashMap<String, String>()
            req["uid"] = uid!!
            req["todayDate"] = today
            val res = homeRepository.getHomeList(jwt!!, req)

            if (res.isSuccessful && res.body() != null) {
                Log.d("HomeViewModel", "getTodoList:${res.body()}")
                val todoList = res.body()
                _todoList.postValue(todoList)
            }
        }


    }

    fun completeTask(taskSeq: Long) {
        viewModelScope.launch(Dispatchers.Main) {

            val res = homeRepository.taskCompleted(jwt!!, taskSeq)

            if (res.isSuccessful && res.body() != null) {
                Log.d("HomeViewModel", "completeTask: ${res.body()}")
                val result = res.body()!!
                val msg = result["msg"]
                _taskCompletedResult.postValue(msg)
            } else {
                Log.d("HomeViewModel", "ELSE : completeTask: ${res.errorBody()?.string()}")
            }
        }
    }

    fun updateFireBaseStorage(imageUrl : Uri) {
        val now = System.currentTimeMillis()
        val path = "$uid $now"

        val storageRef = imageUrl.let { uri ->
            FirebaseStorage.getInstance().reference.child("TaskImage")
                .child(path)
        }

        storageRef.putFile(imageUrl)
            .continueWithTask {
                return@continueWithTask storageRef.downloadUrl
            }.addOnSuccessListener { u ->
                updateImage(u.toString())
                Log.d("TaskAddViewModel", "taskAdd: $u ")
            }
    }



}