package com.hackfest21.covigenix.UI.ProviderServices

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hackfest21.covigenix.HelperClass.Companion.TYPE_LOGGED_OUT
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.UI.Splash
import com.hackfest21.covigenix.ViewModel.ProviderViewModel
import kotlinx.android.synthetic.main.fragment_provider_profile.view.*

class ProviderProfileFragment : Fragment() {

    private val TAG = "ProviderProfileFragment"

    lateinit var providerViewModel: ProviderViewModel
    lateinit var newArea: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provider_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        providerViewModel = ViewModelProvider(requireActivity()).get(ProviderViewModel::class.java)

        providerViewModel.responseUpdateProvider().observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { response ->
                Toast.makeText(this@ProviderProfileFragment.context, response.message, Toast.LENGTH_LONG).show()

                if(response.code == 200){
                    providerViewModel.userRepository.setUserArea(newArea)
                }
            }
        })

        providerViewModel.errorString().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(this@ProviderProfileFragment.context, it, Toast.LENGTH_LONG).show()
            }
        })

        providerViewModel.providerName.observe(viewLifecycleOwner, {
            view.name.text = it
        })
        providerViewModel.providerPhone.observe(viewLifecycleOwner, {
            view.phone.text = it
        })
        providerViewModel.providerArea.observe(viewLifecycleOwner, {
            view.area.setText(it)
        })

        providerViewModel.emitProfile()

        view.update.setOnClickListener { updateProfile() }
        view.logout.setOnClickListener { logout() }
    }

    private fun updateProfile(){
        newArea = requireView().area.text.toString()

        if(newArea == ""){
            requireView().area.setError("Enter an area")
            return
        }

        Log.d(TAG, "updateProfile: "+newArea)
        providerViewModel.updateProvider(newArea)
    }

    private fun logout(){
        providerViewModel.userRepository.setLoginType(TYPE_LOGGED_OUT)

        startActivity(Intent(requireActivity(), Splash::class.java))
        requireActivity().finish()
    }
}