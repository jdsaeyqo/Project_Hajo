package com.ssafy.hajo.competition

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.FragmentProfileBinding
import com.ssafy.hajo.entity.Request
import com.ssafy.hajo.repository.dao.CompetitionRepository
import com.ssafy.hajo.repository.dto.request.MatchRequest
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.ProfilePlanSelectDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment: Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var profileFormAdapter: ProfileFormAdapter
    private val matchFormViewModel by activityViewModels<MatchFormViewModel>()
    private var curPos = 0

    val token = GlobalApplication.userPrefs.getString("jwt","")!!
    val userId = GlobalApplication.userPrefs.getString("uid","")!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        this@ProfileFragment.matchFormViewModel.getProfiles()
        profileFormAdapter = ProfileFormAdapter(requireContext())

        matchFormViewModel.profileForms.observe(viewLifecycleOwner) {
            updateAdapter()
        }

        binding.vpProfileForm.apply {
            adapter = profileFormAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    curPos = position
                    Log.d("tttt", "onPageSelected: $position")
                }
            })

            val offsetBetweenPages = resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages)
            setPageTransformer { page, position ->
                val myOffset = position * -(2 * offsetBetweenPages)
                if(position < -1) {
                    page.translationX = -myOffset
                }
                else if(position <= 1) {
                    val scaleFactor = 0.8f.coerceAtLeast(1 - Math.abs(position))
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                }
                else {
                    page.alpha = 0f
                    page.translationX = myOffset
                }
            }
        }

        // 경쟁 신청
        binding.cvTry.setOnClickListener {
            val profileForm = this@ProfileFragment.matchFormViewModel.profileForms.value!![curPos]
            val enemyUid = profileForm.userUid

            // 내 대플랜 리스트 불러오기
            val myPlanList = this@ProfileFragment.matchFormViewModel.plans.value!!
            // uid로 해당 유저 대플랜 목록 가져오기
            lifecycleScope.launch(Dispatchers.Main) {
                val res = CompetitionRepository().getGrandPlans(token, enemyUid)
                if(res.isSuccessful && res.body() != null) {
                    val enemyPlanList = res.body()!!

                    ProfilePlanSelectDialog(requireContext(), myPlanList, enemyPlanList) { mine, enemy, period ->
                        Log.d("ProfileFragment", "my Plan selected : ${myPlanList[mine].planTitle}")
                        Log.d("ProfileFragment", "enemy Plan selected : ${enemyPlanList[enemy].planTitle}")

                        val myPlan = myPlanList[mine]
                        val ePlan = enemyPlanList[enemy]
                        lifecycleScope.launch(Dispatchers.Main) {
                            val result = CompetitionRepository().sendRequest(token, MatchRequest(ePlan.planSeq,myPlan.planSeq,period, userId, enemyUid))
                            if(result.isSuccessful && result.body() != null) {
                                val body = result.body()!!
                                if(body["message"] == "success") {
                                    Log.d("ProfileFragment", "경쟁전 신청 성공")
                                    Toast.makeText(requireContext(),"경쟁전 신청을 보냈습니다.",Toast.LENGTH_SHORT).show()
                                    //matchFormViewModel.deleteProfile(curPos)
                                }
                                else {
                                    Log.d("ProfileFragment", "경쟁전 신청 실패")
                                }
                            }
                        }
                    }.show()
                }
                else {
                    Log.d("ProfileFragment", "상대방 대플랜 가져오기 실패")
                }
            }
        }
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter() {
        profileFormAdapter.setData(this@ProfileFragment.matchFormViewModel.profileForms.value!!)
        profileFormAdapter.notifyDataSetChanged()
    }
}