package com.hackfest21.covigenix.UI.PatientServices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Adapter.MyRequestsHomeAdapter
import com.hackfest21.covigenix.HelperClass.Companion.createMap
import com.hackfest21.covigenix.Model.Request.MyRequestsModel
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ViewModel.RequestViewModel
import kotlinx.android.synthetic.main.fragment_my_requests_home.view.*

class MyRequestsHomeFragment : Fragment(), MyRequestsHomeAdapter.HomeListener {

    lateinit var requestViewModel: RequestViewModel
    lateinit var list: List<MyRequestsModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_requests_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestViewModel = ViewModelProvider(requireActivity()).get(RequestViewModel::class.java)

        requestViewModel.responseGetMyRequests().observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                list = it.requests

                view.recyclerViewRequests.adapter = MyRequestsHomeAdapter(list, this, createMap())
                view.recyclerViewRequests.apply{
                    layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                }
            }
        })

        requestViewModel.errorString().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        })

        requestViewModel.getMyRequests()
    }

    override fun onClick(pos: Int) {
        Toast.makeText(context, list[pos].area, Toast.LENGTH_SHORT).show()

        requestViewModel.hospitalList.postValue(list[pos])

        requireView().findNavController().navigate(R.id.home_to_list)
    }
}