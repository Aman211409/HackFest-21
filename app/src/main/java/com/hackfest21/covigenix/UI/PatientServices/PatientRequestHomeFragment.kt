package com.hackfest21.covigenix.UI.PatientServices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Adapter.ProviderRequestsHomeAdapter
import com.hackfest21.covigenix.HelperClass
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ViewModel.PatientViewModel
import com.hackfest21.covigenix.ViewModel.RequestViewModel
import kotlinx.android.synthetic.main.fragment_patient_request_home.view.*
import kotlinx.android.synthetic.main.fragment_patient_request_home.view.recyclerView

class PatientRequestHomeFragment : Fragment(), ProviderRequestsHomeAdapter.HomeListener {

    var list: ArrayList<ProviderRequestsHomeAdapter.Item> = ArrayList()
    lateinit var requestViewModel: RequestViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_request_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestViewModel = ViewModelProvider(requireActivity()).get(RequestViewModel::class.java)

        requestViewModel.safeToVisitProviders.observe(viewLifecycleOwner, { safeToVisit->
            if(safeToVisit){
                view.findNavController().navigate(R.id.home_to_apply)
            }
        })

        requestViewModel.errorString().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        })

        list = HelperClass.createEssentialsList()
        val adapter = ProviderRequestsHomeAdapter(list, this)
        view.recyclerView.adapter = adapter
        view.recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
    }

    override fun onClick(itemPos: Int) {
        Toast.makeText(context, list[itemPos].name, Toast.LENGTH_SHORT).show()

        requestViewModel.emitEssentialId(list[itemPos].id)
        requestViewModel.getProvidersByEssentialsId(list[itemPos].id)
    }
}