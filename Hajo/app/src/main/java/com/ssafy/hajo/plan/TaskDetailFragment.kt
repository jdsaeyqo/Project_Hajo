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
        Log.d("taskdetail", "add task ?????? on create")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("taskdetail", "add task ?????? nViewCreated")
        Log.d("taskdetail", "add task nViewCreated taskResponse ${viewModel.taskResponse.value}")

        // -1?????? ??????, 0~ ??? ??????, ?????? ??? ??????
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
                false, "","https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b","?????? ??????")
            imgUri = task.img
        }
        Log.d("taskdetail", "?????? ????????? task $task")

        initObserve()
        initUI()
        initButton()
    }

    private fun initObserve() {
        viewModel.taskResponse.observe(this) {
            if(viewModel.taskResponse.value == 3) {
                return@observe
            }
            if(viewModel.taskResponse.value == 0) { // ?????? ???????????? ????????? ????????? ????????? ??? ??????
                viewModel.getDayPlanInfo(task.plansmallSeq)
                Log.d("taskdetail", "add task ?????? ?????? ${viewModel.taskResponse.value}")
                viewModel.setTaskResponse(3)
                viewModel.updateImage("")
                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();
            }

            if(viewModel.taskResponse.value == 1) { // ?????? ???????????? ????????? ????????? ????????? ??? ??????
                viewModel.getDayPlanInfo(task.plansmallSeq)
                Log.d("taskdetail", "add task ?????? ??????")
                viewModel.setTaskResponse(3)
                viewModel.updateImage("")

                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();

            }

            if(viewModel.taskResponse.value == 2) { // ?????? ???????????? ????????? ????????? ????????? ??? ??????
                viewModel.getDayPlanInfo(task.plansmallSeq)
                Log.d("taskdetail", "add task ?????? ??????")
                viewModel.setTaskResponse(3)
                viewModel.updateImage("")

                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();
            }

            if(viewModel.taskResponse.value == 4) { // ??????
                Log.d("taskdetail", "add task ?????? ??????")
                viewModel.setTaskResponse(3)
                viewModel.updateImage("")

                Toast.makeText(activity, "????????? ??? ????????????.", Toast.LENGTH_SHORT).show()
                val fragmentManager = requireActivity().supportFragmentManager;
                fragmentManager.beginTransaction().remove(this).commit();
            }

            Log.d("taskdetail", "task save ??????")

        }

        viewModel.imageUri.observe(this){ // ????????? ????????? ????????? ?????? ?????? ???
            if(viewModel.imageUri.value != ""){
                task.img = viewModel.imageUri.value!!

                Log.d("taskdetail", "????????? ?????? ${task.img}")
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
            Log.d("taskdetail", "taskposition $taskPositon ?????? ??????")

            binding.etContent.setText(task.title)
            binding.etMemo.setText(task.memo)
            binding.tvAlram.text = task.alramtime
            binding.tvCancle.visibility = View.GONE
            binding.tvDelete.visibility = View.VISIBLE
            if(task.img != "") {
                Log.d("taskdetail", "task.img ?????? ${task.img} ?????? ?????? ??????")
                Glide.with(this@TaskDetailFragment).load(task.img).into(binding.imgPicture)

            }
        }
        Log.d("taskdetail", "init UI ??????")

    }

    private fun initButton() {
        binding.tvSave.setOnClickListener {
                //????????? ??? ???????????? ????????? ????????? ?????? ?????????

                if (binding.etContent.text.toString() == "") {
                    Toast.makeText(activity, "??? ??? ????????? ???????????????.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                task.title = binding.etContent.text.toString()
                task.memo = binding.etMemo.text.toString()
                task.alramtime = binding.tvAlram.text.toString()
                task.img = imgUri // ????????? ?????? ?????? ?????? ?????? " "
                Log.d("taskdetail", "add task tvSave $task")

                if(this::newImageUri.isInitialized) { //newImageUri??? ????????? ?????? ???????????? ???????????? ?????????
                    Log.d("taskdetail", "firebase??? ????????? ?????? ${task.img}")
                    viewModel.updateFireBaseStorage(newImageUri)
                    return@setOnClickListener
                } else {
                    Log.d("taskdetail", "????????? ?????? ?????? ${task.img}")
                    if(taskPositon == -1) {
                        Log.d("taskdetail", "viewModel.addTask ${task}")
                        viewModel.addTask(task)
                    } else {
                        Log.d("taskdetailupdateTask", "viewModel.updateTask ${task}")
                        viewModel.updateTask(task)
                    }
                }

        }

        binding.tvCancle.setOnClickListener { // ??????
            viewModel.setTaskResponse(3)
            viewModel.updateImage("")
            val fragmentManager = requireActivity().supportFragmentManager;
            fragmentManager.beginTransaction().remove(this).commit();
        }

        binding.tvDelete.setOnClickListener { // ??????
            val builder = AlertDialog.Builder(context as Activity)
                .setTitle("??? ??? ??????")
                .setMessage("?????? ?????????????????????????")
                .setPositiveButton("??????",
                    DialogInterface.OnClickListener{ dialog, which ->
                        viewModel.deleteTask(task.taskSeq)
                        val fragmentManager = requireActivity().supportFragmentManager;
                        fragmentManager.beginTransaction().remove(this).commit();
                    })
                .setNegativeButton("??????",
                    DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(context as Activity, "??????", Toast.LENGTH_SHORT).show()
                    })

            builder.show()
        }

        binding.tvAlram.setOnClickListener { // ?????? ??????

        }


        binding.imgPicture.setOnClickListener { // ?????? ????????????
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
                .setDeniedMessage("????????? ??????????????????.")
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

