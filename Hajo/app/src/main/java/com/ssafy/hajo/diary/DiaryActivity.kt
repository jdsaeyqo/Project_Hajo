package com.ssafy.hajo.diary

import Diary
import DiaryObject
import DiaryViewModel
import LocatedObj
import Page
import TextObjectInfo
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.internal.ViewUtils
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityDiaryBinding
import com.ssafy.hajo.plan.BPlanDetailFragment
import com.ssafy.hajo.plan.PlanAddFragment
import com.ssafy.hajo.plan.editTextFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.HashMap

class DiaryActivity : AppCompatActivity() {

    private lateinit var screenSizes : Point
    private lateinit var binding : ActivityDiaryBinding
    private var mode = 0 // 0 조회, 1 배치
    private var WRITE_EXTERNAL_STORAGE = 0
    private lateinit var newImageUri: Uri
    private var newStringUri: String = ""

    // 임시
    private var editImageId = 0
    private var viewList: MutableList<View> = mutableListOf()

    private lateinit var viewModel: DiaryViewModel
    private var page = 0


    private var diarySeq: Int = 0
    private var diaryType: String = "0"
    private var thisDiary: Diary = Diary(0,"","","", mutableListOf())
    private var diaryPages: MutableList<Page> = mutableListOf()
    private var diaryObjects: MutableList<DiaryObject> = mutableListOf()
    private var selectedObject: DiaryObject = DiaryObject(0,-1,"", 0L, 0L,
    "", TextObjectInfo())

    private var newObjLocation = mutableListOf<DiaryObject>()
    private var addedViews = mutableListOf<View>()

    private var added = false // 컴포넌트 추가인가?

    override fun onBackPressed() {
        super.onBackPressed()
        if (isFinishing) {
            // back 버튼으로 화면 종료가 야기되면 동작한다.
            overridePendingTransition(R.anim.none, R.anim.diary_top_to_bottom_anime)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.diary_slowly_fadein, R.anim.none)

        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DiaryViewModel::class.java)

        screenSizes = getScreenSize(this)!!

        //어떤 다이어리 인지 확인
        Log.d("diaryActivity", "diarySeq 조회 전 ${intent.getIntExtra("diarySeq",0)}")
        diarySeq = intent.getIntExtra("diarySeq",0)
        if( intent.getStringExtra("diaryType") != null) {
            diaryType = intent.getStringExtra("diaryType")!!
        }
        Log.d("diaryActivity", "diarySeq 조회 후 $diarySeq diaryType $diaryType")

