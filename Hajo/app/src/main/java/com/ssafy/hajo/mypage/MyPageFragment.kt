package com.ssafy.hajo.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.FragmentMyPageBinding
import com.ssafy.hajo.diary.DiaryActivity
import com.ssafy.hajo.diary.DiaryHomeActivity
import com.ssafy.hajo.registration.UserViewModel
import com.ssafy.hajo.repository.dao.UserRepository
import com.ssafy.hajo.repository.dto.response.GrandPlanResponse
import com.ssafy.hajo.settings.SettingsActivity
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.runBlocking
import java.util.*

class MyPageFragment : Fragment() {
    private lateinit var binding : FragmentMyPageBinding
    private lateinit var recordAdapter: RecordAdapter
    private val userViewModel by activityViewModels<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_page, container, false)

        this@MyPageFragment.userViewModel.getGrandplans()

        Log.d("MyPageFragment", "userInfo : ${userViewModel.user.value!!}")

        recordAdapter = RecordAdapter(requireContext())
        recordAdapter.setData(this@MyPageFragment.userViewModel.grandplans.value!!)

        binding.apply {
            userViewModel = this@MyPageFragment.userViewModel
            lifecycleOwner = this@MyPageFragment

            if(this@MyPageFragment.userViewModel.grandplans.value!!.isEmpty()) {
                tvNoPlan.visibility = View.VISIBLE
                rvRecord.visibility = View.GONE
            }
            rvRecord.layoutManager = LinearLayoutManager(requireContext())
            rvRecord.adapter = recordAdapter

            ivEditUserInfo.setOnClickListener {
                startActivity(Intent(requireContext(), UserInfoEditActivity::class.java))
            }
            ivSetting.setOnClickListener{
                val intent = Intent(context, SettingsActivity::class.java)
                startActivity(intent)
            }
            ivUserDiary.setOnClickListener {
                startActivity(Intent(requireContext(), DiaryHomeActivity::class.java))
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        this@MyPageFragment.userViewModel.getTasks()
        this@MyPageFragment.userViewModel.getUserInfo()
        Log.d("TAG", "image url: ${this@MyPageFragment.userViewModel.user.value!!.userImg}")
        Glide.with(requireContext())
            .load(this@MyPageFragment.userViewModel.user.value!!.userImg).fitCenter()
            .into(binding.ivUserPicture)

    }

}