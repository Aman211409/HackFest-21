package com.hackfest21.covigenix

import android.app.Application
import com.hackfest21.covigenix.HelperClass.Companion.BASE_URL
import com.hackfest21.covigenix.Repository.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MyApplication: Application() {

    var moshi = Moshi.Builder().build()
    var retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    lateinit var providerRepository: ProviderRepository
    lateinit var patientRepository: PatientRepository
    lateinit var requestRepository: RequestRepository
    lateinit var communityRepository: CommunityRepository
    lateinit var userRepository: UserRepository


    override fun onCreate() {
        super.onCreate()

        moshi = Moshi.Builder()
            .add(CustomDateAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

        providerRepository = ProviderRepository(retrofit)
        patientRepository = PatientRepository(retrofit)
        communityRepository = CommunityRepository(retrofit)
        requestRepository = RequestRepository(retrofit)
        userRepository = UserRepository(this)
    }
}