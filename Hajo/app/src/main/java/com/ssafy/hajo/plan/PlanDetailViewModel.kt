package com.ssafy.hajo.plan

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ssafy.hajo.repository.dao.PlanDetailRepository
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.GlobalApplication.Companion.userPrefs
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.Serializable
import java.time.LocalDate


class PlanDetailViewModel: ViewModel() {
    private val planDetailRepository: PlanDetailRepository by lazy {
        PlanDetailRepository()
    }

    private var _bPlansForSpinner = MutableLiveData<MutableList<BPlanSpinner>>() // 스피너의 대플랜 id, 이름들
    val bPlansForSpinner: LiveData<MutableList<BPlanSpinner>>
        get() = _bPlansForSpinner // 대플랜 정보들 가져옴

    private var _startDay = MutableLiveData<String>()
    val startDay: LiveData<String>
        get() = _startDay

    private var _endDay = MutableLiveData<String>()
    val endDay: LiveData<String>
        get() = _endDay

    private var _bColor = MutableLiveData<String>()
    val bColor: LiveData<String>
        get() = _bColor

    private var _bPlan1 = MutableLiveData<BPlan>()
    val bPlan1: LiveData<BPlan>
        get() = _bPlan1

    private var _littlePlanInfo = MutableLiveData<LittlePlanInfo>() // 중플랜과 소플랜, 태스크 정보들
    val littlePlanInfo: LiveData<LittlePlanInfo>
        get() = _littlePlanInfo

    private var _taskList = MutableLiveData<MutableList<Task>>() // 소플랜의 태스크 리스트
    val taskList: LiveData<MutableList<Task>>
        get() = _taskList

    private var _LowPlanUse = MutableLiveData<MutableList<LowPlanInfoUse>>()
    val lowPlanUse: LiveData<MutableList<LowPlanInfoUse>>
        get() = _LowPlanUse

    private var _planSeq = MutableLiveData<Int>() // 대플랜 추가 후 변경 할 seq
    val planSeq: LiveData<Int>
        get() = _planSeq

    private var _taskDone = MutableLiveData<Int>() // 태스크 완료 후 반환
    val taskDone: LiveData<Int>
        get() = _taskDone

    private var _mPlanName = MutableLiveData<String>()
    val mPlanName: LiveData<String>
        get() = _mPlanName

    private var _mPlanDes = MutableLiveData<String>()
    val mPlanDes: LiveData<String>
        get() = _mPlanDes

    private var _deleteMplan = MutableLiveData<Boolean>()
    val deleteMplan: LiveData<Boolean>
        get() = _deleteMplan

    private var _taskResponse = MutableLiveData<Int>() // 0이면 추가 성공 // 1이면 삭제 성공 // 2이면 수정 성공
    val taskResponse: LiveData<Int>
        get() = _taskResponse

    private var _mPlanColor = MutableLiveData<String>() // 중플랜 색 저장
    val mPlanColor: LiveData<String>
        get() = _mPlanColor

    fun setTaskResponse(data: Int) {
        _taskResponse.value = data
    }

    fun setLowPlanUseList(list: MutableList<LowPlanInfoUse>) {
        _LowPlanUse.postValue(list)
    }

    fun setTaskList(list: MutableList<Task>) {
        _taskList.value = list
    }

    init {
        _mPlanColor.value = "#FFFFBD1F"
    }

    fun setMPlanColor(c: String) {
        _mPlanColor.value = c
    }

    // 서버에서 현재 유저의 모든 대플랜 리스트 가져오기, 없으면 기본 세팅
    fun setSpinnerList() {
         //토큰과 유저ID는 프리퍼런스에서 가져와서 넘겨 준다.
        Log.d("planDetailViewModel", "setSpinnerList 시작")

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("planDetailViewModel", "setSpinnerListCoroutineScope 시작")
            val token = userPrefs.getString("jwt", "")!!
            val userId = userPrefs.getString("uid", "")!!
            Log.d("planDetailViewModel", "setSpinnerListCoroutinewithContext 직전")
            withContext(Dispatchers.IO) {
                Log.d("planDetailViewModel", "setSpinnerListCoroutinewithContext 시작")
                val res = planDetailRepository?.getBPlanForSpinners(token, userId)
                Log.d("planDetailViewModel", "setSpinnerListCoroutinewithContext 끝 ${res}")
                _bPlansForSpinner.postValue(res)
            }

            Log.d("planDetailViewModel", "setSpinnerListCoroutineScope 끝")

        }

