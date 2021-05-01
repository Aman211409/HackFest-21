package com.hackfest21.covigenix.UI.PatientServices

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hackfest21.covigenix.HelperClass
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.UI.Splash
import com.hackfest21.covigenix.ViewModel.PatientViewModel
import com.hackfest21.covigenix.ViewModel.ProviderViewModel
import kotlinx.android.synthetic.main.fragment_patient_profile.view.*

class PatientProfileFragment : Fragment() {

    private val TAG = "PatientProfileFragment"

    lateinit var patientViewModel: PatientViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientViewModel = ViewModelProvider(requireActivity()).get(PatientViewModel::class.java)

        patientViewModel.responseUpdatePatient().observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { response ->
                Toast.makeText(this@PatientProfileFragment.context, response.message, Toast.LENGTH_LONG).show()
            }
        })

        patientViewModel.errorString().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(this@PatientProfileFragment.context, it, Toast.LENGTH_LONG).show()
            }
        })

        patientViewModel.patientName.observe(viewLifecycleOwner, {
            view.name.text = it
        })
        patientViewModel.patientPhone.observe(viewLifecycleOwner, {
            view.phone.text = it
        })
        patientViewModel.patientArea.observe(viewLifecycleOwner, {
            view.area.setText(it)
        })

        patientViewModel.emitProfile()

        view.update.setOnClickListener { updateProfile() }
        view.logout.setOnClickListener { logout() }
    }

    private fun updateProfile(){
        val newArea = requireView().area.text.toString()

        if(newArea == ""){
            requireView().area.setError("Enter an area")
            return
        }

        Log.d(TAG, "updateProfile: "+newArea)
        patientViewModel.updatePatient(newArea)
    }

    private fun logout(){
        patientViewModel.userRepository.setLoginType(HelperClass.TYPE_LOGGED_OUT)

        startActivity(Intent(requireActivity(), Splash::class.java))
        requireActivity().finish()
    }
}