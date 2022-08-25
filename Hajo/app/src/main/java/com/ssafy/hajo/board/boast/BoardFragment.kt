package com.ssafy.hajo.board.boast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.databinding.FragmentBoardBinding
import com.ssafy.hajo.repository.dto.response.BoastHomeResponse
import com.ssafy.hajo.settings.SettingsActivity

class BoardFragment : Fragment() {

    private lateinit var binding: FragmentBoardBinding
    private lateinit var ctx: Context

    private val boastViewModel : BoastViewModel by viewModels()

    private lateinit var boastPopularAdapter: BoastAdapter
    private lateinit var boastRecentAdapter: BoastAdapter
    private lateinit var boastLikeAdapter: BoastAdapter
    private lateinit var boastMyAdapter: BoastAdapter

    private lateinit var boastPopularList: ArrayList<BoastHomeResponse>
    private lateinit var boastRecentList: ArrayList<BoastHomeResponse>
    private lateinit var boastLikeList: ArrayList<BoastHomeResponse>
    private lateinit var boastMyList: ArrayList<BoastHomeResponse>
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(layoutInflater)

        boastViewModel.getBoastTop5()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnAddBoast.setOnClickListener {
            val intent = Intent(ctx, BoastAddActivity::class.java)
            intent.putExtra("mode",0)
            startActivity(intent)

        }
        initAdapter()
        initClickListener()

        boastViewModel.boastPopularList.observe(viewLifecycleOwner) {
            if (boastViewModel.boastPopularList != null) {
                boastPopularList = boastViewModel.boastPopularList.value!!

                boastPopularAdapter.setList(boastPopularList)
            }
        }

        boastViewModel.boastRecentList.observe(viewLifecycleOwner) {
            if (boastViewModel.boastRecentList != null) {
                boastRecentList = boastViewModel.boastRecentList.value!!
                boastRecentAdapter.setList(boastRecentList)
            }
        }

        boastViewModel.boastLikeList.observe(viewLifecycleOwner) {
            if (boastViewModel.boastLikeList != null) {
                boastLikeList = boastViewModel.boastLikeList.value!!

                boastLikeAdapter.setList(boastLikeList)
            }
        }

        boastViewModel.boastMyList.observe(viewLifecycleOwner) {
            if (boastViewModel.boastPopularList != null) {
                boastMyList = boastViewModel.boastMyList.value!!

                boastMyAdapter.setList(boastMyList)
            }
        }

    }

    private fun initClickListener() {
        binding.tvPopularMore.setOnClickListener {
            val intent = Intent(ctx, BoastMoreActivity::class.java)
            intent.putExtra("mainTitle", "인기글")
            startActivity(intent)
        }

        binding.tvRecentMore.setOnClickListener {
            val intent = Intent(ctx, BoastMoreActivity::class.java)
            intent.putExtra("mainTitle", "최신글")
            startActivity(intent)
        }

        binding.tvLikeMore.setOnClickListener {
            val intent = Intent(ctx, BoastMoreActivity::class.java)
            intent.putExtra("mainTitle", "내가 좋아요 한 글")
            startActivity(intent)
        }
        binding.tvMyMore.setOnClickListener {
            val intent = Intent(ctx, BoastMoreActivity::class.java)
            intent.putExtra("mainTitle", "내가 작성한 글")
            startActivity(intent)
        }
    }

    private fun initAdapter() {
       boastPopularAdapter = BoastAdapter(ctx) {
            val intent = Intent(ctx, BoastDetailActivity::class.java)
            intent.putExtra("boastSeq", it)
            startActivity(intent)
        }
        binding.rvPopularBoard.layoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularBoard.adapter = boastPopularAdapter

        boastRecentAdapter = BoastAdapter(ctx) {
            val intent = Intent(ctx, BoastDetailActivity::class.java)
            intent.putExtra("boastSeq", it)
            startActivity(intent)
        }
        binding.rvRecentBoard.layoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecentBoard.adapter = boastRecentAdapter

        boastLikeAdapter = BoastAdapter(ctx) {
            val intent = Intent(ctx, BoastDetailActivity::class.java)
            intent.putExtra("boastSeq", it)
            startActivity(intent)
        }
        binding.rvLikeBoard.layoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        binding.rvLikeBoard.adapter = boastLikeAdapter

        boastMyAdapter = BoastAdapter(ctx) {
            val intent = Intent(ctx, BoastDetailActivity::class.java)
            intent.putExtra("boastSeq", it)
            startActivity(intent)
        }
        binding.rvMyBoard.layoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMyBoard.adapter = boastMyAdapter
    }

    override fun onResume() {
        super.onResume()
        boastViewModel.getBoastTop5()
    }

}