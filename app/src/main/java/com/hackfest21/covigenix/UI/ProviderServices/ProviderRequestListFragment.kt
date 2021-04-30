package com.hackfest21.covigenix.UI.ProviderServices

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
import com.hackfest21.covigenix.Adapter.ProviderRequestsListAdapter
import com.hackfest21.covigenix.HelperClass
import com.hackfest21.covigenix.Model.Request.PatientRequestModel
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ViewModel.RequestViewModel
import kotlinx.android.synthetic.main.fragment_provider_requests_list.*

class ProviderRequestListFragment : Fragment(), ProviderRequestsListAdapter.ListListener {

    lateinit var requestViewModel: RequestViewModel
    lateinit var list: List<PatientRequestModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provider_requests_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestViewModel = ViewModelProvider(requireActivity()).get(RequestViewModel::class.java)

        requestViewModel.responseGetProviderRequests().observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let{
                list = it.requests

                recyclerViewPatients.adapter = ProviderRequestsListAdapter(list, this, HelperClass.createMap())
                recyclerViewPatients.apply{
                    layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                }
            }
        })

        requestViewModel.responseGetApproval().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(this@ProviderRequestListFragment.context, it.message, Toast.LENGTH_LONG).show()
            }
        })

        requestViewModel.errorString().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(this@ProviderRequestListFragment.context, it, Toast.LENGTH_LONG).show()
            }
        })

        Log.d("TAG", "onViewCreated: Ouch")
        requestViewModel.getProviderRequests()
    }

    override fun onClick(itemPos: Int) {
        Toast.makeText(context, "Requested address from " + list[itemPos].name, Toast.LENGTH_SHORT).show()

        requestViewModel.getApproval(list[itemPos].request_id)
    }
}