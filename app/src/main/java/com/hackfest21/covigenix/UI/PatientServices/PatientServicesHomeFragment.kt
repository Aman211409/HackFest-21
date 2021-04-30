
package com.hackfest21.covigenix.UI.PatientServices

import android.os.Bundle
import android.util.Patterns.DOMAIN_NAME
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.UI.PatientServices.PatientServicesHomeAdapter.Companion.PLASMA
import com.hackfest21.covigenix.UI.PatientServices.PatientServicesHomeAdapter.Companion.REMDEVISIR

//import java.util.*


class PatientServicesHomeFragment : Fragment(), PatientServicesHomeAdapter.HomeListener {

    private lateinit var list: ArrayList<PatientServicesHomeAdapter.HomeItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_servieshome, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        list = ArrayList()
        list.add(PatientServicesHomeAdapter.HomeItem("Remdevisir", R.mipmap.app_dev, REMDEVISIR))

        list.add(PatientServicesHomeAdapter.HomeItem("Plasma", R.mipmap.web, PLASMA))
        recyclerView.adapter = context?.let { PatientServicesHomeAdapter(list, it, this) }
        recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        (requireParentFragment().requireActivity() as AppCompatActivity).supportActionBar?.title = "CoviGenix"
    }

    override fun onDomainClick(position: Int) {
        val domainArg = list[position].domainArg
        val bundle = Bundle()
        bundle.putString(DOMAIN_NAME.toString(), domainArg)
        parentFragmentManager
                .beginTransaction()
                // Todo() tRANACT ACCORD TO NAV GRAPH
    }
}