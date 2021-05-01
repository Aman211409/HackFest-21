package com.hackfest21.covigenix.UI.Community

import android.os.Bundle
import android.util.Log
import android.util.Patterns.DOMAIN_NAME
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hackfest21.covigenix.Adapter.GetCommunityPostFragmentAdapter
import com.hackfest21.covigenix.Adapter.ProviderRequestsListAdapter
import com.hackfest21.covigenix.HelperClass
import com.hackfest21.covigenix.Model.CommunityPostModel
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ViewModel.CommunityPostViewModel
import com.hackfest21.covigenix.ViewModel.RequestViewModel
import kotlinx.android.synthetic.main.fragment_community_availability.view.*
import kotlinx.android.synthetic.main.fragment_community_requests.*
import kotlinx.android.synthetic.main.fragment_provider_requests_list.*
import kotlin.properties.Delegates

class CommunityRequestsFragment : Fragment() {

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

        communityPostViewModel.responseGetCommunityPost().observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let{
                list = it.posts as ArrayList<CommunityPostModel>

                recyclerViewPatients.adapter = GetCommunityPostFragmentAdapter(list)
                recyclerViewPatients.apply{
                    layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                }
            }
        })

       communityPostViewModel.errorString().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(this@CommunityRequestsFragment.context, it, Toast.LENGTH_LONG).show()
            }
        })

        Log.d("TAG", "onViewCreated: Ouch")
        communityPostViewModel.getCommunityPost(0)

    }

    companion object {
        val POSTS_LIST = "PostsList"
        private const val TAG = "GetAllRequestedItemsPost"
    }
}
