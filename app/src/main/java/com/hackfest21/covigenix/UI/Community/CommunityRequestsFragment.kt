package com.hackfest21.covigenix.UI.Community

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Adapter.GetCommunityPostFragmentAdapter
import com.hackfest21.covigenix.Model.CommunityPostModel
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ViewModel.CommunityPostViewModel
import kotlinx.android.synthetic.main.fragment_community_requests.*

class CommunityRequestsFragment(val type: Int) : Fragment() {

    private lateinit var communityPostViewModel: CommunityPostViewModel
    lateinit var list: ArrayList<CommunityPostModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_community_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communityPostViewModel = ViewModelProvider(requireActivity()).get(CommunityPostViewModel::class.java)

        if(type==0){
            communityPostViewModel.responseGetCommunityPost0().observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let{

                    progress_circular_layout.visibility = View.INVISIBLE

                    list = it.posts as ArrayList<CommunityPostModel>

                    recyclerViewPosts.adapter = GetCommunityPostFragmentAdapter(list)
                    recyclerViewPosts.apply{
                        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        setHasFixedSize(true)
                    }
                }
            })
        }else{
            communityPostViewModel.responseGetCommunityPost1().observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let{

                    progress_circular_layout.visibility = View.INVISIBLE

                    list = it.posts as ArrayList<CommunityPostModel>

                    recyclerViewPosts.adapter = GetCommunityPostFragmentAdapter(list)
                    recyclerViewPosts.apply{
                        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        setHasFixedSize(true)
                    }
                }
            })
        }

       communityPostViewModel.errorString().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let{
                progress_circular_layout.visibility = View.INVISIBLE
                Toast.makeText(this@CommunityRequestsFragment.context, it, Toast.LENGTH_LONG).show()
            }
        })

        Log.d("TAG", "onViewCreated: Ouch")
        communityPostViewModel.getCommunityPost(type)

    }

    companion object {
        private const val TAG = "GetAllRequestedItemsPost"
    }
}
