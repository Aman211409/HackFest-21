package com.hackfest21.covigenix.Repository

import com.hackfest21.covigenix.Model.Provider.BodyProviderSignUp
import com.hackfest21.covigenix.Model.Provider.BodyUpdateEssentials
import com.hackfest21.covigenix.Model.Provider.BodyUpdateProvider
import com.hackfest21.covigenix.Service.ProviderService
import retrofit2.Retrofit

class ProviderRepository(retrofit: Retrofit) {

    //ProviderService class for using retrofit
    var providerService: ProviderService = retrofit.create(ProviderService::class.java)

    suspend fun providerExists(providerId: String) = providerService.providerExists(providerId)

    suspend fun providerSignUp(bodyProviderSignUp: BodyProviderSignUp) = providerService.providerSignUp(bodyProviderSignUp)

    suspend fun getProvider(providerId: String) = providerService.getProvider(providerId)

    suspend fun updateProvider(providerId: String, bodyUpdateProvider: BodyUpdateProvider) = providerService.updateProvider(providerId, bodyUpdateProvider)

    suspend fun updateEssentials(providerId: String, bodyUpdateEssentials: BodyUpdateEssentials) = providerService.updateEssentials(providerId, bodyUpdateEssentials)
}