package com.hackfest21.covigenix.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hackfest21.covigenix.UI.Community.CommunityAvailabilityFragment
import com.hackfest21.covigenix.UI.Community.CommunityRequestsFragment
import com.hackfest21.covigenix.UI.Prediction.AudioPredictionFragment
import com.hackfest21.covigenix.UI.Prediction.ImagePredictionFragment

class PredictionFragmentStateAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        if(position%2==0)
            return AudioPredictionFragment()
        return ImagePredictionFragment()
    }
}