        initObserve()
        initUI()
        if(!added) { // 컴포너트 추가 아닐 경우
            initBtn()
        } else {
            binding.btnComponentEdit.callOnClick()
            added = false
        }


    }
    private fun initUI() {
        if(diarySeq != 0) {
            when (diaryType) {
                "0" -> binding.cDiary.setBackgroundResource(R.drawable.skinoldpage)
                "1" -> {
                    binding.cDiary.setBackgroundResource(R.drawable.skinblack)
                    binding.btnSave.setTextColor(Color.parseColor("#ffffff"))
                    binding.btnPageNext.setTextColor(Color.parseColor("#ffffff"))
                    binding.btnPagePre.setTextColor(Color.parseColor("#ffffff"))
                    binding.btnComponentEdit.setTextColor(Color.parseColor("#ffffff"))
                    binding.btnComponentAdd.setTextColor(Color.parseColor("#ffffff"))

                }
                "2" -> {
                    binding.cDiary.setBackgroundResource(R.drawable.skinyellow)
                    binding.btnSave.setTextColor(Color.parseColor("#FF0B0B0B"))
                    binding.btnPageNext.setTextColor(Color.parseColor("#FF0B0B0B"))
                    binding.btnPagePre.setTextColor(Color.parseColor("#FF0B0B0B"))
                    binding.btnComponentEdit.setTextColor(Color.parseColor("#FF0B0B0B"))
                    binding.btnComponentAdd.setTextColor(Color.parseColor("#FF0B0B0B"))
                }
                "3" -> {
                    binding.cDiary.setBackgroundResource(R.drawable.skilgray)
                    binding.btnSave.setTextColor(Color.parseColor("#FF0B0B0B"))
                    binding.btnPageNext.setTextColor(Color.parseColor("#FF0B0B0B"))
                    binding.btnPagePre.setTextColor(Color.parseColor("#FF0B0B0B"))
                    binding.btnComponentEdit.setTextColor(Color.parseColor("#FF0B0B0B"))
                    binding.btnComponentAdd.setTextColor(Color.parseColor("#FF0B0B0B"))
                }
                "4" -> binding.cDiary.setBackgroundResource(R.drawable.skilgray)
            }
            Log.d("diaryActivity", "initUI getDiaryInfo $diarySeq")
            viewModel.getDiaryInfo(diarySeq)
        }



    }

    private fun initObserve() {
        viewModel.diaryInfo.observe(this) {
            Log.d("diaryActivity", "diaryInfo.observe ${viewModel.diaryInfo.value}")
            if(viewModel.diaryInfo.value == null) {
                Toast.makeText(this,"다이어리 조회 실패", Toast.LENGTH_SHORT).show()
            } else {
                this.thisDiary = viewModel.diaryInfo.value!!
                Log.d("diaryActivity", "This Diary 세팅 ${this.thisDiary}")
                binding.cDiary.removeAllViews()
//                when (thisDiary.diaryType) {
//                    "0" -> binding.cDiary.setBackgroundResource(R.drawable.skinoldpage)
//                    "1" -> binding.cDiary.setBackgroundResource(R.drawable.skinblack)
//                    "2" -> binding.cDiary.setBackgroundResource(R.drawable.skinyellow)
//                    "3" -> binding.cDiary.setBackgroundResource(R.drawable.skilgray)
//                    "4" -> binding.cDiary.setBackgroundResource(R.drawable.skilgray)
//                }
                setDiaryData()
            }
        }

        viewModel.location.observe(this) {
            Log.d("diaryActivity", "location.observe")
            if(viewModel.location.value == 200) {
                Log.d("diaryActivity", "save locate 성공")
                diaryObjects = newObjLocation
            } else {
                Log.d("diaryActivity", "save locate 실패")
                if(addedViews != null) {
                    addedViews.forEachIndexed{ index, value ->
                        value.x = diaryObjects.get(index).diaryobjXloc.toFloat()
                        value.y = diaryObjects.get(index).diaryobjYloc.toFloat()
                    }
                }
            }
        }

        viewModel.addObject.observe(this) {
            if(viewModel.addObject.value == 200) {
                viewModel.getDiaryInfo(diarySeq)


            } else {
                binding.cDiary.removeView(addedViews.get(addedViews.size-1))
                addedViews.removeAt(addedViews.size-1)
                Toast.makeText(this,"추가 실패", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.deleteObject.observe(this) {
            if(viewModel.deleteObject.value == 200) {
                viewModel.getDiaryInfo(diarySeq)

            } else {
                Toast.makeText(this,"삭제 실패", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.imageUri.observe(this) {
            if(viewModel.imageUri.value != "" && viewModel.imageUri.value != null) {
                newStringUri = viewModel.imageUri.value!!

                Log.d("diaryActivity","imageUri.observe objImgUpdate objid $editImageId uri $newStringUri")

                viewModel.objImgUpdate(editImageId, newStringUri)
            }
        }

        viewModel.updateObjImg.observe(this) {
            if(viewModel.updateObjImg != null && viewModel.updateObjImg.value == 200) {
                Log.d("diaryActivity",".updateObjImg.observe 성공")
                viewModel.getDiaryInfo(diarySeq)
            } else {
                Toast.makeText(this,"이미지 변경 실패", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.EditText.observe(this) {
            if(viewModel.EditTextResponse.value == 200) {
                viewModel.getDiaryInfo(diarySeq)
            } else {
            }
        }

        viewModel.EditTextResponse.observe(this) {
            if(viewModel.EditTextResponse.value == 200) {
                initUI()
            }
        }

    }

    private fun initBtn() {

        binding.btnComponentAdd.setOnClickListener { // 컴포넌트 추가 창 띄우기
            val bundle = Bundle()
            val diaryComponentAddFragment = DiaryComponentAddFragment(applicationContext).apply {
                arguments = bundle
            }
            Log.d("diaryActivity","diaryAdd 프래그먼트 호출")
            diaryComponentAddFragment.show(supportFragmentManager, diaryComponentAddFragment.tag)

        }

        binding.btnComponentEdit.setOnClickListener {
            mode = 1
            binding.btnComponentAdd.visibility = View.GONE
            binding.btnComponentEdit.visibility = View.GONE
            binding.btnPageNext.visibility = View.GONE
            binding.btnPagePre.visibility = View.GONE
            binding.btnSave.visibility = View.VISIBLE

            newObjLocation = diaryObjects

        }

        binding.btnSave.setOnClickListener { //
            mode = 0
            binding.btnComponentAdd.visibility = View.VISIBLE
            binding.btnComponentEdit.visibility = View.VISIBLE
            binding.btnPageNext.visibility = View.VISIBLE
            binding.btnPagePre.visibility = View.VISIBLE
            binding.btnSave.visibility = View.GONE

            var locateObjs: MutableList<LocatedObj> = mutableListOf()
            for(i in newObjLocation) {
                locateObjs.add(LocatedObj(i.diaryobjSeq,i.diaryobjXloc,i.diaryobjYloc))
            }
            if(locateObjs.size > 0) {
                viewModel.saveLocation(locateObjs)
            }

        }

        binding.btnPageNext.setOnClickListener {
            Log.d("diaryActivity","btnPageNext 프래그먼트 호출")
            if(page <= 9) {
                page++
                binding.cDiary.removeAllViews()
                setDiaryData()
            }
        }

        binding.btnPagePre.setOnClickListener {
            Log.d("diaryActivity","btnPagePre 프래그먼트 호출")
            if(page > 0) {
                page--
                binding.cDiary.removeAllViews()
                setDiaryData()
            }

        }
    }

    fun setDiaryData() {
        Log.d("diaryActivity", "setDiaryData")
        if(thisDiary.diarySeq != 0) {
            Log.d("diaryActivity", "diarySeq 0 아님 ${thisDiary.diarySeq}")
            diarySeq = thisDiary.diarySeq // todo ???
            if(thisDiary.diaryPages != null) {
                Log.d("diaryActivity", "diary diaryPages null 아님 ${thisDiary.diaryPages}")
                diaryPages = thisDiary.diaryPages
                if(thisDiary.diaryPages[page].dirayObjs != null) {
                    diaryObjects = thisDiary.diaryPages[page].dirayObjs
                    Log.d("diaryActivity", "diary diaryObjects $diaryObjects")
                    setObjects()
                }
            }
        }

    }

    fun setObjects() {
        for(i in diaryObjects) {
            if(i.diaryobjType == "pic") {
                componentLocate(i.diaryobjSeq, "img", i.diaryobjXloc, i.diaryobjYloc, i.objpicImg )
            } else if (i.diaryobjType == "text") {
                componentLocate(i.diaryobjSeq,"text", i.diaryobjXloc, i.diaryobjYloc, "",i.objtext)
            }
        }
    }




    fun getScreenSize(activity: Activity): Point? {
        val display: Display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }


    private fun checkGalleryPermission() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = TedPermission.create()

                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDeniedMessage("권한을 허용해주세요.")
                .check()

            if (result.isGranted) {
                WRITE_EXTERNAL_STORAGE = 1
            }
        }
    }

    private fun openGallery() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    @SuppressLint("ResourceAsColor")
    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            newImageUri = it.data?.data!!
            Log.d("imanewImageUrigeUri", "$newImageUri")


            if(this::newImageUri.isInitialized) { //newImageUri는 사진을 새로 입력했을 경우에만 사용됨
                Log.d("taskdetail", "firebase에 이미지 저장 $newImageUri")
                viewModel.updateFireBaseStorage(newImageUri)
            }


        }
    }

    @SuppressLint("ResourceAsColor")
    fun componentAdd(componentId: Int) { // 컴포넌트 생성
        added = true
        val scrapMainLayout: ConstraintLayout = findViewById(R.id.c_diary) // 다이어리 생성
        val newImage = ImageView(this@DiaryActivity)
        val newText = TextView(this@DiaryActivity)

        when(componentId) {
            1 -> { // 이미지뷰 생성
                val imageLayoutParams = LinearLayout.LayoutParams( // 이미지 뷰 컨테이너 설정
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT)
                    0,0
                    ).apply {
                    gravity = Gravity.CENTER
                }

                // 이미지 뷰 설정
                newImage.layoutParams = imageLayoutParams
                newImage.adjustViewBounds = true
                newImage.scaleType = ImageView.ScaleType.CENTER
//                newImage.minimumHeight = 500
//                newImage.minimumWidth = 500
                newImage.maxHeight = 100
                newImage.maxHeight = 100

                newImage.x = 333f
                newImage.y = 357f
                newImage.setPadding(10,5,10,30)
                newImage.elevation = 5.0f

//                Glide.with(this@DiaryActivity).load(foodImages[i]).into(scrapImage) todo 이미지는 다른데서
//                newImage.setBackgroundColor(Color.parseColor("#ffffff"))
                newImage.setOnClickListener {
                    Log.d(
                        "diaryImage click",
                        "mode ${mode}"
                    )
                    if(mode == 0) {
                        Log.d(
                            "diaryImage",
                            "newImage.setOnClickListener WRITE_EXTERNAL_STORAGE ${WRITE_EXTERNAL_STORAGE}"
                        )
                        if (WRITE_EXTERNAL_STORAGE != 1) {
                            checkGalleryPermission()
                            if(WRITE_EXTERNAL_STORAGE == 1) {
                                openGallery()
                            }
                        } else {
                            openGallery()
                        }
                    }

                }
                newImage.setOnTouchListener { v, event ->
                    if(mode == 1) {
                        var moveX = 0f
                        var moveY = 0f
                        when(event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                moveX = v.x - event.rawX
                                moveY = v.y - event.rawY
                            }

                            MotionEvent.ACTION_MOVE -> {
                                if(event.rawY + moveY < 0) {
                                    v.animate()
                                        .x(event.rawX + moveX)
                                        .y(100f)
                                        .setDuration(0)
                                        .start()
                                } else if(event.rawX + moveX < 0) {
                                    v.animate()
                                        .x(100f)
                                        .y(event.rawY + moveY)
                                        .setDuration(0)
                                        .start()
                                } else if(event.rawY + moveY > screenSizes.y - 200f) {
                                    v.animate()
                                        .x(event.rawX + moveX)
                                        .y(screenSizes.y - 220f)
                                        .setDuration(0)
                                        .start()
                                } else if(event.rawX + moveX > screenSizes.x - 300f) {
                                    v.animate()
                                        .x(screenSizes.x - 300f)
                                        .y(event.rawY + moveY)
                                        .setDuration(0)
                                        .start()
                                } else {
                                    v.animate()
                                        .x(event.rawX + moveX - 100)
                                        .y(event.rawY + moveY - 170)
                                        .setDuration(0)
                                        .start()
                                }

                            }

                        }

                        var x = v.x
                        var y = v.y

                    }


                    false
                }

                scrapMainLayout.addView(newImage)
                addedViews.add(newImage)
                var newObj1 = DiaryObject(diaryPages[page].diarypageSeq,0,"pic",newImage.x.toLong(), newImage.y.toLong(),
                    "https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b\n",
                TextObjectInfo())
//                var newObj = HashMap<String, Any>()
//                newObj.set("diarypageSeq", diaryPages[page].diarypageSeq.toString())
//                newObj.set("diaryobjXloc", "10")
//                newObj.set("diaryobjYloc", "10")
//                newObj.set("objpicImg", "https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b\n")

                viewModel.addObj(newObj1)
            }
            2 -> { // 텍스트뷰 생성
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ).apply {
                    setMargins(80,5,0,0)
                }


                newText.layoutParams = layoutParams
                newText.setText("")
                newText.x = 133f
                newText.y = 1031f
                newText.minimumHeight = 300
                newText.minimumWidth = 300
                newText.maxWidth = 800
                newText.setTextSize(22.0f)
                newText.setTypeface(newText.typeface, Typeface.BOLD) // 두께
                newText.setTextColor(Color.LTGRAY) // 텍스트 컬러
                newText.setOnTouchListener { v, event ->
                    if(mode == 1) {
                        var moveX = 0f
                        var moveY = 0f
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                moveX = v.x - event.rawX
                                moveY = v.y - event.rawY
                            }

                            MotionEvent.ACTION_MOVE -> {
                                if (event.rawY + moveY < 0) {
                                    v.animate()
                                        .x(event.rawX + moveX)
                                        .y(100f)
                                        .setDuration(0)
                                        .start()
                                } else if (event.rawX + moveX < 0) {
                                    v.animate()
                                        .x(100f)
                                        .y(event.rawY + moveY)
                                        .setDuration(0)
                                        .start()
                                } else if (event.rawY + moveY > screenSizes.y - 200f) {
                                    v.animate()
                                        .x(event.rawX + moveX)
                                        .y(screenSizes.y - 220f)
                                        .setDuration(0)
                                        .start()
                                } else if (event.rawX + moveX > screenSizes.x - 300f) {
                                    v.animate()
                                        .x(screenSizes.x - 300f)
                                        .y(event.rawY + moveY)
                                        .setDuration(0)
                                        .start()
                                } else {
                                    v.animate()
                                        .x(event.rawX + moveX - 100)
                                        .y(event.rawY + moveY - 170)
                                        .setDuration(0)
                                        .start()
                                }

                            }

                        }

                        var x = v.x
                        var y = v.y
                    }

                    true
                }




                scrapMainLayout.addView(newText)
                addedViews.add(newText)
                var newObj1 = DiaryObject(diaryPages[page].diarypageSeq,0,"text",newText.x.toLong(), newText.y.toLong(),
                    "https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b\n",
                    TextObjectInfo())
//                var newObj = HashMap<String, Any>()
//                newObj.set("diarypageSeq", diaryPages[page].diarypageSeq.toString())
//                newObj.set("diaryobjXloc", "10")
//                newObj.set("diaryobjYloc", "10")
//                newObj.set("objtext", TextObjectInfo())


                viewModel.addObj(newObj1)
            }
            else -> {

            }
        }
    }
    // componentLocate("img", i.diaryobjXloc, i.diaryobjYloc, i.objpicImg, i.diaryobjSeq)

    @SuppressLint("ResourceAsColor")
    fun componentLocate(objSeq: Int, type: String, xL: Long, yL: Long, imgUri: String = "", textInfo: TextObjectInfo = TextObjectInfo()) { // 컴포넌트 생성
        val scrapMainLayout: ConstraintLayout = findViewById(R.id.c_diary) // 다이어리 생성
        val newImage = ImageView(this@DiaryActivity)
        val newText = TextView(this@DiaryActivity)

        when(type) {
            "img" -> { // 이미지뷰 생성
                val imageLayoutParams = LinearLayout.LayoutParams( // 이미지 뷰 컨테이너 설정
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER

                }



                newImage.adjustViewBounds = true
                newImage.scaleType = ImageView.ScaleType.FIT_XY
                newImage.minimumHeight = 500
                newImage.minimumWidth = 500
//                newImage.maxHeight = 700
//                newImage.maxWidth = 500
                newImage.maxHeight = 500
                newImage.maxWidth = 500


                // 이미지 뷰 설정
                newImage.layoutParams = imageLayoutParams
                newImage.id = objSeq
                newImage.x = xL.toFloat()
                newImage.y = yL.toFloat()
                newImage.setPadding(20,20,20,50)
                if(imgUri != "" && imgUri.toCharArray().get(0) == 'h') {
                    Glide.with(this@DiaryActivity).load(imgUri).into(newImage)
                }
                newImage.setBackgroundColor(Color.parseColor("#ffffff"))
                newImage.setOnClickListener {
                    Log.d(
                        "diaryImage click",
                        "mode ${mode}"
                    )
                    if(mode == 0) {
                        Log.d(
                            "diaryImage",
                            "newImage.setOnClickListener WRITE_EXTERNAL_STORAGE ${WRITE_EXTERNAL_STORAGE}"
                        )
                        editImageId = newImage.id
                        if (WRITE_EXTERNAL_STORAGE != 1) {
                            checkGalleryPermission()
                            if(WRITE_EXTERNAL_STORAGE == 1) {
                                openGallery()
                            }
                        } else {
                            openGallery()
                        }
                    }

                }


                newImage.setOnLongClickListener {
                    if(mode == 0) {
                        Log.d(
                            "diaryActivity", "diaryImage Longclick ${newImage.id} mode ${mode}",
                        )
                        viewModel.deleteObj(it.id)
                    }
                    return@setOnLongClickListener true
                }

                newImage.setOnTouchListener { v, event ->
                    if(mode == 1) {
                        var moveX = 0f - 50
                        var moveY = 0f - 50
                        when(event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                moveX = v.x - event.rawX
                                moveY = v.y - event.rawY
                            }

                            MotionEvent.ACTION_MOVE -> {
                                if(event.rawY + moveY < 0) {
                                    v.animate()
                                        .x(event.rawX + moveX)
                                        .y(100f)
                                        .setDuration(0)
                                        .start()
                                } else if(event.rawX + moveX < 0) {
                                    v.animate()
                                        .x(100f)
                                        .y(event.rawY + moveY)
                                        .setDuration(0)
                                        .start()
                                } else if(event.rawY + moveY > screenSizes.y - 200f) {
                                    v.animate()
                                        .x(event.rawX + moveX)
                                        .y(screenSizes.y - 220f)
                                        .setDuration(0)
                                        .start()
                                } else if(event.rawX + moveX > screenSizes.x - 300f) {
                                    v.animate()
                                        .x(screenSizes.x - 300f)
                                        .y(event.rawY + moveY)
                                        .setDuration(0)
                                        .start()
                                } else {
                                    v.animate()
                                        .x(event.rawX + moveX)
                                        .y(event.rawY + moveY)
                                        .setDuration(0)
                                        .start()
                                }

                            }

                        }

                        var x = v.x
                        var y = v.y

                        for(i in newObjLocation){
                            if(i.diaryobjSeq == v.id) {
                                i.diaryobjXloc = x.toLong()
                                i.diaryobjYloc = y.toLong()
                            }
                        }

                    }


                    false
                }

                viewList.add(newImage)
                scrapMainLayout.addView(newImage)
            }
            "text" -> { // 텍스트뷰 생성
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ).apply {
                    setMargins(80,5,0,0)
                }

                newText.layoutParams = layoutParams
                newText.setText(textInfo.objtextContent)
                newText.x = xL.toFloat()
                newText.y = yL.toFloat()
                newText.id = objSeq
                newText.minimumHeight = 300
                newText.minimumWidth = 300
                newText.maxWidth = 800

                when(textInfo.objtextFont) {
                    "0" -> {
                        Log.d("font", textInfo.objtextFont)

                        val typeface = resources.getFont(R.font.kotra_hope)
                        newText.typeface = typeface
                    }
                    "1" -> {
                        Log.d("font", textInfo.objtextFont)

                        val typeface = resources.getFont(R.font.cookie_run_regular)
                        newText.typeface = typeface
                    }
                    "2" -> {
                        Log.d("font", textInfo.objtextFont)
                        val typeface = resources.getFont(R.font.nexon_lv2_gothic_bold)
                        newText.typeface = typeface
                    }
                }

                newText.setTextSize(textInfo.objtextSize.toFloat())
                if(textInfo.objtextIsbold){
                    newText.setTypeface(newText.typeface, Typeface.BOLD) // 두께
                } else {
                    newText.setTypeface(newText.typeface, Typeface.NORMAL) // 두께
                }
                if(textInfo.objtextColor == "" || textInfo.objtextColor.toCharArray().get(0) != '#') {
                    newText.setTextColor(Color.BLACK) // 텍스트 컬러
                } else {
                    newText.setTextColor(Color.parseColor(textInfo.objtextColor)) // 텍스트 컬러
                }

                newText.setOnClickListener {
                    if(mode == 0) {
                        Log.d(
                            "editText", "newText.setOnClickListener ${newText.id} mode ${mode}",
                        )
                        for (i in diaryObjects) {
                            if (i.diaryobjSeq == newText.id) {
                                val bundle = Bundle()
                                val editTextFragment = editTextFragment(applicationContext).apply {
                                    arguments = bundle
                                }
                                viewModel.setEditText(i)
                                Log.d("editText", "text edit to  id ${newText.id} object ${i}")
                                editTextFragment.show(supportFragmentManager, editTextFragment.tag)
                            }
                        }
                    }

                }

                newText.setOnLongClickListener {
                    // 삭제  todo 삭제 수정 선택
                    if(mode == 0) {
                        Log.d(
                            "diaryActivity", "diaryText Longclick ${newText.id} mode ${mode}",
                        )
                        viewModel.deleteObj(it.id)
                    }

                    return@setOnLongClickListener true
                }

                newText.setOnTouchListener { v, event ->
                    if(mode == 1) {
                        var moveX = 0f
                        var moveY = 0f
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                moveX = v.x - event.rawX
                                moveY = v.y - event.rawY
                            }

                            MotionEvent.ACTION_MOVE -> {
//                                if (event.rawY + moveY < 0) {
//                                    v.animate()
//                                        .x(event.rawX + moveX)
//                                        .y(100f)
//                                        .setDuration(0)
//                                        .start()
//                                } else if (event.rawX + moveX < 0) {
//                                    v.animate()
//                                        .x(100f)
//                                        .y(event.rawY + moveY)
//                                        .setDuration(0)
//                                        .start()
//                                } else if (event.rawY + moveY > screenSizes.y - 200f) {
//                                    v.animate()
//                                        .x(event.rawX + moveX)
//                                        .y(screenSizes.y - 220f)
//                                        .setDuration(0)
//                                        .start()
//                                } else if (event.rawX + moveX > screenSizes.x - 300f) {
//                                    v.animate()
//                                        .x(screenSizes.x - 300f)
//                                        .y(event.rawY + moveY)
//                                        .setDuration(0)
//                                        .start()
//                                } else {
                                    v.animate()
                                        .x(event.rawX + moveX)
                                        .y(event.rawY + moveY)
                                        .setDuration(0)
                                        .start()
//                                }

                            }

                        }

                        var x = v.x
                        var y = v.y

                        for(i in newObjLocation){
                            if(i.diaryobjSeq == v.id) {
                                i.diaryobjXloc = x.toLong()
                                i.diaryobjYloc = y.toLong()
                            }
                        }
                    }

                    false
                }
                viewList.add(newText)
                scrapMainLayout.addView(newText)
            }
            else -> {

            }
        }
    }



}