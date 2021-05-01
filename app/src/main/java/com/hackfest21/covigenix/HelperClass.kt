package com.hackfest21.covigenix

import androidx.lifecycle.MutableLiveData
import com.hackfest21.covigenix.Adapter.EssentialsAdapter
import com.hackfest21.covigenix.Adapter.ProviderRequestsHomeAdapter
import java.lang.Exception

class HelperClass {

    companion object {
        val SHARED_PREFS = "SharedPreferences"
        val LOGIN_STATUS = "LoginStatus"
        val LOGIN_TYPE = "LoginType"
        val USER_ID = "UserId"
        val USER_NAME = "UserName"
        val BASE_URL = "http://192.168.1.6:3000/"
        val USER_PHONE = "UserPhone"
        val USER_AREA = "UserArea"
        val PHONE_NOT_PROVIDED = "Phone number is not provided."
        val ID_NOT_GIVEN = "User ID not provided."
        val TYPE_PATIENT = 0;
        val TYPE_PROVIDER = 1;
        val TYPE_LOGGED_OUT = -1
        val USER_LAT = "UserLat"
        val USER_LONG = "UserLong"
        val DUMMY_LONGI = 83.2
        val DUMMY_LAT = 21.4

        fun handleError(e: Exception, errorString: MutableLiveData<Event<String>>){
            e.printStackTrace()

            //Change the Mutable LiveData so that change can be detected in Fragment/Activity. One extra Observer per ViewModel per Activity
            errorString.postValue(Event(e.localizedMessage))
        }

        fun createEssentialCheckList(): ArrayList<EssentialsAdapter.Essential>{
            val list = ArrayList<EssentialsAdapter.Essential>()
            list.add(EssentialsAdapter.Essential("Remdesivir", 0, false))
            list.add(EssentialsAdapter.Essential("Oxygen", 1, false))
            return list
        }

        fun createEssentialsList(): ArrayList<ProviderRequestsHomeAdapter.Item>{
            val list = ArrayList<ProviderRequestsHomeAdapter.Item>()
            list.add(ProviderRequestsHomeAdapter.Item("Remdesivir", R.mipmap.app_dev, 0))
            list.add(ProviderRequestsHomeAdapter.Item("Oxygen", R.mipmap.web, 1))
            return list
        }

        fun createMap() : HashMap<Int, String>{
            val map = HashMap<Int, String>()
            map.put(0, "Remdesivir")
            map.put(1, "Oxygen")
            return map
        }
    }
}