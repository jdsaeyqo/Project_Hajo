package com.ssafy.hajo.plan

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ssafy.hajo.R
import com.ssafy.hajo.board.share.ShareBoardActivity
import com.ssafy.hajo.board.share.ShareMoreActivity
import com.ssafy.hajo.databinding.FragmentPlanBinding
import com.ssafy.hajo.repository.dto.LargePlanHomeDTO
import com.ssafy.hajo.repository.dto.response.GrandPlanHomeResponse
import com.ssafy.hajo.settings.SettingsActivity

class PlanFragment : Fragment() {

    private lateinit var binding: FragmentPlanBinding
    private lateinit var ctx: Context

    private val planHomeViewModel: PlanHomeViewModel by viewModels()

    private val ingList = mutableListOf<GrandPlanHomeResponse>()
    private val doneList = mutableListOf<GrandPlanHomeResponse>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanBinding.inflate(inflater, container, false)
        planHomeViewModel.getPlanHomeList()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoSharePlan.setOnClickListener {
            val intent = Intent(ctx, ShareBoardActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoBookmark.setOnClickListener {
            val intent = Intent(ctx, ShareMoreActivity::class.java)
            intent.putExtra("mainTitle", "북마크 한 글")
            startActivity(intent)
        }

        planHomeViewModel.planHomeList.observe(viewLifecycleOwner) { list ->
            if (planHomeViewModel.planHomeList.value != null) {
                list.forEach {
                    if (it.planRate != 100) {
                        ingList.add(it)
                    } else {
                        doneList.add(it)
                    }
                }
                if (ingList.isEmpty()) {
                    binding.tvNothingDoing.visibility = View.VISIBLE
                    binding.rvCurrentPlan.visibility = View.GONE

                }else{
                    binding.rvCurrentPlan.visibility = View.VISIBLE
                    binding.tvNothingDoing.visibility = View.GONE

                    val adapter = PlanHomeAdapter(ingList, ctx) { planSeq ->
                        val intent = Intent(ctx, PlanDetailActivity::class.java)
                        Log.d("PlanFragment", "planSeq: $planSeq")
                        intent.putExtra("planSeq", planSeq.toInt())
                        startActivity(intent)


                    }
                    binding.rvCurrentPlan.adapter = adapter
                    binding.rvCurrentPlan.layoutManager =
                        LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
                }

                if (doneList.isEmpty()) {
                    binding.tvNothingDone.visibility = View.VISIBLE
                    binding.rvDonePlan.visibility = View.GONE
                }else{
                    binding.tvNothingDone.visibility = View.GONE
                    binding.rvDonePlan.visibility = View.VISIBLE

                    binding.rvDonePlan.layoutManager =
                        LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
                    binding.rvDonePlan.adapter = PlanHomeAdapter(doneList, ctx) {
                }

                }

                initSpinnerAdapter()

            }
        }
    }

    private fun initSpinnerAdapter() {
        val spinnerList = mutableListOf("전체", "진행중인 플랜", "완료한 플랜")
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.item_home_spinner, spinnerList)
        binding.spinnerPlan.adapter = spinnerAdapter
        binding.spinnerPlan.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (spinnerList[position]) {

                        "진행중인 플랜" -> {
                            binding.tvCurrentPlan.visibility = View.VISIBLE
                            binding.rvCurrentPlan.visibility = View.VISIBLE
                            binding.rvDonePlan.visibility = View.GONE
                            binding.tvDonePlan.visibility = View.GONE
                            binding.tvNothingDone.visibility = View.GONE

                            if (ingList.isEmpty()) {
                                binding.tvNothingDoing.visibility = View.VISIBLE
                                binding.rvCurrentPlan.visibility = View.INVISIBLE
                            }
                        }
                        "완료한 플랜" -> {
                            binding.tvCurrentPlan.visibility = View.GONE
                            binding.rvCurrentPlan.visibility = View.GONE
                            binding.rvDonePlan.visibility = View.VISIBLE
                            binding.tvDonePlan.visibility = View.VISIBLE
                            binding.tvNothingDoing.visibility = View.GONE

                            if (doneList.isEmpty()) {
                                binding.tvNothingDone.visibility = View.VISIBLE
                                binding.rvDonePlan.visibility = View.INVISIBLE
                            }
                        }
                        "전체" -> {
                            binding.tvCurrentPlan.visibility = View.VISIBLE
                            binding.rvCurrentPlan.visibility = View.VISIBLE
                            binding.rvDonePlan.visibility = View.VISIBLE
                            binding.tvDonePlan.visibility = View.VISIBLE

                            if (ingList.isEmpty()) {
                                binding.rvCurrentPlan.visibility = View.INVISIBLE
                                binding.tvNothingDoing.visibility = View.VISIBLE
                            }

                            if (doneList.isEmpty()) {
                                binding.tvNothingDone.visibility = View.VISIBLE
                                binding.rvDonePlan.visibility = View.INVISIBLE
                            }
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
    }

}