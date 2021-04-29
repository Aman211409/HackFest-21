package com.hackfest21.covigenix.Repository

import com.hackfest21.covigenix.Model.Request.*
import com.hackfest21.covigenix.Service.RequestService
import retrofit2.Retrofit
import retrofit2.http.*

class RequestRepository(retrofit: Retrofit) {

    var requestService = retrofit.create(RequestService::class.java)


    suspend fun getProvidersByEssentialsId(essentials_id: String, bodyGetProvidersByEssentialsId: BodyGetProvidersByEssentialsId) = requestService.getProvidersByEssentialsId(essentials_id, bodyGetProvidersByEssentialsId)

    suspend fun createRequest(essentials_id: String, bodyCreateRequest: BodyCreateRequest) = requestService.createRequest(essentials_id, bodyCreateRequest)

    suspend fun getProviderRequests(providerId: String) = requestService.getProviderRequests(providerId)

    suspend fun getApproval(requestId: String, providerId: String) = requestService.getApproval(requestId, providerId)

    suspend fun shareAddress(requestId: String, providerId: String, bodyShareAddress: BodyShareAddress) = requestService.shareAddress(requestId, providerId, bodyShareAddress)

    suspend fun markCompleted(requestId: String) = requestService.markCompleted(requestId)

    suspend fun getMyRequests(patientId: String) = requestService.getMyRequests(patientId)
}