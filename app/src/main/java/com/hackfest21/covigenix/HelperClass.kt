package com.hackfest21.covigenix

import androidx.lifecycle.MutableLiveData
import java.lang.Exception

class HelperClass {

    companion object {
        val SHARED_PREFS = "SharedPreferences"
        val LOGIN_STATUS = "LoginStatus"
        val LOGIN_TYPE = "LoginType"
        val USER_ID = "UserId"
        val USER_NAME = "UserName"
        val BASE_URL = "http://192.168.1.4:3000/"
        val USER_PHONE = "UserPhone"
        val PHONE_NOT_PROVIDED = "Phone number is not provided."
        val ID_NOT_GIVEN = "User ID not provided."
        fun handleError(e: Exception, errorString: MutableLiveData<Event<String>>){
            e.printStackTrace()

            //Change the Mutable LiveData so that change can be detected in Fragment/Activity. One extra Observer per ViewModel per Activity
            errorString.postValue(Event(e.localizedMessage))
        }
    }
}