package com.hackfest21.covigenix.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hackfest21.covigenix.Event
import com.hackfest21.covigenix.HelperClass
import com.hackfest21.covigenix.Model.*
import com.hackfest21.covigenix.MyApplication
import com.hackfest21.covigenix.Repository.CommunityRepository
import com.hackfest21.covigenix.Repository.PatientRepository
import com.hackfest21.covigenix.Repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.function.DoubleUnaryOperator

class CommunityPostViewModel(val app: Application): AndroidViewModel(app){

    val communityPostRepository: CommunityRepository = (app as MyApplication).communityRepository
    val userRepository: UserRepository = (app as MyApplication).userRepository

    private val responseGetCommunityPost0: MutableLiveData<Event<ResponseGetCommunityPost>> = MutableLiveData()
    private val responseGetCommunityPost1: MutableLiveData<Event<ResponseGetCommunityPost>> = MutableLiveData()

    private val responseCreateCommunityPost: MutableLiveData<Event<ResponseCreateCommunityPost>> = MutableLiveData()
    private val responseDeleteCommunitypost: MutableLiveData<Event<ResponseDeleteCommunityPost>> = MutableLiveData()

    private val errorString: MutableLiveData<Event<String>> = MutableLiveData()

    fun responseGetCommunityPost0(): LiveData<Event<ResponseGetCommunityPost>> = responseGetCommunityPost0
    fun responseGetCommunityPost1(): LiveData<Event<ResponseGetCommunityPost>> = responseGetCommunityPost1

    fun responseCreateCommunityPost(): LiveData<Event<ResponseCreateCommunityPost>> = responseCreateCommunityPost
    fun responseDeleteCommunityPost(): LiveData<Event<ResponseDeleteCommunityPost>> = responseDeleteCommunitypost


    fun errorString(): LiveData<Event<String>> = errorString

    fun getCommunityPost(communityPostType : Int ){
        val coordinates : Array<Double> = arrayOf(userRepository.getUserLong(), userRepository.getUserLat())

        val bodyCommunityPost = BodyGetCommunityPost(coordinates[0], coordinates[1])

        viewModelScope.launch {
            try{
                if(communityPostType==0)
                    responseGetCommunityPost0.postValue(Event(communityPostRepository.communityPostsNearby(communityPostType,bodyCommunityPost)))
                else
                    responseGetCommunityPost1.postValue(Event(communityPostRepository.communityPostsNearby(communityPostType,bodyCommunityPost)))
            }catch(e: Exception){
                HelperClass.handleError(e, errorString)
            }
        }
    }

    fun createCommunityPost(name:String , phone:String , area : String , itemName : String , details:String, communityPostType : Int ){
        val lat = userRepository.getUserLat()
        val long = userRepository.getUserLong()
        val coordinates : Array<Double> = arrayOf(0.0,0.0)
        coordinates[0] = lat
        coordinates[1] = long
        val bodyCreateCommunityPost = BodyCreateCommunityPost(name, phone, itemName ,area ,  coordinates,details)
        viewModelScope.launch {
            try{
                responseCreateCommunityPost.postValue(Event(communityPostRepository.createCommunityPost(communityPostType,bodyCreateCommunityPost)))
            }catch(e: Exception){
                HelperClass.handleError(e, errorString)
            }
        }
    }
    // Todo (Hew to delete Communtiy post Check)
    fun deleteCommunityPost(){
        val patientId = userRepository.getUserId()
        if(patientId.equals("")){
            errorString.postValue(Event(HelperClass.ID_NOT_GIVEN))
            return
        }

        viewModelScope.launch {
            try{
                responseDeleteCommunitypost.postValue(Event(communityPostRepository.deleteCommunityPost(patientId)))
            }catch(e: Exception){
                HelperClass.handleError(e, errorString)
            }
        }
    }

}
