package com.hackfest21.covigenix.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hackfest21.covigenix.Event
import com.hackfest21.covigenix.HelperClass
import com.hackfest21.covigenix.Model.Request.*
import com.hackfest21.covigenix.MyApplication
import com.hackfest21.covigenix.Repository.RequestRepository
import com.hackfest21.covigenix.Repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class RequestViewModel(val app: Application): AndroidViewModel(app) {

    val requestRepository: RequestRepository = (app as MyApplication).requestRepository
    val userRepository: UserRepository = (app as MyApplication).userRepository

    private val responseGetProvidersByEssentialsId: MutableLiveData<Event<ResponseGetProvidersByEssentialsId>> = MutableLiveData()
    private val responseCreateRequest: MutableLiveData<Event<ResponseCreateRequest>> = MutableLiveData()
    private val responseGetProviderRequests: MutableLiveData<Event<ResponseGetProviderRequests>> = MutableLiveData()
    private val responseGetApproval: MutableLiveData<Event<ResponseGetApproval>> = MutableLiveData()
    private val responseShareAddress: MutableLiveData<Event<ResponseShareAddress>> = MutableLiveData()
    private val responseMarkCompleted: MutableLiveData<Event<ResponseMarkCompleted>> = MutableLiveData()
    private val responseGetMyRequests: MutableLiveData<Event<ResponseGetMyRequests>> = MutableLiveData()
    private val errorString: MutableLiveData<Event<String>> = MutableLiveData()

    fun responseGetProvidersByEssentialsId(): LiveData<Event<ResponseGetProvidersByEssentialsId>> = responseGetProvidersByEssentialsId
    fun responseCreateRequest(): LiveData<Event<ResponseCreateRequest>> = responseCreateRequest
    fun responseGetProviderRequests(): LiveData<Event<ResponseGetProviderRequests>> = responseGetProviderRequests
    fun responseGetApproval(): LiveData<Event<ResponseGetApproval>> = responseGetApproval
    fun responseShareAddress(): LiveData<Event<ResponseShareAddress>> = responseShareAddress
    fun responseMarkCompleted(): LiveData<Event<ResponseMarkCompleted>> = responseMarkCompleted
    fun responseGetMyRequests(): LiveData<Event<ResponseGetMyRequests>> = responseGetMyRequests
    fun errorString(): LiveData<Event<String>> = errorString


    val safeToVisitProviders: MutableLiveData<Boolean> = MutableLiveData()
    fun getProvidersByEssentialsId(essentialsId: Int){
        val bodyGetProvidersByEssentialsId = BodyGetProvidersByEssentialsId(userRepository.getUserLong(), userRepository.getUserLat())

        viewModelScope.launch {
            try{
                responseGetProvidersByEssentialsId.postValue(Event(requestRepository.getProvidersByEssentialsId(essentialsId.toString(), bodyGetProvidersByEssentialsId)))
                safeToVisitProviders.postValue(true)
            }catch(e: Exception){
                HelperClass.handleError(e, errorString)
            }
        }
    }

    fun createRequest(providers: ArrayList<ProviderStatusModel>){
        val essentialsId = essentialIdLiveData.value
        val bodyCreateRequest = BodyCreateRequest(userRepository.getUserId(), userRepository.getUserName(), userRepository.getUserPhone(), userRepository.getUserArea(), arrayOf(userRepository.getUserLong(), userRepository.getUserLat()), providers)

        viewModelScope.launch {
            try{
                responseCreateRequest.postValue(Event(requestRepository.createRequest(essentialsId.toString(), bodyCreateRequest)))
            }catch(e: Exception){
                HelperClass.handleError(e, errorString)
            }
        }
    }

    fun getProviderRequests(){
        val providerId = userRepository.getUserId()

        viewModelScope.launch {
            try{
                responseGetProviderRequests.postValue(Event(requestRepository.getProviderRequests(providerId)))
            }catch(e: Exception){
                HelperClass.handleError(e, errorString)
            }
        }
    }

    fun getApproval(requestId: String){
        val providerId = userRepository.getUserId()

        viewModelScope.launch {
            try{
                responseGetApproval.postValue(Event(requestRepository.getApproval(requestId, providerId)))
            }catch(e: Exception){
                HelperClass.handleError(e, errorString)
            }
        }
    }

    fun shareAddress(requestId: String, providerId: String, address: String, providers: List<ProviderStatusModel>){
        val bodyShareAddress = BodyShareAddress(address, providers)

        viewModelScope.launch {
            try{
                responseShareAddress.postValue(Event(requestRepository.shareAddress(requestId, providerId, bodyShareAddress)))
            }catch(e: Exception){
                HelperClass.handleError(e, errorString)
            }
        }
    }

    fun getMyRequests(){
        val patientId = userRepository.getUserId()

        viewModelScope.launch {
            try{
                responseGetMyRequests.postValue(Event(requestRepository.getMyRequests(patientId)))
            }catch(e: Exception){
                HelperClass.handleError(e, errorString)
            }
        }
    }

    val essentialIdLiveData: MutableLiveData<Int> = MutableLiveData()
    fun emitEssentialId(id: Int){
        essentialIdLiveData.postValue(id)
    }

    val hospitalList: MutableLiveData<MyRequestsModel> = MutableLiveData()
}