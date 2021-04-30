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
import com.hackfest21.covigenix.Repository.PatientRepository
import com.hackfest21.covigenix.Repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class PatientViewModel(val app: Application): AndroidViewModel(app) {

    val patientRepository: PatientRepository= (app as MyApplication).patientRepository
    val userRepository: UserRepository = (app as MyApplication).userRepository

    private val responsePatientExists: MutableLiveData<Event<ResponsePatientExists>> = MutableLiveData()
    private val responsePatientSignUp: MutableLiveData<Event<ResponsePatientSignUp>> = MutableLiveData()
    private val responseGetPatient: MutableLiveData<Event<ResponseGetPatient>> = MutableLiveData()
    private val responseUpdatePatient: MutableLiveData<Event<ResponseUpdatePatient>> = MutableLiveData()
    private val errorString: MutableLiveData<Event<String>> = MutableLiveData()

    fun responsePatientExists(): LiveData<Event<ResponsePatientExists>> = responsePatientExists
    fun responsePatientSignUp(): LiveData<Event<ResponsePatientSignUp>> = responsePatientSignUp
    fun responseGetPatient(): LiveData<Event<ResponseGetPatient>> = responseGetPatient
    fun responseUpdatePatient(): LiveData<Event<ResponseUpdatePatient>> = responseUpdatePatient

    fun errorString(): LiveData<Event<String>> = errorString

    fun checkPatientExists(){
        val phone = userRepository.getUserPhone()
        if(phone.equals("")){
            errorString.postValue(Event(PHONE_NOT_PROVIDED))
            return
        }

        viewModelScope.launch {
            try{
                responsePatientExists.postValue(Event(patientRepository.patientExists(phone)))
            }catch(e: Exception){
                handleError(e, errorString)
            }
        }
    }

    fun patientSignUp(name: String, area: String){
        val coordinates: Array<Double> = arrayOf(0.0, 0.0)
        val phone = userRepository.getUserPhone()
        //TODO: GetLocation
        val bodyPatientSignUp = BodyPatientSignUp(name, phone, area, coordinates)
        viewModelScope.launch {
            try{
                responsePatientSignUp.postValue(Event(patientRepository.patientSignUp(bodyPatientSignUp)))
            }catch(e: Exception){
                handleError(e, errorString)
            }
        }
    }

    fun getPatient(){
        val patientId = userRepository.getUserId()
        if(patientId.equals("")){
            errorString.postValue(Event(ID_NOT_GIVEN))
            return
        }

        viewModelScope.launch {
            try{
                responseGetPatient.postValue(Event(patientRepository.getPatient(patientId)))
            }catch(e: Exception){
                handleError(e, errorString)
            }
        }
    }

    fun updatePatient(area: String){
        val patientId = userRepository.getUserId()
        if(patientId.equals("")){
            errorString.postValue(Event(ID_NOT_GIVEN))
            return
        }

        val coordinates: Array<Double> = arrayOf(0.0, 0.0);
        //TODO: GetLocation
        val bodyUpdatePatient = BodyUpdatePatient(area, coordinates)

        viewModelScope.launch {
            try{
                responseUpdatePatient.postValue(Event(patientRepository.updatePatient(patientId, bodyUpdatePatient)))
            }catch(e: Exception){
                handleError(e, errorString)
            }
        }
    }

}