package com.ssafy.hajo.competition

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.FragmentRequestBinding
import com.ssafy.hajo.repository.dao.CompetitionRepository
import com.ssafy.hajo.repository.dto.request.MatchRequest
import com.ssafy.hajo.repository.dto.response.RequestFormResponse
import com.ssafy.hajo.util.GlobalApplication
import com.ssafy.hajo.util.GlobalApplication.Companion.userPrefs
import com.ssafy.hajo.util.ProfilePlanSelectDialog
import com.ssafy.hajo.util.RequestPlanSelectDialog
import com.ssafy.hajo.util.RequestWriteDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Math.abs

class RequestFragment: Fragment() {
    private lateinit var binding: FragmentRequestBinding
    lateinit var requestFormAdapter: RequestFormAdapter
    private val matchFormViewModel by activityViewModels<MatchFormViewModel>()
    private var curPos = 0

    private lateinit var token : String
    private lateinit var userId : String

    private val competitionRepository by lazy {
        CompetitionRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestBinding.inflate(inflater, container, false)

        token = userPrefs.getString("jwt","")!!
        userId = userPrefs.getString("uid","")!!

        this@RequestFragment.matchFormViewModel.getRequests()
        requestFormAdapter = RequestFormAdapter(requireContext())

        matchFormViewModel.requestForms.observe(viewLifecycleOwner) {
            updateAdapter()
        }

        binding.vpRequestForm.apply {
            adapter = requestFormAdapter
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
                    val scaleFactor = 0.8f.coerceAtLeast(1 - abs(position))
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
            // 내 대플랜 리스트 불러오기
            val myPlanList = matchFormViewModel.plans.value!!
            val lists= mutableListOf<String>()
            myPlanList.forEach {
                lists.add(it.planTitle)
            }

            // 상대 대플랜 id 값
            val requestForm = matchFormViewModel.requestForms.value!![curPos]
            val planId = requestForm.matchGrandPlanSeq
            val matchUserUid = requestForm.matchUserUid

            RequestWriteDialog(requireContext(), myPlanList) { pos, period ->
                val request = MatchRequest(planId, myPlanList[pos].planSeq, period, userId, matchUserUid)
                Log.d("RequestFragment", "request: $request")
                lifecycleScope.launch(Dispatchers.Main) {
                    val res = competitionRepository.sendRequest(token, request)
                    if(res.isSuccessful && res.body() != null) {
                        val body = res.body()!!
                        if(body["message"] == "success") {
                            Log.d("RequestFragment", "sendRequest: Success!")
                            Toast.makeText(requireContext(),"경쟁전 신청을 보냈습니다.", Toast.LENGTH_SHORT).show()
                            //matchFormViewModel.deleteRequest(curPos)
                        }
                        else {
                            Log.d("RequestFragment", "sendRequest: Failed")
                        }
                    }else{
                        val body = res.body()
                        Log.d("RequestFragment", "body: $body")
                        Log.d("RequestFragment", "err: ${res.errorBody()?.string()}")
                    }
                }
            }.show()
        }

        return binding.root

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter() {
        requestFormAdapter.setData(matchFormViewModel.requestForms.value!!)
        requestFormAdapter.notifyDataSetChanged()
    }
}