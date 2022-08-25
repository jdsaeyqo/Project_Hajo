package com.ssafy.hajo.settings.quest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.databinding.FragmentMyQuestBinding
import com.ssafy.hajo.databinding.FragmentQuestAllBinding
import com.ssafy.hajo.util.RecyclerItemDecoration

class MyQuestFragment : Fragment() {


    private val questViewModel: QuestViewModel by viewModels()

    lateinit var questMyAdapter: MyQuestAdapter

    private lateinit var binding: FragmentMyQuestBinding
    lateinit var ctx : Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        questViewModel.getMyQuest()
        binding = FragmentMyQuestBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questViewModel.myQuestList.observe(viewLifecycleOwner) { questList ->
            if (questViewModel.myQuestList.value != null) {
                questMyAdapter.setList(questList)
            }
        }
        questMyAdapter = MyQuestAdapter(ctx){
            questViewModel.getReward(it)

        }

        binding.rvMyQuest.adapter = questMyAdapter
        binding.rvMyQuest.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMyQuest.addItemDecoration(RecyclerItemDecoration(0, 30, 0, 0))

    }
}