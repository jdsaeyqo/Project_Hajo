package com.ssafy.hajo.settings.quest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val TABS_NUM = 2

class QuestViewPagerAdapter(fragmentManager: FragmentManager, lifecycle : Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int = TABS_NUM

    override fun createFragment(position: Int): Fragment {
       when(position){
           0 -> return QuestAllFragment()
           1 -> return MyQuestFragment()
       }
        return MyQuestFragment()
    }

}