package com.hackfest21.covigenix.UI.PatientServices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Adapter.MyRequestsListAdapter
import com.hackfest21.covigenix.Model.Request.ProviderStatusModel
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ViewModel.RequestViewModel
import kotlinx.android.synthetic.main.fragment_my_requests_list.view.*

class MyRequestsListFragment : Fragment(), MyRequestsListAdapter.ListListener, GetAddressDialogFragment.GetAddressListener {

    lateinit var requestViewModel: RequestViewModel
    lateinit var list: List<ProviderStatusModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_requests_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestViewModel = ViewModelProvider(requireActivity()).get(RequestViewModel::class.java)

        requestViewModel.hospitalList.observe(viewLifecycleOwner, {
            list = it.providers

            view.recyclerViewHospitals.adapter = MyRequestsListAdapter(list, this)
            view.recyclerViewHospitals.apply{
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                setHasFixedSize(true)
            }
        })

        requestViewModel.responseShareAddress().observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        })

        requestViewModel.errorString().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onShare(pos: Int) {
        val dialog = GetAddressDialogFragment(this, pos)
        dialog.show(childFragmentManager, "DH")
    }

    override fun onSubmit(address: String, pos: Int) {
        list[pos].approved = true
        requestViewModel.shareAddress(requestViewModel.hospitalList.value!!.id, list[pos].provider_id, address, list)
    }
}