        Log.d("planDetailViewModel", "setSpinnerList  끝")

    }


    // 특정 대플랜 id로 해당 대플랜의 정보, 달력을 위한 일자별 정보를 가져옴
    fun getBplanInfo(bPlanID: Int) {
        Log.d("planDetailViewModel","getBplanInfo 시작")

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("planDetailViewModel", "getBplanInfoCoroutineScope 시작")
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = userPrefs.getString("jwt", "")!!
            val userId = userPrefs.getString("uid", "")!!


            val res: Response<BPlan>
            withContext(Dispatchers.IO) {
                Log.d("planDetailViewModel", "withContext getBplanInfo  시작")


                Log.d("planDetailViewModel", "서버에서 getBplanInfo 위한 통신 시작 플랜 id ${bPlanID}")
                res = planDetailRepository.getBPlanInfo(token, bPlanID)
                Log.d("planDetailViewModel", "서버에서 getBplanInfo 위한 통신 끝")

                Log.d("planDetailViewModel", "getBplanInfo 반환 값 ${res.body()}")

                Log.d("planDetailViewModel", "withContext getBplanInfo  끝")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("planDetailViewModel", "getBplanInfo 성공 ${res.body()}")
                    Log.d("planDetailViewModel", "getBplanInfo 성공 ${res.body()!!.subDto}")
                    _bPlan1.postValue(res.body())
                } else {
                    Log.d("planDetailViewModel", "getBplanInfo 실패 또는 없음")
                    _bPlan1.postValue(BPlan(
                        userId,
                        0,
                        "플랜 없음",
                        "none",
                        "시작날짜",
                        "종료날짜",
                        "설명 없음",
                        mutableListOf<LowPlanInfo>()
                    )) // 없으면
                }

                Log.d("planDetailViewModel", "getBplanInfoCoroutineScope 끝")

            }
        }
        Log.d("planDetailViewModel","getBplanInfo 끝")

    }

    fun getDayPlanInfo(smallplanSeq: Int) {
        Log.d("planDetailViewModel1","getDayPlanInfo 시작")
        if(smallplanSeq == -1) {
            val list = LittlePlanInfo(-1,-1,"없음","없음","","","0"
            , 0, mutableListOf())
            _littlePlanInfo.postValue(list)

            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("planDetailViewModel1", "getDayPlanInfo CoroutineScope 시작")
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = GlobalApplication.userPrefs.getString("jwt", "")!!
            val userId = GlobalApplication.userPrefs.getString("uid", "")!!


            withContext(Dispatchers.IO) {
                Log.d("planDetailViewModel1", "withContext getDayPlanInfo  시작 planSeq ${smallplanSeq}")


                Log.d("planDetailViewModel1", "서버에서 getDayPlanInfo 위한 통신 시작")

                val res = planDetailRepository.getDayPlanInfo(token, smallplanSeq)
                Log.d("planDetailViewModel1", "서버에서 getDayPlanInfo 위한 통신 끝")

                Log.d("planDetailViewModel1", "getDayPlanInfo 반환 값 ${res.body()}")

                Log.d("planDetailViewModel1", "withContext getDayPlanInfo  끝")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("planDetailViewModel1", "getDayPlanInfo 성공 ${res.body()}")
                    Log.d("planDetailViewModel1", "getDayPlanInfo 성공 ${res.body()!!.plantaskList}")
                    _littlePlanInfo.postValue(res.body())
                } else {
                    Log.d("planDetailViewModel1", "getDayPlanInfo 실패 또는 없음")
                }

                Log.d("planDetailViewModel1", "getDayPlanInfo CoroutineScope 끝")

            }
        }
        Log.d("planDetailViewModel1","getDayPlanInfo 끝")

    }

    fun getTaskDetail(smallplanSeq: Int) {
        Log.d("planDetailViewModel","getTaskDetail 시작")

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("planDetailViewModel", "getTaskDetailCoroutineScope 시작")
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = userPrefs.getString("jwt", "")!!
            val userId = userPrefs.getString("uid", "")!!


            val res: Response<Task>
            withContext(Dispatchers.IO) {
                Log.d("planDetailViewModel", "withContext getTaskDetail  시작")


                Log.d("planDetailViewModel", "서버에서 getTaskDetail 위한 통신 시작")
                res = planDetailRepository.getTaskDetail(token, smallplanSeq)
                Log.d("planDetailViewModel", "서버에서 getTaskDetail 위한 통신 끝")

                Log.d("planDetailViewModel", "getTaskDetail 반환 값 ${res.body()}")

                Log.d("planDetailViewModel", "withContext getTaskDetail  끝")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("planDetailViewModel", "getTaskDetail 성공 ${res.body()}")
//                    _bPlan1.postValue(res.body())
                } else {
                    Log.d("planDetailViewModel", "getTaskDetail 실패 또는 없음")
//                    _bPlan1.postValue(BPlan(
//                        userId,
//                        0,
//                        "플랜 없음",
//                        "none",
//                        "시작날짜",
//                        "종료날짜",
//                        "설명 없음",
//                        mutableListOf<LowPlanInfo>()
//                    )) // 없으면
                }

                Log.d("planDetailViewModel", "getTaskDetailCoroutineScope 끝")

            }
        }
        Log.d("planDetailViewModel","getTaskDetailInfo 끝")

    }

    // 대플랜 이름을 업데이트 한다.
    fun updateBPlantitle(bPlanID: Int, title: String) {
        CoroutineScope(Dispatchers.Main).launch {
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = userPrefs.getString("jwt", "")!!
//            val userId = userPrefs.getString("uid","")!!

            val hashMap = HashMap<String, Any>()
            hashMap["planSeq"] = bPlanID
            hashMap["updateContent"] = title
            hashMap["updateType"] = "title"


            withContext(Dispatchers.IO) {
                val res = planDetailRepository.updateBPlanDescription(token, hashMap)

                if (res.isSuccessful && res.body() != null) {
                    if(res.body() == 0) {
                        Log.d("planViewModel", "updateBPlantitle 성공 ${res.body()}")
                        setSpinnerList()
                    }
                } else {
                    Log.d("planViewModel", "updateBPlantitle 실패 또는 없음")
                    _bPlan1.value!!.grandplanTitle = "대플랜 이름 입력"
                }
            }
        }
    }

    // 대플랜 설명을 업데이트 한다.
    fun updateBPlanDescription(bPlanID: Int, des: String) {
        CoroutineScope(Dispatchers.Main).launch {
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = userPrefs.getString("jwt", "")!!
//            val userId = userPrefs.getString("uid","")!!

            val hashMap = HashMap<String, Any>()
            hashMap["planSeq"] = bPlanID
            hashMap["updateContent"] = des
            hashMap["updateType"] = "desc"


            withContext(Dispatchers.IO) {
                val res = planDetailRepository.updateBPlanDescription(token, hashMap)

                if (res.isSuccessful && res.body() != null) {
                    if(res.body() == 0) {
                        Log.d("planViewModel", "updateBPlanDescription 성공 ${res.body()}")
                        _bPlan1.value!!.grandplanDesc = des
                    }
                } else {
                    Log.d("planViewModel", "updateBPlanDescription 실패 또는 없음")
                    _bPlan1.value!!.grandplanDesc = "설명을 입력하세요."
                }
            }
        }
    }

    // 대플랜 시작일을 업데이트 한다.
    fun updateBPlanStartday(startDay: String) {
        CoroutineScope(Dispatchers.Main).launch {
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = userPrefs.getString("jwt", "")!!
//            val userId = userPrefs.getString("uid","")!!

            val hashMap = HashMap<String, Any>()
            hashMap["planSeq"] = _bPlan1.value!!.grandplanSeq
            hashMap["updateContent"] = startDay
            hashMap["updateType"] = "startdate"


            withContext(Dispatchers.IO) {
                val res = planDetailRepository.updateBPlanStartday(token, hashMap)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("planViewModel", "updateBPlanStartday 성공 ${res.body()!!}")
                    _bPlan1.value!!.grandplanStart = res.body()!!.toString()
//                    _startDay.value = res.body()!!["updateContent"].toString()
                } else {
                    Log.d("planViewModel", "updateBPlanStartday 실패 또는 없음")
                    _bPlan1.value!!.grandplanStart = "2022-00-00"
//                    _startDay.value = "2020-02-02"
                }
            }
        }
    }

    // 대플랜 종료일을 업데이트 한다.
    fun updateBPlanEndday(endDay: String) {
        CoroutineScope(Dispatchers.Main).launch {
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = userPrefs.getString("jwt", "")!!
//            val userId = userPrefs.getString("uid","")!!

            val hashMap = HashMap<String, Any>()
            hashMap["planSeq"] = _bPlan1.value!!.grandplanSeq
            hashMap["updateContent"] = endDay
            hashMap["updateType"] = "enddate"


            withContext(Dispatchers.IO) {
                val res = planDetailRepository.updateBPlanEndday(token, hashMap)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("planViewModel", "updateBPlanEndday 성공 ${res.body()!!}")
                    _bPlan1.value!!.grandplanEnd = res.body()!!.toString()
//                    _endDay.value = res.body()!!["updateContent"].toString()
                } else {
                    Log.d("planViewModel", "updateBPlanEndday 실패 또는 없음")
                    _bPlan1.value!!.grandplanEnd = "2022-00-00"
//                    _endDay.value = "2020-02-02"
                }
            }
        }
    }

    // 대플랜 색을 업데이트 한다.
    fun updateBPlanColor(bPlanID: Int, color: String) {
        CoroutineScope(Dispatchers.Main).launch {
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = userPrefs.getString("jwt", "")!!
//            val userId = userPrefs.getString("uid","")!!

            val hashMap = HashMap<String, Any>()
            hashMap["planSeq"] = _bPlan1.value!!.grandplanSeq
            hashMap["updateContent"] = color
            hashMap["updateType"] = "color"


            withContext(Dispatchers.IO) {
                val res = planDetailRepository.updateBPlanColor(token, hashMap)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("planViewModel", "updateBPlanColor 성공 ${res.body()!!}")
                    _bPlan1.value!!.grandplanColor = res.body()!!.toString()
                    _bColor.value = res.body()!!.toString()
                } else {
                    Log.d("planViewModel", "updateBPlanColor 실패 또는 없음")
                    _bPlan1.value!!.grandplanColor = "R.color.primary"
                    _bColor.value = "R.color.primary"
                }
            }
        }
    }

    //  중플랜을 수정한다.
    fun updateMPlan(mplanSqe:String, content: String, type: String) {
        CoroutineScope(Dispatchers.Main).launch {
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = userPrefs.getString("jwt", "")!!
//            val userId = userPrefs.getString("uid","")!!

            val hashMap = HashMap<String, String>()
            hashMap["planSeq"] = mplanSqe
            hashMap["updateContent"] = content
            hashMap["updateType"] = type


            withContext(Dispatchers.IO) {
                Log.d("mplandetail", "updateMPlan seq $mplanSqe content $content type $type")
                val res = planDetailRepository.updateMPlan(token, hashMap)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("mplandetail", "updateMPlan success ${res.body()}")
                        if(res.body() == 0 && type == "title") {
                            _mPlanName.postValue(content)
                        }

                        if(res.body() == 0 && type == "desc") {
                            _mPlanDes.postValue(content)
                        }
//                    Log.d("planViewModel", "updateMPlan 성공 ${res.body()!!["updateContent"]}") // todo 숫자 반환
//                    _bPlan1.value!!.grandplanColor = res.body()!!["updateContent"].toString()
//                    _bColor.value = res.body()!!["updateContent"].toString()
                } else {
                    Log.d("mplandetail", "updateMPlan updateMPlan 실패 또는 없음")
//                    _bPlan1.value!!.grandplanColor = "R.color.primary"
//                    _bColor.value = "R.color.primary"
                }
            }
        }
    }

    //  중플랜을 수정한다.
    fun updateTask(updateTask: Task) {
        CoroutineScope(Dispatchers.Main).launch {
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = userPrefs.getString("jwt", "")!!
//            val userId = userPrefs.getString("uid","")!!

            withContext(Dispatchers.IO) {
                Log.d("taskdetailupdateTask", "updateTask task $updateTask")
                val res = planDetailRepository.updateTask(token, updateTask)
                Log.d("taskdetailupdateTask", "updateTask 반환값 ${res.body()}")
                if (res.isSuccessful && res.body() != null) {
                    Log.d("taskdetailupdateTask", "updateTask success ${res.body()}")
                    if(res.body() == 0 ) {
                        _taskResponse.postValue(2)
                    }

                } else {
                    Log.d("taskdetailupdateTask", "updateTask 실패 또는 없음 ${res.body()}")

                }
            }
        }
    }


    //대플랜을 추가한다.
    fun addBPlan(newBPlan: HashMap<String, String>) {
            // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
            val token = userPrefs.getString("jwt","")!!
            val userId = userPrefs.getString("uid","")!!


        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                Log.d("planDetailViewModel", "addBPlan 통신 시작")
                val res = planDetailRepository.addBPlan(token, newBPlan)
                Log.d("planDetailViewModel", "addBPlan 통신 끝 ")

                if (res.isSuccessful && res.body() != null) {
                    Log.d("planDetailViewModel", "addBPlan ${res.body()}")
                    _planSeq.postValue(res.body()!!.grandplanSeq)

                } else {
                    Log.d("planDetailViewModel", "addBPlan 실패")
                }
            }

            Log.d("planDetailViewModel", "addBPlan CoroutineScope 종료")

        }
    }

    //중플랜을 추가한다.
    fun addMPlan(newMPlan: HashMap<String, String>) {
        // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
        val token = userPrefs.getString("jwt","")!!
        val userId = userPrefs.getString("uid","")!!

//            val hashMap = HashMap<String, Any>()
//            hashMap["userId"] = userId
//            hashMap["BPlan"] = newBPlan

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                Log.d("planDetailAddM", "addMPlan start ${newMPlan.get("midplanStartdate")} " +
                        "end ${newMPlan.get("midplanEnddate")}")
                val res = planDetailRepository.addMPlan(token, newMPlan)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("planDetailAddM", "addMPlan ${res.body()}")
//                    val temp = _bPlan1.value
//                    _bPlan1.postValue(temp)
                    getBplanInfo(newMPlan.get("grandplanSeq")!!.toInt())

                } else {
                    Log.d("planDetailAddM", "addMPlan 실패 또는 없음")

                }
            }
        }
    }

    //태스크를 추가한다.
    fun addTask(newTask: Task) {
        // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
        val token = userPrefs.getString("jwt","")!!
        val userId = userPrefs.getString("uid","")!!

//            val hashMap = HashMap<String, Any>()
//            hashMap["userId"] = userId
//            hashMap["BPlan"] = newBPlan

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                Log.d("taskdetail", "addTask 시작 ${newTask}")
                val res = planDetailRepository.addTask(token, newTask)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("taskdetail", "addTask ${newTask} response${res.body()}")
                    if(res.body() == 0) {
                        _taskResponse.postValue(0)
                    }

                } else {
                    Log.d("taskdetail", "addTask 실패 또는 없음 ${res.body()}")
                }
            }
        }
    }

    fun deleteBPlan(planSeq: Int) {
        // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
        val token = userPrefs.getString("jwt","")!!
        val userId = userPrefs.getString("uid","")!!

//            val hashMap = HashMap<String, Any>()
//            hashMap["userId"] = userId
//            hashMap["BPlan"] = newBPlan

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {

                val res = planDetailRepository.deleteBPlan(token, planSeq)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("planDetailDelete", "deleteBPlan ${res.body()}")
                    setSpinnerList()
                } else {
                    Log.d("planDetailDelete", "deleteBPlan 실패 또는 없음")

                }
            }

        }

    }

    fun deleteMPlan(planSeq: Int) {
        // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
        val token = userPrefs.getString("jwt","")!!
        val userId = userPrefs.getString("uid","")!!

//            val hashMap = HashMap<String, Any>()
//            hashMap["userId"] = userId
//            hashMap["BPlan"] = newBPlan

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                Log.d("mplanDelete", "mplan seq ${planSeq}")

                val res = planDetailRepository.deleteMPlan(token, planSeq)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("mplanDelete", "mplanDelete ${res.body()}")
                    if(res.body() == 0) { // 달력 갱신 해주기
                        getBplanInfo(_bPlan1.value!!.grandplanSeq)
                        _deleteMplan.postValue(true)
                    }
                } else {
                    Log.d("mplanDelete", "mplanDelete 실패 또는 없음")

                }
            }

        }

    }

    fun deleteTask(planSeq: Int) {
        // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
        val token = userPrefs.getString("jwt","")!!
        val userId = userPrefs.getString("uid","")!!

//            val hashMap = HashMap<String, Any>()
//            hashMap["userId"] = userId
//            hashMap["BPlan"] = newBPlan

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                Log.d("taskDetail", "deleteTask seq ${planSeq}")

                val res = planDetailRepository.deleteTask(token, planSeq)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("taskDetail", "deleteTask 성공 ${res.body()}")
                    if(res.body() == 0) { // 달력 갱신 해주기
                        _taskResponse.postValue(1)
                    } else {
                        _taskResponse.postValue(4)
                    }
                } else {
                    Log.d("taskDetail", "mplanDelete 실패 또는 없음")

                }
            }

        }

    }

    fun checkTask(taskSeq: Int, position: Int) {
        // 토큰은 프리퍼런스에서 가져와서 넘겨 준다.
        val token = userPrefs.getString("jwt","")!!
        val userId = userPrefs.getString("uid","")!!

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {

                val res = planDetailRepository.checkTask(token, taskSeq)

                if (res.isSuccessful && res.body() != null) {
                    Log.d("planDetailViewModel1", "checkTask ${res.body()}")
                    _taskDone.postValue(position)
                } else {
                    Log.d("planDetailViewModel1", "checkTask 실패 또는 없음")
                    _taskDone.postValue(-1)
                }
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

    fun updateFireBaseStorage(imageUrl : Uri) {
        Log.d("taskdetail", "updateFireBaseStorage $imageUrl ")
        val storageRef = imageUrl.let { uri ->
            FirebaseStorage.getInstance().reference.child("TaskImage")
                .child(uri.toString())
        }

        storageRef.putFile(imageUrl)
            .continueWithTask {
                return@continueWithTask storageRef.downloadUrl
            }.addOnSuccessListener { u ->
                updateImage(u.toString())
                Log.d("taskdetail", "updateFireBaseStorage success : $u ")
            }
    }

    fun getFireBaseImage(imageUrl: Uri) {
        var storage = Firebase.storage
        val httpsReference = storage.getReferenceFromUrl(
            "imageUrl")
    }



}


data class BPlanSpinner(
    val planSeq: Int,
    val planTitle: String
)


data class BPlan(
    var uid: String,
    var grandplanSeq: Int = 0,
    var grandplanTitle: String,
    var grandplanColor: String,
    var grandplanStart: String,
    var grandplanEnd: String,
    var grandplanDesc: String = "",
    var subDto: MutableList<LowPlanInfo> = mutableListOf()
)

data class MPlan(
    var grandplanSeq: Int = 0,
    var midplanSeq: Int,
    var mPlanName: String,
    var midplanColor: String,
    var midplanStartdate: String,
    var midplanEnddate: String,
    var midplanDesc: String,
    var midplanTitle: String,
)

data class LowPlanInfo(
    // 플랜 상세 조회 위한 정보
    var midplanSeq: Int = 0,
    var smallplanSeq: Int,
    var smallplanDate: String,
    var midplanTitle: String,
    var midplanColor: String,
    var midplanStart: String,
    var hasTask: Boolean,
): Serializable

data class LowPlanInfoUse( // 플랜 상세 조회 위한 정보 저장용
    var midplanSeq: Int = 0,
    var smallplanSeq: Int,
    var smallplanDate: LocalDate,
    var midplanTitle: String,
    var midplanColor: String,
    var midplanStart: LocalDate,
    var hasTask: Boolean
)

data class LittlePlanInfo( // 일자별 소플랜 조회에서 사용될 소플랜 정보
    var smallplanSeq: Int,
    var midplanSeq: Int,
    var midplanTitle: String,
    var midplanDesc: String,
    var midplanStart: String,
    var midplanEnd: String,
    var midplanColor: String,
    var grandplanSeq : Int,
    var plantaskList: MutableList<Task>
)


data class Task(
    var plansmallSeq: Int,
    var taskSeq: Int,
    var title: String,
    var isDone: Boolean,
    var memo: String,
    var img: String,
    var alramtime: String // hh:mm

)