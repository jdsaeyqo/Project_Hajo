package com.ssafy.hajo.competition

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.FragmentMyRequestBinding
import com.ssafy.hajo.repository.dao.CompetitionRepository
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyRequestFragment: Fragment() {
    private lateinit var binding: FragmentMyRequestBinding
    lateinit var requestFormAdapter: RequestFormAdapter
    private val matchFormViewModel by activityViewModels<MatchFormViewModel>()
    private var curPos = 0

    val token = GlobalApplication.userPrefs.getString("jwt","")!!
    val userId = GlobalApplication.userPrefs.getString("uid","")!!

    private val competitionRepository by lazy {
        CompetitionRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyRequestBinding.inflate(layoutInflater)

        this@MyRequestFragment.matchFormViewModel.getMyRequests()
        requestFormAdapter = RequestFormAdapter(requireContext())

        matchFormViewModel.myRequestForms.observe(viewLifecycleOwner) {
            updateAdapter()
        }

        binding.cvRemove.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                Log.d("MyRequestFragment", "제안서 삭제 : ${matchFormViewModel.myRequestForms.value!![curPos]}")
                val res = competitionRepository.deleteRequest(token, matchFormViewModel.myRequestForms.value!![curPos].matchGrandPlanSeq)

                if(res.isSuccessful && res.body() != null) {
                    val body = res.body()!!
                    if(body["message"] == "success") {
                        matchFormViewModel.deleteMyRequest(curPos)
                        Log.d("MyRequestFragment", "deleteRequest 성공")
                    }
                    else {
                        Log.d("MyRequestFragment", "deleteRequest 실패패")
                   }
                }
            }

        }

        binding.vpRequestForm.apply {
            adapter = requestFormAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d("tttt", "onPageSelected: $position")
                    curPos = position
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

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter() {
        requestFormAdapter.setData(matchFormViewModel.myRequestForms.value!!)
        requestFormAdapter.notifyDataSetChanged()
    }
}