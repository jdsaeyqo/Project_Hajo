package com.ssafy.hajo.competition

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.hajo.R
import com.ssafy.hajo.mypage.SettingActivity
import com.ssafy.hajo.databinding.FragmentCompetitionBinding
import com.ssafy.hajo.registration.UserViewModel
import com.ssafy.hajo.repository.dao.CompetitionRepository
import com.ssafy.hajo.util.*
import com.ssafy.hajo.util.GlobalApplication.Companion.userPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CompetitionFragment : Fragment() {
    lateinit var binding : FragmentCompetitionBinding
    private val competitionViewModel by viewModels<CompetitionViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()

    lateinit var matchAdapter: MatchAdapter
    lateinit var competitionReceivedAdapter: CompetitionReceivedAdapter

    private lateinit var token : String
    private lateinit var userId : String

    private val competitionRepository by lazy {
        CompetitionRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_competition, container, false)
        binding.apply {
            userViewModel = this@CompetitionFragment.userViewModel
            lifecycleOwner = this@CompetitionFragment
        }

        // 유저의 정보를 가져온다.
//        this@CompetitionFragment.userViewModel.getUser("testuid1")
        token = userPrefs.getString("jwt","")!!
        userId = userPrefs.getString("uid","")!!

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sliding 효과 적용
        val sp = binding.mainFrame
        sp.addPanelSlideListener(PanelEventListener())

        this@CompetitionFragment.competitionViewModel.getUser()
        this@CompetitionFragment.competitionViewModel.getMatchRequest()
        this@CompetitionFragment.competitionViewModel.getNowMatch()

        // 뷰모델 내 list의 값이 변경되면 adapter의 list를 업데이트한다.
        this@CompetitionFragment.competitionViewModel.match.observe(viewLifecycleOwner) {
            matchAdapter.setData(it)
            matchAdapter.notifyDataSetChanged()
            if(it.isEmpty()) {
                binding.tvNoMatch.visibility = View.VISIBLE
            }
            else {
                binding.tvNoMatch.visibility = View.GONE
            }
        }

        // 현재 진행중인 경쟁전 Adapter 생성
        // 리턴값으로는 match list의 position 값을 가져온다.
        matchAdapter = MatchAdapter(requireContext()) {
            val pos = it
            val result = competitionViewModel.match.value!![pos].result
            val seq = competitionViewModel.match.value!![pos].nowSeq

            when(result) {
                "L" -> {
                    LoseDialog(requireContext(), competitionViewModel.match.value!![pos]) {
                        // 포인트 결과 서버에 전송
                        updateResult(seq, "X", pos)
                    }.show()
                }
                "D" -> {
                    DrawDialog(requireContext(), competitionViewModel.match.value!![pos]) {
                        // 포인트 결과 서버에 전송
                        updateResult(seq, "X", pos)
                    }.show()
                }
                "W" -> {
                    // 포인트 결과 서버에 전송
                    WinDialog(requireContext(), competitionViewModel.match.value!![pos]){
                        // it -> point
                        updateResult(seq, "O", pos)
                    }.show()
                }
                else -> {
                    // 포인트 결과 서버에 전송
                    WinwinDialog(requireContext(), competitionViewModel.match.value!![pos]){ res, point ->
                        // it -> point
                        updateResult(seq, res, pos)
                    }.show()
                }
            }

        }

        // 뷰모델 내 list의 값이 변경되면 adapter의 list를 업데이트한다.
        this@CompetitionFragment.competitionViewModel.request.observe(viewLifecycleOwner) {
            competitionReceivedAdapter.setData(it)
            competitionReceivedAdapter.notifyDataSetChanged()

            // 받은 제안서가 존재할 경우 받은 제안서 리스트를 panel에 출력
            // 없을 경우 제안서가 없다는 text 출력
            if(this@CompetitionFragment.competitionViewModel.request.value!!.isNotEmpty()) {
                binding.tvNoRequest.visibility = View.GONE
                binding.rvRequestReceived.visibility = View.VISIBLE
            }
        }

        // 받은 제안서 Adapter 생성
        // 리턴값으로는 제안서 list의 position과 수락을 눌렀는지 여부에 대한 값을 가져온다.
        competitionReceivedAdapter = CompetitionReceivedAdapter { pos: Int, accepted: Boolean ->
            val seq = competitionViewModel.request.value!![pos].matchSeq

            if (accepted) {
                // 수락
                val hm = HashMap<String, Any>()
                hm["pschallSeq"] = seq

                lifecycleScope.launch(Dispatchers.Main) {
                    val res = competitionRepository.acceptMatch(token, hm)
                    if(res.isSuccessful && res.body() != null) {
                        val body = res.body()!!
                        if(body["message"] == "success") {
                            Log.d("CompetitionFragment", "신청 받은 경쟁전 수락 성공")
                            this@CompetitionFragment.competitionViewModel.getNowMatch()
                            competitionViewModel.deleteRequest(pos)
                        }
                        else {
                            Log.d("CompetitionFragment", "신청 받은 경쟁전 수락 실패")
                        }
                    }
                }
            }
            else {
                // 거절
                lifecycleScope.launch(Dispatchers.Main) {
                    val res = competitionRepository.rejectMatch(token, seq)
                    if(res.isSuccessful && res.body() != null) {
                        val body = res.body()!!
                        if(body["message"] == "success") {
                            Log.d("CompetitionFragment", "신청 받은 경쟁전 거절 성공")
                            competitionViewModel.deleteRequest(pos)
                        }
                        else {
                            Log.d("CompetitionFragment", "신청 받은 경쟁전 거절 실패")
                        }
                    }
                }
            }

        }

        binding.apply {
            // 처음에 받은 제안서 panel의 scroll을 할 수 없게 한다.
            // 이유는 panel을 올리지 않았는데도 scroll이 되기 때문.
            nsRequest.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean = true
            })

            // 받은 제안서 NestedScrollView가 가장 위로 올라왔을 때만 panel의 slide를 활성화한다.
            // NestedScrollView를 아래로 내리다가 layout이 아래로 slide가 되는 경우가 있기 때문.
            nsRequest.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
                override fun onScrollChange(
                    v: NestedScrollView?,
                    scrollX: Int,
                    scrollY: Int,
                    oldScrollX: Int,
                    oldScrollY: Int
                ) {
                    sp.isEnabled = scrollY == 0
                }
            })

            // 받은 제안서 위에 있는 swipe바로는 panel을 열고 닫을 수 있게 한다.
            swipe.setOnTouchListener(object : View.OnTouchListener{
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when(event!!.action) {
                        MotionEvent.ACTION_DOWN -> {
                            sp.isEnabled = true
                        }
                    }
                    return false
                }
            })

            // 현재 진행중인 경쟁전 recyclerView 셋팅
            rvMatch.layoutManager = LinearLayoutManager(requireContext())
            rvMatch.adapter = matchAdapter

            // 받은 제안서 recyclerView 셋팅
            rvRequestReceived.layoutManager = LinearLayoutManager(requireContext())
            rvRequestReceived.adapter = competitionReceivedAdapter

            // 나의 전적 보기 버튼 클릭 이벤트
            tvHistory.setOnClickListener {
                if(binding.mainFrame.panelState != SlidingUpPanelLayout.PanelState.EXPANDED)
                    startActivity(Intent(requireContext(), HistoryActivity::class.java))
            }

            // 신청한 경쟁전 버튼 클릭 이벤트
            tvSent.setOnClickListener {
                if(binding.mainFrame.panelState != SlidingUpPanelLayout.PanelState.EXPANDED)
                    startActivity(Intent(requireContext(), CompetitionSentActivity::class.java))

            }

            // 매칭하기 버튼 클릭 이벤트
            tvMatch.setOnClickListener {
                if(binding.mainFrame.panelState != SlidingUpPanelLayout.PanelState.EXPANDED)
                    startActivity(Intent(requireContext(), MatchActivity::class.java))
            }

            // 제안서 등록 버튼 클릭 이벤트
            tvRegister.setOnClickListener {
                if(binding.mainFrame.panelState != SlidingUpPanelLayout.PanelState.EXPANDED) {
                    // uid로 해당 유저의 경쟁전 참여 가능한 대플랜 목록 가져오기
                    this@CompetitionFragment.competitionViewModel.getPlans()
                    val lists = this@CompetitionFragment.competitionViewModel.plans.value!!
                    Log.d("CompetitionFragment", "경쟁전 등록 가능한 대플랜 : $lists")
                    // 제안서 등록 다이얼로그 생성
                    if(lists.isNotEmpty()) {
                        RequestWriteDialog(requireContext(), lists) { pos, period ->
                            Log.d("CompetitionFragment", "choosed plan: ${lists[pos]}")
                            val hm = HashMap<String, Any>()
                            hm["userUid"] = userId
                            hm["grandplanSeq"] = lists[pos].planSeq
                            hm["matchPeriod"] = period

                            lifecycleScope.launch(Dispatchers.Main) {
                                val res = competitionRepository.registerRequest(token, hm)
                                Log.d("CompetitionFragment", "response: ${res.body()}")
                                if(res.isSuccessful && res.body() != null) {
                                    val body = res.body()!!

                                    if(body["message"] == "success") {
                                        Toast.makeText(requireContext(),"제안서가 등록되었습니다.",Toast.LENGTH_SHORT).show()
                                        Log.d("CompetitionFragment", "registerRequest 성공")
                                    }
                                    else {
                                        Log.d("CompetitionFragment", "registerRequest 실패")
                                    }
                                }
                            }
                        }.show()
                    }
                    else {
                        Toast.makeText(requireContext(),"경쟁전 참여 가능한 대플랜이 없습니다!",Toast.LENGTH_SHORT).show()
                    }

                }
            }


            this@CompetitionFragment.competitionViewModel.user.observe(viewLifecycleOwner) {
                swRegister.isChecked = this@CompetitionFragment.competitionViewModel.user.value!!.userOnmatch
            }

            // 경쟁전 참여 설정 리스너
            swRegister.setOnCheckedChangeListener { buttonView, isChecked ->
                if(this@CompetitionFragment.competitionViewModel.user.value!!.userOnmatch != isChecked) {
                    this@CompetitionFragment.competitionViewModel.getPlans()
                    if(this@CompetitionFragment.competitionViewModel.plans.value!!.isEmpty()) {
                        Toast.makeText(requireContext(),"경쟁전에 참여할 수 있는 대플랜이 없습니다!",Toast.LENGTH_SHORT).show()
                        swRegister.isChecked = false
                    }
                    else {
                        changeStatus()
                        this@CompetitionFragment.competitionViewModel.changeOnMatch(isChecked)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun changeStatus() {
        val hm = HashMap<String, Any>()
        hm["userUid"] = userId
        runBlocking {
            val res = competitionRepository.changeStatus(token,hm)
            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!
                if(body["message"] == "success") {
                    Log.d("CompetitionFragment", "changeStatus Success")
                }
                else {
                    Log.d("CompetitionFragment", "changeStatus Failed")
                    binding.swRegister.isChecked = this@CompetitionFragment.competitionViewModel.user.value!!.userOnmatch
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateResult(seq: Long, result: String, pos: Int) {
        val hm = HashMap<String,Any>()
        hm["nowSeq"] = seq
        hm["roulette"] = result

        lifecycleScope.launch(Dispatchers.Main) {
            val res = competitionRepository.updateResult(token, hm)
            if(res.isSuccessful && res.body() != null) {
                val body = res.body()!!
                if(body["message"] == "success") {
                    competitionViewModel.deleteMatch(pos)
                    Log.d("CompetitionFragment", "룰렛 결과 업데이트 성공")
                }
                else {
                    Log.d("CompetitionFragment", "룰렛 결과 업데이트 실패")
                }
            }
        }
    }


    inner class PanelEventListener: SlidingUpPanelLayout.PanelSlideListener {
        override fun onPanelSlide(panel: View?, slideOffset: Float) { }

        @SuppressLint("ClickableViewAccessibility")
        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {
            when(newState) {
                // panel이 닫혀 있을 때는 받은 제안서 NestedScrollView의 scroll을 할 수 없게 한다.
                // panel이 내려갔을 때 NestedScrollView의 위치를 최상단으로 올린다.
                SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                    binding.nsRequest.setOnTouchListener(object : View.OnTouchListener {
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean = true
                    })
                    binding.nsRequest.scrollY = 0
                }
                // panel이 열려 있을 때는 받은 제안서 NestedScrollView의 scroll을 할 수 있게 한다.
                SlidingUpPanelLayout.PanelState.EXPANDED -> {
                    binding.nsRequest.setOnTouchListener(object : View.OnTouchListener {
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean = false
                    })
                }
            }
        }
    }
}