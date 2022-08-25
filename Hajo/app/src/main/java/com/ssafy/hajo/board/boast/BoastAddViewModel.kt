package com.ssafy.hajo.board.boast

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.ssafy.hajo.repository.dao.BoardRepository
import com.ssafy.hajo.repository.dto.request.BoastAddRequest
import com.ssafy.hajo.repository.dto.request.BoastModifyRequest
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.TodayStringUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BoastAddViewModel : ViewModel() {

    private val userPrefs = GlobalApplication.userPrefs
    val uid = userPrefs.getString("uid", "")
    val jwt = userPrefs.getString("jwt", "")
    private val boardRepository by lazy {
        BoardRepository()
    }

    private var _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private var _content = MutableLiveData<String>()
    val content: LiveData<String>
        get() = _content

    private var _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String>
        get() = _imageUri

    private var _validate = MutableLiveData<Boolean>()
    val validate: LiveData<Boolean>
        get() = _validate

    private var _result = MutableLiveData<Boolean>()
    val result: LiveData<Boolean>
        get() = _result

    private var _onProgress = MutableLiveData<Boolean>()
    val onProgress: LiveData<Boolean>
        get() = _onProgress

    init {
        _title.value = ""
        _content.value = ""

    }

    val titleInputText = MutableLiveData<String>()
    val contentInputText = MutableLiveData<String>()

    fun getUpdate() {

        val updatedTitle = titleInputText.value.toString()
        val updateContent = contentInputText.value.toString()
        val updateImage = imageUri.value.toString()
        Log.d("getUpdate", "$updatedTitle $updateContent")

        if (updatedTitle == "null" || updatedTitle == "" || updateContent == "null" || updateContent == "" || updateImage == "" || updateImage == "null") {
            _validate.value = false
            return
        }
        _title.value = updatedTitle
        _content.value = updateContent
        _validate.value = true

    }

    fun updateImage(image: String) {
        _imageUri.value = image
    }

    fun updateFireBaseStorage(imageUrl: Uri, mode: Int?, modifyImageUri: String) {
        val str = System.currentTimeMillis()

        val path = "$uid $str"

        Log.d("TaskAddViewModel", "updateFireBaseStorage: $path")
        val storageRef = imageUrl.let { uri ->
            FirebaseStorage.getInstance().reference.child("BoardImage")
                .child(path)
        }

        storageRef.putFile(imageUrl)
            .addOnProgressListener {
                _onProgress.value = true
            }.continueWithTask {
                return@continueWithTask storageRef.downloadUrl
            }.addOnSuccessListener { u ->
                updateImage(u.toString())
                Log.d("TaskAddViewModel", "taskAdd: $u ")
            }
    }

    fun upload(mode: Int,boastSeq : Long) {

        viewModelScope.launch(Dispatchers.Main) {
            if (mode == 0) {
                val boast = BoastAddRequest(uid!!, title.value!!, content.value!!, imageUri.value!!)
                Log.d("BoastAddViewModel", "upload: $boast")
                val res = boardRepository.addBoast(jwt!!, boast)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("BoastAddViewModel", "res: ${res.body()}")
                }
                _result.value = true
            } else {
                val boast = BoastModifyRequest(uid!!, boastSeq ,title.value!!, content.value!!, imageUri.value!!)
                Log.d("BoastAddViewModel", "Modifyupload: $boast")
                val res = boardRepository.modifyBoast(jwt!!, boast)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("BoastAddViewModel", "Modifyupload: ${res.body()}")
                }
                _result.value = true
            }

        }


    }
}