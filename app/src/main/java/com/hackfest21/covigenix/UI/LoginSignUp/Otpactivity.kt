package com.hackfest21.covigenix.UI.LoginSignUp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.hackfest21.covigenix.R
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.ktx.Firebase
import com.hackfest21.covigenix.HelperClass.Companion.LOGIN_TYPE
import com.hackfest21.covigenix.HelperClass.Companion.TYPE_PATIENT
import com.hackfest21.covigenix.HelperClass.Companion.TYPE_PROVIDER
import com.hackfest21.covigenix.HelperClass.Companion.USER_PHONE
import com.hackfest21.covigenix.MyApplication
import com.hackfest21.covigenix.UI.PatientActivity
import com.hackfest21.covigenix.UI.ProviderActivity
import com.hackfest21.covigenix.UI.RegisterPatient
import com.hackfest21.covigenix.UI.RegisterProvider
import com.hackfest21.covigenix.ViewModel.PatientViewModel
import com.hackfest21.covigenix.ViewModel.ProviderViewModel
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates
import com.google.android.gms.tasks.TaskExecutors.MAIN_THREAD as MAIN_THREAD1


class Otpactivity : AppCompatActivity() {
    private val TAG = "Otpactivity"
    
    private var verificationId: String? = null
    private var mAuth: FirebaseAuth? = null
    var progressBar: ProgressBar? = null
    lateinit var editText: EditText
    lateinit var buttonSignIn: Button

    var type by Delegates.notNull<Int>()
    lateinit var phoneNumber: String
    lateinit var number: String

    lateinit var patientViewModel: PatientViewModel
    lateinit var providerViewModel: ProviderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otpactivity)
        mAuth = FirebaseAuth.getInstance()
        editText = findViewById(R.id.otp_edit_text1)
        buttonSignIn = findViewById(R.id.btn_verify)

        type = intent.getIntExtra(LOGIN_TYPE, TYPE_PROVIDER)
        number = intent.getStringExtra(USER_PHONE)!!

        if(type == TYPE_PROVIDER){
            providerViewModel = ViewModelProvider(this).get(ProviderViewModel::class.java)

            providerViewModel.responseProviderExists().observe(this, {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                    if(it.code==200){
                        //Store details
                        (application as MyApplication).userRepository.setUserId(it.id!!)
                        (application as MyApplication).userRepository.setUserName(it.name!!)
                        (application as MyApplication).userRepository.setUserArea(it.area!!)
                        (application as MyApplication).userRepository.setUserLong(it.location!![0])
                        (application as MyApplication).userRepository.setUserLat(it.location!![1])
                        (application as MyApplication).userRepository.setLoginType(TYPE_PROVIDER)
                        //Visit Provider Activity
                        startActivity(Intent(this, ProviderActivity::class.java))
                        finish()
                    }else{

                        //Get to RegisterProvider
                        startActivity(Intent(this, RegisterProvider::class.java))
                        finish()
                    }
                }
            })

            providerViewModel.errorString().observe(this, {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            patientViewModel = ViewModelProvider(this).get(PatientViewModel::class.java)

            patientViewModel.responsePatientExists().observe(this, {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                    if(it.code==200){
                        //Store details
                        (application as MyApplication).userRepository.setUserId(it.id!!)
                        (application as MyApplication).userRepository.setUserName(it.name!!)
                        (application as MyApplication).userRepository.setUserArea(it.area!!)
                        (application as MyApplication).userRepository.setUserLong(it.location!![0])
                        (application as MyApplication).userRepository.setUserLat(it.location!![1])
                        (application as MyApplication).userRepository.setLoginType(TYPE_PATIENT)
                        //Visit Patient Activity
                        startActivity(Intent(this, PatientActivity::class.java))
                        finish()
                    }else{

                        //Get to RegisterPatient
                        startActivity(Intent(this, RegisterPatient::class.java))
                        finish()
                    }
                }
            })

            patientViewModel.errorString().observe(this, {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            })
        }



        phoneNumber = "+91$number"
        sendVerificationCode(phoneNumber)

        buttonSignIn.setOnClickListener {

                val code = editText.getText().toString().trim { it <= ' ' }
                if (code.isEmpty() || code.length < 6) {
                    editText.setError("Enter code...")
                    editText.requestFocus()
                    return@setOnClickListener
                }
                verifyCode(code)

        }
    }


    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                        //Todo() Intent To screen
                    Toast.makeText(this, "Wow", Toast.LENGTH_SHORT).show()

                    (application as MyApplication).userRepository.setUserPhone(number)

                    if(type == TYPE_PROVIDER){
                        providerViewModel.checkProviderExists()
                    }else{
                        patientViewModel.checkPatientExists()
                    }
                } else {
                    Toast.makeText(
                        this@Otpactivity,
                        task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                    //progressBar!!.visibility = View.GONE
                }
            }
    }

    private fun sendVerificationCode(number: String?) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(number)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onCodeSent(
                    verId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    Log.d(TAG, "onCodeSent: ")
                    verificationId = verId
                    //resendToken = token
                    Toast.makeText(this@Otpactivity, "Code Sent", Toast.LENGTH_SHORT).show()
                }

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    // Sign in with the credential
                    // ...
                    Log.d(TAG, "onVerificationCompleted: ")
                    Toast.makeText(this@Otpactivity, "Wow, verified", Toast.LENGTH_SHORT).show()
                    signInWithCredential(phoneAuthCredential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // ...
                    Log.d(TAG, "onVerificationFailed: ")
                    Toast.makeText(this@Otpactivity, "bad boy", Toast.LENGTH_SHORT).show()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
}