package com.hackfest21.covigenix.UI.PatientServices

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Adapter.PatientRequestApplyAdapter
import com.hackfest21.covigenix.Model.Request.ProviderResponseModel
import com.hackfest21.covigenix.Model.Request.ProviderStatusModel
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ViewModel.RequestViewModel
import kotlinx.android.synthetic.main.fragment_edit_essentials.view.*
import kotlinx.android.synthetic.main.fragment_patient_request_apply.*
import kotlinx.android.synthetic.main.fragment_patient_request_apply.view.*
import java.util.*
import kotlin.collections.ArrayList


class PatientRequestApplyFragment : Fragment(), PatientRequestApplyAdapter.ApplyListener {

    lateinit var requestViewModel: RequestViewModel
    lateinit var providers: ArrayList<ProviderResponseModel>
    lateinit var adapter: PatientRequestApplyAdapter


    var isBeingSelected: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_request_apply, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestViewModel = ViewModelProvider(requireActivity()).get(RequestViewModel::class.java)

        requestViewModel.safeToVisitProviders.postValue(false)

        requestViewModel.responseGetProvidersByEssentialsId().observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                providers = it.providers as ArrayList<ProviderResponseModel>

                //TODO: Sorting Algo

                adapter = PatientRequestApplyAdapter(providers, this)
                recyclerViewProviders.adapter = adapter
                recyclerViewProviders.apply {
                    layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                }
            }
        })

        requestViewModel.responseCreateRequest().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let {
                Toast.makeText(
                    this@PatientRequestApplyFragment.context,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        requestViewModel.errorString().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let {
                Toast.makeText(this@PatientRequestApplyFragment.context, it, Toast.LENGTH_LONG)
                    .show()
            }
        })

        view.toggle.setOnClickListener { toggleList() }
        view.apply.setOnClickListener { startApply() }
    }

    override fun onCheck(itemPos: Int) {
        providers[itemPos].checkStatus = 1-providers[itemPos].checkStatus
    }

    override fun onLongPress(itemPos: Int) {
        val long  = providers[itemPos].location[0]
        val lat   = providers[itemPos].location[1]
        val uri: String = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", lat, long)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        requireContext().startActivity(intent)
    }

    private fun toggleList(){
        isBeingSelected = !isBeingSelected

        toggle.text = if(isBeingSelected) "Cancel Selection" else "Start Selection"
        val num = if(isBeingSelected) 0 else -1

        for(i in providers.indices){
            val oldVal = providers[i]
            oldVal.checkStatus = num
            providers[i] = oldVal
        }

        adapter = PatientRequestApplyAdapter(providers, this)
        recyclerViewProviders.adapter = adapter
        recyclerViewProviders.apply{
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun startApply(){
        val requested: ArrayList<ProviderStatusModel> = ArrayList()

        for(provider in providers){
            if(provider.checkStatus == 1){
                requested.add(
                    ProviderStatusModel(
                        provider.name,
                        provider.phone,
                        provider._id,
                        false,
                        false
                    )
                )
            }
        }

        if(requested.isEmpty()){
            Toast.makeText(context, "Please select at least 1 provider.", Toast.LENGTH_SHORT).show()
            return
        }

        requestViewModel.createRequest(requested)
    }
}