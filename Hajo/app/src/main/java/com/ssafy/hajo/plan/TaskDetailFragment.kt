package com.ssafy.hajo.plan

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.test.core.app.ApplicationProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.FragmentTaskDetailBinding
import com.ssafy.hajo.repository.dto.request.TaskAddRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailFragment(context: Context) : BottomSheetDialogFragment() {
//    val gallery = 0

    var taskPositon: Int = -1
    //https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b
    lateinit var task: Task
    var imgUri: String = ""
    lateinit var newImageUri: Uri

    var WRITE_EXTERNAL_STORAGE = 0


    private lateinit var binding: FragmentTaskDetailBinding
    private val viewModel: PlanDetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        Log.d("taskdetail", "add task 화면 on create")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("taskdetail", "add task 화면 nViewCreated")
        Log.d("taskdetail", "add task nViewCreated taskResponse ${viewModel.taskResponse.value}")

        // -1이면 추가, 0~ 는 조회, 수정 및 삭제
        taskPositon = requireArguments().getInt("taskPositon",-1)
        Log.d("taskdetail", "taskPositon $taskPositon")

        if(taskPositon != -1 && viewModel.taskList.value != null && viewModel.taskList.value!!.size > taskPositon) {
            task = viewModel.taskList.value!!.get(taskPositon)
            imgUri = task.img
            if(task.isDone) {
                binding.tvSave.visibility = View.GONE
            }
        } else {
            task = Task(viewModel.littlePlanInfo.value!!.smallplanSeq,-1, "",
                false, "","https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b","알람 설정")
            imgUri = task.img
        }
        Log.d("taskdetail", "현재 태스크 task $task")

        initObserve()
        initUI()
        initButton()
    }

    private fun initObserve() {
        viewModel.taskResponse.observe(this) {
            if(viewModel.taskResponse.value == 3) {
                return@observe
            }
            if(viewModel.taskResponse.value == 0) { // 추가 성공하면 태스크 리스트 갱신한 후 종료
                viewModel.getDayPlanInfo(task.plansmallSeq)
                Log.d("taskdetail", "add task 추가 종료 ${viewModel.taskResponse.value}")
                viewModel.setTaskResponse(3)
                viewModel.updateImage("")
                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();
            }

            if(viewModel.taskResponse.value == 1) { // 삭제 성공하면 태스크 리스트 갱신한 후 종료
                viewModel.getDayPlanInfo(task.plansmallSeq)
                Log.d("taskdetail", "add task 삭제 종료")
                viewModel.setTaskResponse(3)
                viewModel.updateImage("")

                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();

            }

            if(viewModel.taskResponse.value == 2) { // 수정 성공하면 태스크 리스트 갱신한 후 종료
                viewModel.getDayPlanInfo(task.plansmallSeq)
                Log.d("taskdetail", "add task 수정 종료")
                viewModel.setTaskResponse(3)
                viewModel.updateImage("")

                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();
            }

            if(viewModel.taskResponse.value == 4) { // 에러
                Log.d("taskdetail", "add task 삭제 에러")
                viewModel.setTaskResponse(3)
                viewModel.updateImage("")

                Toast.makeText(activity, "삭제할 수 없습니다.", Toast.LENGTH_SHORT).show()
                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();
            }

            Log.d("taskdetail", "task save 종료")

        }

        viewModel.imageUri.observe(this){ // 새로운 이미지 업로드 완료 했을 시
            if(viewModel.imageUri.value != ""){
                task.img = viewModel.imageUri.value!!

                Log.d("taskdetail", "이미지 변경 ${task.img}")
                if(taskPositon == -1) {
                    Log.d("taskdetail", "viewModel.addTask ${task}")
                    viewModel.addTask(task)
                } else {
                    Log.d("taskdetail", "viewModel.updateTask ${task}")
                    viewModel.updateTask(task)
                }
            } else {
                Log.d("taskdetail", "imageUri.observe null ${viewModel.imageUri.value}")
            }
        }


    }

    private fun initUI() {
        if(taskPositon == -1) {
            Log.d("taskdetail", "initUI $taskPositon")
            binding.etContent.setText(task.title)
            binding.etMemo.setText(task.memo)
            binding.tvAlram.text = task.alramtime
            Glide.with(this@TaskDetailFragment).load(task.img).into(binding.imgPicture)
            binding.tvCancle.visibility = View.VISIBLE
            binding.tvDelete.visibility = View.GONE
        } else {
            Log.d("taskdetail", "taskposition $taskPositon 으로 세팅")

            binding.etContent.setText(task.title)
            binding.etMemo.setText(task.memo)
            binding.tvAlram.text = task.alramtime
            binding.tvCancle.visibility = View.GONE
            binding.tvDelete.visibility = View.VISIBLE
            if(task.img != "") {
                Log.d("taskdetail", "task.img 존재 ${task.img} 으로 사진 입력")
                Glide.with(this@TaskDetailFragment).load(task.img).into(binding.imgPicture)

            }
        }
        Log.d("taskdetail", "init UI 종료")

    }

    private fun initButton() {
        binding.tvSave.setOnClickListener {
                //옵저버 후 성공하면 끝내고 태스크 정보 갱신함

                if (binding.etContent.text.toString() == "") {
                    Toast.makeText(activity, "할 일 내용을 입력하세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                task.title = binding.etContent.text.toString()
                task.memo = binding.etMemo.text.toString()
                task.alramtime = binding.tvAlram.text.toString()
                task.img = imgUri // 기존에 들어 있던 사진 또는 " "
                Log.d("taskdetail", "add task tvSave $task")

                if(this::newImageUri.isInitialized) { //newImageUri는 사진을 새로 입력했을 경우에만 사용됨
                    Log.d("taskdetail", "firebase에 이미지 저장 ${task.img}")
                    viewModel.updateFireBaseStorage(newImageUri)
                    return@setOnClickListener
                } else {
                    Log.d("taskdetail", "이미지 변경 없음 ${task.img}")
                    if(taskPositon == -1) {
                        Log.d("taskdetail", "viewModel.addTask ${task}")
                        viewModel.addTask(task)
                    } else {
                        Log.d("taskdetailupdateTask", "viewModel.updateTask ${task}")
                        viewModel.updateTask(task)
                    }
                }

        }

        binding.tvCancle.setOnClickListener { // 취소
            viewModel.setTaskResponse(3)
            viewModel.updateImage("")
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        binding.tvDelete.setOnClickListener { // 삭제
            val builder = AlertDialog.Builder(context as Activity)
                .setTitle("할 일 삭제")
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener{ dialog, which ->
                        viewModel.deleteTask(task.taskSeq)
                        val fragmentManager = requireActivity().supportFragmentManager;
                        fragmentManager.beginTransaction().remove(this).commit();
                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(context as Activity, "취소", Toast.LENGTH_SHORT).show()
                    })

            builder.show()
        }

        binding.tvAlram.setOnClickListener { // 알람 설정

        }


        binding.imgPicture.setOnClickListener { // 사진 올리기기
            if (WRITE_EXTERNAL_STORAGE != 1) {
                checkGalleryPermission()
            } else {
                openGallery()
            }
        }

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
            imgUri = newImageUri.toString()
            Log.d("imageUri", "$newImageUri")
            binding.imgPicture.setImageURI(newImageUri)



        }
    }

}

