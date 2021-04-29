package com.hackfest21.covigenix.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hackfest21.covigenix.Event
import com.hackfest21.covigenix.HelperClass.Companion.ID_NOT_GIVEN
import com.hackfest21.covigenix.HelperClass.Companion.PHONE_NOT_PROVIDED
import com.hackfest21.covigenix.HelperClass.Companion.handleError
import com.hackfest21.covigenix.Model.*
import com.hackfest21.covigenix.MyApplication
import com.hackfest21.covigenix.Repository.ProviderRepository
import com.hackfest21.covigenix.Repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ProviderViewModel(val app: Application): AndroidViewModel(app) {

    val providerRepository: ProviderRepository = (app as MyApplication).providerRepository
    val userRepository: UserRepository = (app as MyApplication).userRepository

    private val responseProviderExists: MutableLiveData<Event<ResponseProviderExists>> = MutableLiveData()
    private val responseProviderSignUp: MutableLiveData<Event<ResponseProviderSignUp>> = MutableLiveData()
    private val responseGetProvider: MutableLiveData<Event<ResponseGetProvider>> = MutableLiveData()
    private val responseUpdateProvider: MutableLiveData<Event<ResponseUpdateProvider>> = MutableLiveData()
    private val responseUpdateEssentials: MutableLiveData<Event<ResponseUpdateEssentials>> = MutableLiveData()
    private val errorString: MutableLiveData<Event<String>> = MutableLiveData()

    fun responseProviderExists(): LiveData<Event<ResponseProviderExists>> = responseProviderExists
    fun responseProviderSignUp(): LiveData<Event<ResponseProviderSignUp>> = responseProviderSignUp
    fun responseGetProvider(): LiveData<Event<ResponseGetProvider>> = responseGetProvider
    fun responseUpdateProvider(): LiveData<Event<ResponseUpdateProvider>> = responseUpdateProvider
    fun responseUpdateEssentials(): LiveData<Event<ResponseUpdateEssentials>> = responseUpdateEssentials
    fun errorString(): LiveData<Event<String>> = errorString

    fun checkProviderExists(){
        val phone = userRepository.getUserPhone()
        if(phone.equals("")){
            errorString.postValue(Event(PHONE_NOT_PROVIDED))
            return
        }

        viewModelScope.launch {
            try{
                responseProviderExists.postValue(Event(providerRepository.providerExists(phone)))
            }catch(e: Exception){
                handleError(e, errorString)
            }
        }
    }

    fun providerSignUp(name: String, area: String, essentials: Array<Int>){
        val coordinates: Array<Double> = arrayOf(0.0, 0.0)
        val phone = userRepository.getUserPhone()
        //TODO: GetLocation
        val bodyProviderSignUp = BodyProviderSignUp(name, phone, area, coordinates, essentials)
        viewModelScope.launch {
            try{
                responseProviderSignUp.postValue(Event(providerRepository.providerSignUp(bodyProviderSignUp)))
            }catch(e: Exception){
                handleError(e, errorString)
            }
        }
    }

    fun getProvider(){
        val providerId = userRepository.getUserId()
        if(providerId.equals("")){
            errorString.postValue(Event(ID_NOT_GIVEN))
            return
        }

        viewModelScope.launch {
            try{
                responseGetProvider.postValue(Event(providerRepository.getProvider(providerId)))
            }catch(e: Exception){
                handleError(e, errorString)
            }
        }
    }

    fun updateProvider(area: String){
        val providerId = userRepository.getUserId()
        if(providerId.equals("")){
            errorString.postValue(Event(ID_NOT_GIVEN))
            return
        }

        val coordinates: Array<Double> = arrayOf(0.0, 0.0);
        //TODO: GetLocation
        val bodyUpdateProvider = BodyUpdateProvider(area, coordinates)

        viewModelScope.launch {
            try{
                responseUpdateProvider.postValue(Event(providerRepository.updateProvider(providerId, bodyUpdateProvider)))
            }catch(e: Exception){
                handleError(e, errorString)
            }
        }
    }

    fun updateEssentials(essentials: Array<Int>){
        val bodyUpdateEssentials = BodyUpdateEssentials(essentials)
        val providerId = userRepository.getUserId()
        if(providerId.equals("")){
            errorString.postValue(Event(ID_NOT_GIVEN))
            return
        }
        viewModelScope.launch {
            try{
                responseUpdateEssentials.postValue(Event(providerRepository.updateEssentials(providerId, bodyUpdateEssentials)))
            }catch(e: Exception){
                handleError(e, errorString)
            }
        }
    }
}