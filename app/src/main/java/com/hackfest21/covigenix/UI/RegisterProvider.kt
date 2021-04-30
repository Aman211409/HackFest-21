package com.hackfest21.covigenix.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackfest21.covigenix.Adapter.EssentialsAdapter
import com.hackfest21.covigenix.HelperClass
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ViewModel.ProviderViewModel
import kotlinx.android.synthetic.main.activity_register_provider.*

class RegisterProvider : AppCompatActivity(), EssentialsAdapter.EssentialListener {

    private val TAG = "RegisterProvider"

    lateinit var providerViewModel: ProviderViewModel
    lateinit var sName: String
    lateinit var sArea: String
    var essentialsList: ArrayList<EssentialsAdapter.Essential> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_provider)

        providerViewModel = ViewModelProvider(this).get(ProviderViewModel::class.java)

        providerViewModel.responseProviderSignUp().observe(this, {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                if (it.code == 200) {
                    //TODO: Save the current profile
                    Log.d(TAG, "onCreate: Success")
                    providerViewModel.userRepository.setUserId(it.id)
                    providerViewModel.userRepository.setUserName(sName)
                    providerViewModel.userRepository.setUserArea(sArea)

                    startActivity(Intent(this, ProviderActivity::class.java))
                }
            }
        })

        providerViewModel.errorString().observe(this, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        recyclerView.apply{
            layoutManager = LinearLayoutManager(this@RegisterProvider, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }

        essentialsList = HelperClass.createEssentialCheckList()
        recyclerView.adapter = EssentialsAdapter(essentialsList, this)
        register.setOnClickListener { registerProvider() }
    }

    private fun registerProvider(){
        sName = name.text.toString().trim()
        sArea =  area.text.toString()

        if(sName.isEmpty()){
            name.setError("Enter valid name.")
            return
        }else{
            name.error = null
        }

        if(sArea.isEmpty()){
            area.setError("Enter valid area.")
            return
        }else{
            area.error = null
        }


        var essentialIdList = ArrayList<Int>()
        for(e in essentialsList){
            if(e.checked){
                Log.d(TAG, "updateEssentials: "+e.name)
                essentialIdList.add(e.id)
            }

        }

        Log.d(TAG, "updateEssentials: "+essentialIdList.size)
        if(essentialIdList.size == 0){
            Toast.makeText(this, "Please select at least one essential commodity in stock with you.", Toast.LENGTH_SHORT).show()
            return
        }

        //TODO: API CALL
        //Log.d(TAG, "registerProvider: hi")
        providerViewModel.providerSignUp(sName, sArea, essentialIdList.toIntArray())
    }

    override fun toggle(itemPos: Int) {
        essentialsList.get(itemPos).checked = !essentialsList.get(itemPos).checked
    }
}