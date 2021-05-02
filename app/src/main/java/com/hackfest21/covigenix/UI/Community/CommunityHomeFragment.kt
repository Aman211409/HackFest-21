package com.hackfest21.covigenix.UI.Community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hackfest21.covigenix.Adapter.CommunityFragmentStateAdapter
import com.hackfest21.covigenix.R

class CommunityHomeFragment : Fragment() {

    lateinit var adapter: CommunityFragmentStateAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)

        val viewPager: ViewPager2 = view.findViewById<ViewPager2>(R.id.pager)
        adapter = CommunityFragmentStateAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if(position==0) "Requirements" else "Availability"
        }.attach()
        super.onViewCreated(view, savedInstanceState)


    }
}