package com.ssafy.hajo.competition

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.hajo.databinding.ListItemProfileFormBinding
import com.ssafy.hajo.entity.GrandPlan
import com.ssafy.hajo.repository.dao.CompetitionRepository
import com.ssafy.hajo.repository.dto.response.GrandPlanResponse
import com.ssafy.hajo.repository.dto.response.ProfileFormResponse
import com.ssafy.hajo.repository.dto.response.UserResponse
import com.ssafy.hajo.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFormAdapter(private val mContext: Context): RecyclerView.Adapter<ProfileFormAdapter.ViewHolder>() {
    private var datas = emptyList<UserResponse>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileFormAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = ListItemProfileFormBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileFormAdapter.ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    fun setData(datas: List<UserResponse>) {
        this.datas = datas
    }

    fun getData() = datas

    inner class ViewHolder(private val binding: ListItemProfileFormBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(profileForm: UserResponse) {
                binding.apply {
                    this.profileForm = profileForm

                    val str = "${profileForm.userWinwin}윈윈 ${profileForm.userWin}승 ${profileForm.userDraw}무 ${profileForm.userLose}패"
                    this.tvHistory.setText(str)

                    val token = GlobalApplication.userPrefs.getString("jwt","")!!
                    val userId = profileForm.userUid

                    // uid를 갖고 대플랜 목록 불러오기
                    CoroutineScope(Dispatchers.Main).launch {
                        val res = CompetitionRepository().getGrandPlans(token,userId)
                        Log.d("ProfileFormAdapter", "getGrandPlans : ${res.body()}")
                        if(res.isSuccessful && res.body() != null) {
                            val body = res.body()!!
                            rvGrandPlan.adapter = GrandPlanAdapter(mContext, body)
                            rvGrandPlan.layoutManager = LinearLayoutManager(mContext)
                        }
                        else {
                            Log.d("ProfileFormAdapter", "getGrandPlans 실패")
                        }
                    }

                }
            }
        }
}