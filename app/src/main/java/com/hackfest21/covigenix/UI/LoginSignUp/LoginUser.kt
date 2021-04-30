package com.hackfest21.covigenix.UI.LoginSignUp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.hackfest21.covigenix.R


class LoginUser : AppCompatActivity() {

    lateinit  var editTextPhone: TextInputEditText
    lateinit var buttonContinue1: AppCompatButton
    lateinit var buttonContinue2: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        editTextPhone = findViewById(R.id.input_phoneNo)
        buttonContinue1 = findViewById(R.id.btn_login1)
        buttonContinue2 = findViewById((R.id.btn_login2))
        buttonContinue1.setOnClickListener {
            fun onClick(v: View?) {
                val number = editTextPhone.getText().toString().trim { it <= ' ' }
                if (number.isEmpty() || number.length < 10) {
                    editTextPhone.setError("Valid number is required")
                    return
                }
                val phoneNumber = "+91$number"
                val intent = Intent(this@LoginUser, Otpactivity::class.java)
                intent.putExtra("phoneNumber", phoneNumber)
                intent.putExtra("Type", 0);
                startActivity(intent)
            }
        }
        buttonContinue2.setOnClickListener {
            fun onClick(v: View?) {
                val number = editTextPhone.getText().toString().trim { it <= ' ' }
                if (number.isEmpty() || number.length < 10) {
                    editTextPhone.setError("Valid number is required")
                    return
                }
                val phoneNumber = "+91$number"
                val intent = Intent(this@LoginUser, Otpactivity::class.java)
                intent.putExtra("phoneNumber", phoneNumber)
                intent.putExtra("Type", 1);
                startActivity(intent)
            }
        }

    }
}

