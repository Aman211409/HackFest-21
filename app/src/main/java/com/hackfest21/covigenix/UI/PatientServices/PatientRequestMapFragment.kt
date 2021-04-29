package com.hackfest21.covigenix.UI.PatientServices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.hackfest21.covigenix.R
import kotlinx.android.synthetic.main.fragment_provider_requests_home.view.*

class PatientRequestMapFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_request_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.text.setOnClickListener { view.findNavController().navigate(R.id.map_to_apply)}
    }
}