package com.hackfest21.covigenix.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hackfest21.covigenix.HelperClass.Companion.TYPE_PROVIDER
import com.hackfest21.covigenix.MyApplication
import com.hackfest21.covigenix.R
import com.hackfest21.covigenix.UI.LoginSignUp.LoginUser

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val loginType = (application as MyApplication).userRepository.getLoginType()

        if(loginType == -1){
            startActivity(Intent(this, LoginUser::class.java))
            finish()
        }else if(loginType == TYPE_PROVIDER){
            startActivity(Intent(this, ProviderActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this, PatientActivity::class.java))
            finish()
        }
    }
}