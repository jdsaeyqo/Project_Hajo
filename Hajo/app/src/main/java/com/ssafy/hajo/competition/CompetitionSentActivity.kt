package com.ssafy.hajo.competition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.R
import com.ssafy.hajo.databinding.ActivityCompetitionSentBinding
import com.ssafy.hajo.entity.Request
import com.ssafy.hajo.registration.UserViewModel
import com.ssafy.hajo.repository.dao.CompetitionRepository
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetitionSentActivity : AppCompatActivity() {
    lateinit var binding : ActivityCompetitionSentBinding
    lateinit var competitionSentAdapter: CompetitionSentAdapter
    private val userViewModel by viewModels<UserViewModel>()
    val datas = mutableListOf<Request>()

    private val competitionRepository by lazy {
        CompetitionRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompetitionSentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this@CompetitionSentActivity.userViewModel.getUserInfo()

        // 화면이 위로 올라오는 Animation 효과 적용
        overridePendingTransition(R.anim.translate_left, R.anim.none)

        binding.btnClose.setOnClickListener {
            finish()
        }

        val token = GlobalApplication.userPrefs.getString("jwt","")!!
        val userId = GlobalApplication.userPrefs.getString("uid","")!!

        CoroutineScope(Dispatchers.Main).launch {
            val res = competitionRepository.getMatchSent(token, userId)
            Log.d("CompetitionSentActivity", "getMatchSent: ${res.body()}")
            if(res.isSuccessful && res.body() != null) {
                res.body()!!.forEach {
                    datas.add(Request(it.pschallSeq, userId, it.matchUserUid, it.matchUserNickname, it.matchUserExp,it.matchUserTitle, it.matchGrandplanTitle, it.grandplanTitle, it.matchPeriod))
                }

                competitionSentAdapter = CompetitionSentAdapter(this@CompetitionSentActivity.userViewModel.user.value!!)
                competitionSentAdapter.setData(datas)

                binding.rvCompetitionRequest.layoutManager = LinearLayoutManager(this@CompetitionSentActivity)
                binding.rvCompetitionRequest.adapter = competitionSentAdapter
            }
            else {
                Log.d("CompetitionSentActivity", "신청한 경쟁전 불러오기 오류")
                //datas.add(Request(1,userId,"testuid1","상대닉네임", 5,"<성실한>","대플랜1","대플랜11",3))
                //datas.add(Request(2,userId,"testuid2", "상대닉네임2",3,"<성실한>","대플랜2","대플랜22",5))
            }
        }


    }

    override fun finish() {
        super.finish()
        // 화면이 아래로 내려가는 Animation 효과 적용
        overridePendingTransition(R.anim.none,R.anim.translate_right)
    }
}