package com.hackfest21.covigenix.UI.LoginSignUp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.hackfest21.covigenix.HelperClass.Companion.LOGIN_TYPE
import com.hackfest21.covigenix.HelperClass.Companion.TYPE_PATIENT
import com.hackfest21.covigenix.HelperClass.Companion.TYPE_PROVIDER
import com.hackfest21.covigenix.HelperClass.Companion.USER_PHONE
import com.hackfest21.covigenix.R


class LoginUser : AppCompatActivity() {

    private val TAG = "LoginUser"
    lateinit  var editTextPhone: EditText
    lateinit var buttonContinue1: Button
    lateinit var buttonContinue2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        editTextPhone = findViewById(R.id.input_phoneNo)
        buttonContinue1 = findViewById(R.id.btn_login1)
        buttonContinue2 = findViewById((R.id.btn_login2))
        buttonContinue1.setOnClickListener {


                Log.d(TAG, "onClick: click bc1")

                val number = editTextPhone.getText().toString().trim { it <= ' ' }
                if (number.isEmpty() || number.length < 10) {
                    editTextPhone.setError("Valid number is required")
                    return@setOnClickListener
                }
                val phoneNumber = number
                val intent = Intent(this@LoginUser, Otpactivity::class.java)
                intent.putExtra(USER_PHONE, phoneNumber)
                intent.putExtra(LOGIN_TYPE, TYPE_PROVIDER);
                startActivity(intent)

        }
        buttonContinue2.setOnClickListener {

                Log.d(TAG, "onClick: click bc2")

                val number = editTextPhone.getText().toString().trim { it <= ' ' }
                if (number.isEmpty() || number.length < 10) {
                    editTextPhone.setError("Valid number is required")
                    return@setOnClickListener
                }
                val phoneNumber = number
                val intent = Intent(this@LoginUser, Otpactivity::class.java)
                intent.putExtra(USER_PHONE, phoneNumber)
                intent.putExtra(LOGIN_TYPE, TYPE_PATIENT);
                startActivity(intent)

        }

    }
}

