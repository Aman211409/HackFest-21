package com.hackfest21.covigenix.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.ViewModel.PatientViewModel
import kotlinx.android.synthetic.main.activity_register_patient.*

class RegisterPatient : AppCompatActivity() {
    private val TAG = "RegisterPatient"

    lateinit var patientViewModel: PatientViewModel
    lateinit var sName: String
    lateinit var sArea:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_patient)

        patientViewModel = ViewModelProvider(this).get(PatientViewModel::class.java)

        patientViewModel.responsePatientSignUp().observe(this, {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                if (it.code == 200) {
                    //TODO: Save the current profile
                    Log.d(TAG, "onCreate: Success")
                    patientViewModel.userRepository.setUserId(it.id)
                    patientViewModel.userRepository.setUserName(sName)
                    patientViewModel.userRepository.setUserArea(sArea)

                    startActivity(Intent(this, PatientActivity::class.java))
                }
            }
        })

        patientViewModel.errorString().observe(this, {
            it?.getContentIfNotHandled()?.let{
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        btn_login1.setOnClickListener { registerPatient() }
    }

    private fun registerPatient(){
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

        //TODO: API CALL
        patientViewModel.patientSignUp(sName, sArea)
    }
}