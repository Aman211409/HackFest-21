package com.hackfest21.covigenix.Service

import com.hackfest21.covigenix.Model.Request.*
import retrofit2.http.*

interface RequestService {

    @POST("request/{essentials_id}/nearby")
    suspend fun getProvidersByEssentialsId(@Path("essentials_id") essentials_id: String, @Body bodyGetProvidersByEssentialsId: BodyGetProvidersByEssentialsId): ResponseGetProvidersByEssentialsId

    @POST("request/{essentials_id}")
    suspend fun createRequest(@Path("essentials_id") essentials_id: String, @Body bodyCreateRequest: BodyCreateRequest): ResponseCreateRequest

    @GET("request/provider/{providerId}")
    suspend fun getProviderRequests(@Path("providerId") providerId: String): ResponseGetProviderRequests

    @GET("request/approval/{requestId}/{providerId}")
    suspend fun getApproval(@Path("requestId") requestId: String, @Path("providerId") providerId: String): ResponseGetApproval

    @GET("request/share-address/{requestId}/{providerId}")
    suspend fun shareAddress(@Path("requestId") requestId: String, @Path("providerId") providerId: String, @Body bodyShareAddress: BodyShareAddress): ResponseShareAddress

    @PATCH("request/{requestId}")
    suspend fun markCompleted(@Path("requestId") requestId: String): ResponseMarkCompleted

    @GET("request/patient/{patientId}")
    suspend fun getMyRequests(@Path("patientId") patientId: String): ResponseGetMyRequests
}