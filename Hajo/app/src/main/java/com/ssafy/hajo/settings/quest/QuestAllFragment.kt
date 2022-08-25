package com.ssafy.hajo.settings.quest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.hajo.databinding.FragmentQuestAllBinding
import com.ssafy.hajo.util.RecyclerItemDecoration

class QuestAllFragment : Fragment() {

    private val questViewModel: QuestViewModel by viewModels()

    lateinit var questAllAdapter: QuestAllAdapter

    private lateinit var binding: FragmentQuestAllBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        questViewModel.getQuestList()
        binding = FragmentQuestAllBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questViewModel.questList.observe(viewLifecycleOwner) { questList ->
            if (questViewModel.questList.value != null) {
                questAllAdapter.setList(questList)
            }
        }
        questAllAdapter = QuestAllAdapter()
        binding.rvAllQuest.adapter = questAllAdapter
        binding.rvAllQuest.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvAllQuest.addItemDecoration(RecyclerItemDecoration(0, 30, 0, 0))

    }


}