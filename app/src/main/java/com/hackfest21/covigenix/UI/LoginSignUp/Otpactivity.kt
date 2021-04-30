package com.hackfest21.covigenix.UI.LoginSignUp


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.hackfest21.covigenix.R
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit
import com.google.android.gms.tasks.TaskExecutors.MAIN_THREAD as MAIN_THREAD1


class Otpactivity : AppCompatActivity() {
    private var verificationId: String? = null
    private var mAuth: FirebaseAuth? = null
    var progressBar: ProgressBar? = null
    lateinit var editText: TextInputEditText
    lateinit var buttonSignIn: AppCompatButton
    val type = intent.getStringExtra("Type")
    var number: Int? = null
    val phoneNumber = intent.getStringExtra("phoneNumber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otpactivity)
        mAuth = FirebaseAuth.getInstance()
        editText = findViewById(R.id.otp_edit_text1)
        buttonSignIn = findViewById(R.id.btn_verify)

        sendVerificationCode(phoneNumber)

        buttonSignIn.setOnClickListener {
            fun onClick(v: View?) {
                val code = editText.getText().toString().trim { it <= ' ' }
                if (code.isEmpty() || code.length < 6) {
                    editText.setError("Enter code...")
                    editText.requestFocus()
                    return
                }
                verifyCode(code)
            }
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
//                    val Type = type?.toString()
//                    if(type!=null){
//                        //Todo() Intent To screen
//                    if(type==0)
//                    val intent = Intent(this@Otpactivity, ::class.java)
//                    startActivity(intent)
//                    else{
//                        val intent = Intent(this@Otpactivity, ProfileActivity::class.java)
//                            startActivity(intent)
//                    }
//                    }
                } else {
                    Toast.makeText(
                        this@Otpactivity,
                        task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                    progressBar!!.visibility = View.GONE
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
                    verificationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                }

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    // Sign in with the credential
                    // ...
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // ...
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
}