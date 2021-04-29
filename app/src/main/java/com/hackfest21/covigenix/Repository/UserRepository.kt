package com.hackfest21.covigenix.Repository

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.hackfest21.covigenix.HelperClass.Companion.LOGIN_STATUS
import com.hackfest21.covigenix.HelperClass.Companion.LOGIN_TYPE
import com.hackfest21.covigenix.HelperClass.Companion.SHARED_PREFS
import com.hackfest21.covigenix.HelperClass.Companion.USER_AREA
import com.hackfest21.covigenix.HelperClass.Companion.USER_ID
import com.hackfest21.covigenix.HelperClass.Companion.USER_LAT
import com.hackfest21.covigenix.HelperClass.Companion.USER_LONG
import com.hackfest21.covigenix.HelperClass.Companion.USER_NAME
import com.hackfest21.covigenix.HelperClass.Companion.USER_PHONE
import com.hackfest21.covigenix.MyApplication

class UserRepository(val app: MyApplication) {

    var sharedPref: SharedPreferences
    var editor: SharedPreferences.Editor

    init{
        sharedPref = app.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun getLoginStatus(): Boolean{
        try{
            val loginStatus = sharedPref.getBoolean(LOGIN_STATUS, false)
            return loginStatus
        } catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }

    fun setLoginStatus(loginStatus: Boolean) {
        try{
            editor.putBoolean(LOGIN_STATUS, loginStatus)
            editor.commit()
        } catch(e : Exception){
            e.printStackTrace()
        }
    }

    fun getLoginType(): Int{
        try{
            val loginType = sharedPref.getInt(LOGIN_TYPE, -1)
            return loginType
        } catch (e: Exception){
            e.printStackTrace()
            return -1
        }
    }

    fun setLoginStatus(loginType: Int) {
        try{
            editor.putInt(LOGIN_TYPE, loginType)
            editor.commit()
        } catch(e : Exception){
            e.printStackTrace()
        }
    }

    fun getUserId(): String{
        try{
            return sharedPref.getString(USER_ID, null)!!
        } catch(e: Exception){
            e.printStackTrace()
            return ""
            //TODO:Show "Error in token" Toast
        }
    }

    fun setUserId(id: String){
        try{
            if(id.equals(""))
                throw NullPointerException()
            editor.putString(USER_ID, id)
            editor.commit()
        } catch(e: Exception){
            e.printStackTrace()
            //TODO:Show "Error in token" Toast
        }
    }

    fun getUserPhone() : String {
        try{
            return sharedPref.getString(USER_PHONE, "")!!
        } catch (e: Exception){
            e.printStackTrace()
            return ""
        }
    }

    fun setUserPhone(phone : String){
        try{
            if(phone.equals(""))
                throw NullPointerException()
            editor.putString(USER_PHONE, phone)
            editor.commit()
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun getUserLat() : Double {
        try{
            val lat = sharedPref.getString(USER_LAT, "0.0")!!
            return lat.toDouble()
        } catch (e: Exception){
            e.printStackTrace()
            return 0.0
        }
    }

    fun setUserLat(lat : Double){
        try{
            editor.putString(USER_LAT, lat.toString())
            editor.commit()
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun getUserLong() : Double {
        try{
            val longit = sharedPref.getString(USER_LONG, "0.0")!!
            return longit.toDouble()
        } catch (e: Exception){
            e.printStackTrace()
            return 0.0
        }
    }

    fun setUserLong(longit : Double){
        try{
            editor.putString(USER_LONG, longit.toString())
            editor.commit()
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun getUserName() : String {
        try{
            return sharedPref.getString(USER_NAME, "")!!
        } catch (e: Exception){
            e.printStackTrace()
            return ""
        }
    }

    fun setUserName(name : String){
        try{
            if(name.equals(""))
                throw NullPointerException()
            editor.putString(USER_NAME, name)
            editor.commit()
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun getUserArea() : String {
        try{
            return sharedPref.getString(USER_AREA, "")!!
        } catch (e: Exception){
            e.printStackTrace()
            return ""
        }
    }

    fun setUserArea(area : String){
        try{
            if(area.equals(""))
                throw NullPointerException()
            editor.putString(USER_AREA, area)
            editor.commit()
        } catch(e: Exception){
            e.printStackTrace()
        }
    }
}