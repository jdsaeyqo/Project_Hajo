import android.net.Uri
import android.util.Log
import android.view.textservice.TextInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import com.ssafy.hajo.plan.*
import com.ssafy.hajo.repository.dao.DiaryRepository
import com.ssafy.hajo.repository.dao.PlanDetailRepository
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class DiaryViewModel: ViewModel() {
    private val diaryRepository: DiaryRepository by lazy {
        DiaryRepository()
    }

    private var _diaryList = MutableLiveData<MutableList<DiaryHome>>() // 홈 화면 다이어리 리스트
    val diaryList: LiveData<MutableList<DiaryHome>>
        get() = _diaryList // 홈화면 다이어리 리스트

    private var _addDiary = MutableLiveData<Int>() // 다이어리 추가 콜백
    val addDiary: LiveData<Int>
        get() = _addDiary // 다이어리 추가 콜 백

    private var _diaryDelete = MutableLiveData<Int>() // 다이어리 삭제 콜백
    val diaryDelete: LiveData<Int>
        get() = _diaryDelete // 다이어리 삭제 콜 백

    private var _diaryInfo = MutableLiveData<Diary>() // 다이어리 seq 조회 콜백
    val diaryInfo: LiveData<Diary>
        get() = _diaryInfo // 다이어리 seq 조회 콜백

    private var _location = MutableLiveData<Int>() // 오브젝트 위치 수정 콜백
    val location: LiveData<Int>
        get() = _location // 오브젝트 위치 수정 콜백

    private var _addObject = MutableLiveData<Int>() // 오브젝트 추가 콜백
    val addObject: LiveData<Int>
        get() = _addObject // 오브젝트 추가 콜백

    private var _deleteObject = MutableLiveData<Int>() // 오브젝트 삭제 콜백
    val deleteObject: LiveData<Int>
        get() = _deleteObject // 오브젝트 삭제 콜백

    private var _updateObjImg = MutableLiveData<Int>() // 오브젝트 이미지 추가 콜백
    val updateObjImg: LiveData<Int>
        get() = _updateObjImg // 오브젝트 이미지 추가 콜백

    private var _EditText = MutableLiveData<DiaryObject>() // 텍스트 편집
    val EditText: LiveData<DiaryObject>
        get() = _EditText // 텍스트 편집

    private var _EditTextResponse = MutableLiveData<Int>() // 텍스트 편집 콜백
    val EditTextResponse: LiveData<Int>
        get() = _EditTextResponse // 텍스트 편집 콜백

    fun setEditText(textObj : DiaryObject) {
        _EditText.value = textObj
    }

    fun resetEditTextResponse() {
        _EditTextResponse.value = 0
    }


    //diary activity
    fun getDiaryInfo(diarySeq: Int) {
        Log.d("diaryViewmodel","getDiaryInfo 시작")

        CoroutineScope(Dispatchers.Main).launch {
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!

            val res: Response<Diary>
            withContext(Dispatchers.IO) {
                Log.d("diaryViewmodel", "서버에서 getDiaryInfo 위한 통신 시작 다이어리 seq ${diarySeq}")
                res = diaryRepository.getDiaryInfo(token, diarySeq) // todo user 바꾸기
                Log.d("diaryViewmodel", "서버에서 getDiaryInfo 위한 통신 끝  반환 값 ${res.body()}")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("diaryViewmodel", "getDiaryInfo 성공 ${res.body()}")
                    _diaryInfo.postValue(res.body())
                } else {
                    Log.d("diaryViewmodel", "getDiaryInfo 실패 또는 없음 ${res.body()}")
                    _diaryInfo.postValue(null) // null 이면 로딩 실패

                }
                Log.d("diaryViewmodel", "getDiaryInfo CoroutineScope 끝")

            }

        }

    }

    //로케이션 변경
    fun saveLocation(objs: MutableList<LocatedObj>) {
        Log.d("diaryViewmodel","saveLocation 시작")

        CoroutineScope(Dispatchers.Main).launch {
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!

            val res: Response<Int>
            withContext(Dispatchers.IO) {
                Log.d("diaryViewmodel", "서버에서 saveLocation 위한 통신 시작 objs ${objs}")
                res = diaryRepository.saveLocation(token, objs) // todo user 바꾸기
                Log.d("diaryViewmodel", "서버에서 saveLocation 위한 통신 끝  반환 값 ${res.body()}")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("diaryViewmodel", "saveLocation 성공 ${res.body()}")
                    _location.postValue(res.body())
                } else {
                    Log.d("diaryViewmodel", "saveLocation 실패 또는 없음 ${res.body()}")
                    _location.postValue(300)
                }
                Log.d("diaryViewmodel", "getUserDiary CoroutineScope 끝")

            }

        }

    }

    // 오브젝트 추가
    fun addObj(newObj: DiaryObject) {
        Log.d("diaryViewmodel","addObj 시작")

        CoroutineScope(Dispatchers.Main).launch {
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!

            val res: Response<Int>
            withContext(Dispatchers.IO) {
                Log.d("diaryViewmodel", "서버에서 addObj 위한 통신 시작 newObj ${newObj}")
                res = diaryRepository.addObj(token, newObj)
                Log.d("diaryViewmodel", "서버에서 addObj 위한 통신 끝  반환 값 ${res.body()}")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("diaryViewmodel", "addObj 성공 ${res.body()}")
                    _addObject.postValue(res.body())
                } else {
                    Log.d("diaryViewmodel", "addObj 실패 또는 없음 ${res.body()}")
                    _addObject.postValue(400)

                }
                Log.d("diaryViewmodel", "addObj CoroutineScope 끝")

            }

        }

    }

    // 오브젝트 제거
    fun deleteObj(objSeq: Int) {
        Log.d("diaryViewmodel","deleteObj 시작")

        CoroutineScope(Dispatchers.Main).launch {
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!

            val res: Response<Int>
            withContext(Dispatchers.IO) {
                Log.d("diaryViewmodel", "서버에서 deleteObj 위한 통신 시작 objSeq ${objSeq}")
                res = diaryRepository.deleteObj(token, objSeq)
                Log.d("diaryViewmodel", "서버에서 deleteObj 위한 통신 끝  반환 값 ${res.body()}")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("diaryViewmodel", "deleteObj 성공 ${res.body()}")
                    _deleteObject.postValue(res.body())
                } else {
                    Log.d("diaryViewmodel", "deleteObj 실패 또는 없음 ${res.body()}")
                    _deleteObject.postValue(400)

                }
                Log.d("diaryViewmodel", "deleteObj CoroutineScope 끝")

            }

        }

    }

    // 오브젝트 이미지 수정
    fun objImgUpdate(objSeq: Int, imgUri: String) {
        Log.d("diaryViewmodel","objImgUpdate 시작")

        CoroutineScope(Dispatchers.Main).launch {
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!

            val hash = HashMap<String, String>()
            hash.set("diaryobjSeq",objSeq.toString())
            hash.set("objpicImg",imgUri)

            val res: Response<Int>
            withContext(Dispatchers.IO) {
                Log.d("diaryViewmodel", "서버에서 objImgUpdate 위한 통신 시작 objSeq ${objSeq}")
                res = diaryRepository.updateObjImg(token, hash)
                Log.d("diaryViewmodel", "서버에서 objImgUpdate 위한 통신 끝  반환 값 ${res.body()}")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("diaryViewmodel", "objImgUpdate 성공 ${res.body()}")
                    _updateObjImg.postValue(res.body())
                } else {
                    Log.d("diaryViewmodel", "objImgUpdate 실패 또는 없음 ${res.body()}")
                    _updateObjImg.postValue(300)

                }
                Log.d("diaryViewmodel", "objImgUpdate CoroutineScope 끝")

            }

        }

    }

    // 오브젝트 텍스트 수정
    fun updateTextObj(obj: DiaryObject) {
        Log.d("diaryViewmodel","updateTextObj 시작")

        CoroutineScope(Dispatchers.Main).launch {
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!

            val res: Response<Int>
            withContext(Dispatchers.IO) {
                Log.d("diaryViewmodel", "서버에서 updateTextObj 위한 통신 시작 DiaryObject ${obj}")
                obj.objtext.objSeq = obj.diaryobjSeq
                res = diaryRepository.updateObjText(token, obj.objtext)
                Log.d("diaryViewmodel", "서버에서 updateTextObj 위한 통신 끝  반환 값 ${res.body()}")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("diaryViewmodel", "updateTextObj 성공 ${res.body()}")
                    _EditTextResponse.postValue(res.body())
                } else {
                    Log.d("diaryViewmodel", "updateTextObj 실패 또는 없음 ${res.body()}")
                    _EditTextResponse.postValue(300)

                }
                Log.d("diaryViewmodel", "updateTextObj CoroutineScope 끝")

            }

        }

    }


    // firebase를 통한 이미지 처리
    private var _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String>
        get() = _imageUri

    init {
        _imageUri.value = ""
    }
    fun updateImage(imageUri: String){
        _imageUri.value = imageUri
    }
    // 사진 수정
    fun updateFireBaseStorage(imageUrl : Uri) {
        Log.d("diaryViewmodel", "updateFireBaseStorage $imageUrl ")
        val storageRef = imageUrl.let { uri ->
            FirebaseStorage.getInstance().reference.child("DiaryImage")
                .child(uri.toString())
        }

        storageRef.putFile(imageUrl)
            .continueWithTask {
                return@continueWithTask storageRef.downloadUrl
            }.addOnSuccessListener { u ->
                updateImage(u.toString())
                Log.d("diaryViewmodel", "updateFireBaseStorage success : $u ")
            }
    }






    // diary home
    fun getUserDiary() {
        Log.d("diaryViewmodel","getUserDiary 시작")

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("diaryViewmodel", "getUserDiaryCoroutineScope 시작")
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!


            val res: Response<MutableList<DiaryHome>>
            withContext(Dispatchers.IO) {
                Log.d("diaryViewmodel", "withContext getUserDiary  시작")

                Log.d("diaryViewmodel", "서버에서 getUserDiary 위한 통신 시작 유저 id ${userId}")
                res = diaryRepository.getUserDiary(token, userId) // todo user 바꾸기
                Log.d("diaryViewmodel", "서버에서 getUserDiary 위한 통신 끝")

                Log.d("diaryViewmodel", "getUserDiary 반환 값 ${res.body()}")

                Log.d("diaryViewmodel", "withContext getUserDiary  끝")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("diaryViewmodel", "getUserDiary 성공 ${res.body()}")
                    _diaryList.postValue(res.body())
                } else {
                    Log.d("diaryViewmodel", "getUserDiary 실패 또는 없음 ${res.body()}")
                    _diaryList.postValue(mutableListOf())

                }

                Log.d("diaryViewmodel", "getUserDiary CoroutineScope 끝")

            }
        }
        Log.d("diaryViewmodel","getUserDiary 끝")

    }

    fun addDiary(diaryType: String, diaryTitle: String) {
        Log.d("diaryViewmodel","addDiary 시작")

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("diaryViewmodel", "addDiaryCoroutineScope 시작")
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!

            var hash = HashMap<String, String>()
            hash.set("uid", userId)
            hash.set("diaryType", diaryType)
            hash.set("diaryTitle", diaryTitle)

            val res: Response<Int>
            withContext(Dispatchers.IO) {
                Log.d("diaryViewmodel", "withContext addDiary  시작")

                Log.d("diaryViewmodel", "서버에서 addDiary 위한 통신 시작 diary ${hash}")
                res = diaryRepository.addDiary(token, hash) // todo user 바꾸기
                Log.d("diaryViewmodel", "서버에서 addDiary 위한 통신 끝")

                Log.d("diaryViewmodel", "addDiary 반환 값 ${res.body()}")

                Log.d("diaryViewmodel", "withContext addDiary  끝")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("diaryViewmodel", "addDiary 성공 ${res.body()}")
                    _addDiary.postValue(res.body())
                } else {
                    Log.d("diaryViewmodel", "addDiary 실패 또는 없음 ${res.body()}")

                }

                Log.d("diaryViewmodel", "addDiary CoroutineScope 끝")

            }
        }
        Log.d("diaryViewmodel","addDiary 끝")

    }

    fun deleteDiary(diarySeq: Int) {
        Log.d("diaryViewmodel","deleteDiary 시작")

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("diaryViewmodel", "deleteDiary CoroutineScope 시작")
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!


            val res: Response<Int>
            withContext(Dispatchers.IO) {
                Log.d("diaryViewmodel", "withContext deleteDiary  시작")

                Log.d("diaryViewmodel", "서버에서 deleteDiary 위한 통신 시작 다이어리 seq ${diarySeq}")
                res = diaryRepository.deleteDiary(token, diarySeq) // todo user 바꾸기
                Log.d("diaryViewmodel", "서버에서 deleteDiary 위한 통신 끝")

                Log.d("diaryViewmodel", "deleteDiary 반환 값 ${res.body()}")

                Log.d("diaryViewmodel", "withContext deleteDiary  끝")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("diaryViewmodel", "deleteDiary 성공 ${res.body()}")
                    _diaryDelete.postValue(res.body())
                } else {
                    Log.d("diaryViewmodel", "deleteDiary 실패 또는 없음 ${res.body()}")
                    _diaryDelete.postValue(-1)

                }

                Log.d("diaryViewmodel", "deleteDiary CoroutineScope 끝")

            }
        }
        Log.d("diaryViewmodel","deleteDiary 끝")

    }


}

data class DiaryHome(
    var diarySeq: Int,
    var diaryTitle: String,
    var diaryType: String
)

data class Diary(
    var diarySeq: Int,
    var userUid: String,
    var diaryTitle: String,
    var diaryType: String,
    var diaryPages: MutableList<Page>
)

data class Page(
    var diarypageSeq: Int,
    var diarypageNum: Int,
    var dirayObjs: MutableList<DiaryObject>
)

data class DiaryObject(
    var diarypageSeq: Int,
    var diaryobjSeq: Int,
    var diaryobjType: String,
    var diaryobjXloc: Long,
    var diaryobjYloc: Long,
    var objpicImg: String,
    var objtext: TextObjectInfo
)

data class TextObjectInfo(
    var objtextSeq: Int = 0,
    var objtextContent: String = "나의 다이어리 이야기를 써보세요!",
    var objtextFont: String = "",
    var objtextSize: Int = 20,
    var objtextColor: String = "#818181",
    var objtextIsbold: Boolean = false,
    var objtextIsitalic: Boolean = false,
    var objSeq: Int = 0

)

data class LocatedObj(
    var diaryobjSeq: Int,
    var diaryobjXloc: Long,
    var diaryobjYloc: Long
)