package com.hackfest21.covigenix.UI.ProviderServices

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Adapter.EssentialsAdapter
import com.hackfest21.covigenix.HelperClass
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ViewModel.ProviderViewModel
import kotlinx.android.synthetic.main.fragment_edit_essentials.view.*

class EditEssentialsFragment : Fragment(), EssentialsAdapter.EssentialListener {

    private val TAG = "EditEssentialsFragment"

    var essentialsList: ArrayList<EssentialsAdapter.Essential> = ArrayList()

    private lateinit var providerViewModel: ProviderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_essentials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        providerViewModel = ViewModelProvider(requireActivity()).get(ProviderViewModel::class.java)

        providerViewModel.responseUpdateEssentials().observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { response ->
                Toast.makeText(this@EditEssentialsFragment.context, response.message, LENGTH_LONG).show()
            }
        })

        providerViewModel.errorString().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(this@EditEssentialsFragment.context, it, LENGTH_LONG).show()
            }
        })

        view.recyclerViewEssentials.apply{
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }

        essentialsList = HelperClass.createEssentialCheckList()
        view.recyclerViewEssentials.adapter = EssentialsAdapter(essentialsList, this)

        view.submit.setOnClickListener { updateEssentials() }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Edit Essentials"
    }

    override fun toggle(itemPos: Int) {
        essentialsList.get(itemPos).checked = !essentialsList.get(itemPos).checked
    }

    private fun updateEssentials(){
        var essentialIdList = ArrayList<Int>()
        for(e in essentialsList){
            if(e.checked){
                Log.d(TAG, "updateEssentials: "+e.name)
                essentialIdList.add(e.id)
            }

        }

        Log.d(TAG, "updateEssentials: "+essentialIdList.size)
        providerViewModel.updateEssentials(essentialIdList.toIntArray())
    }
}