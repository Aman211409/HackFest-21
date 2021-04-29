package com.hackfest21.covigenix.Service

import com.hackfest21.covigenix.Model.*
import retrofit2.http.*

interface ProviderService {

    //Provider Exists
    @GET("provider/{providerId}/exists")
    suspend fun providerExists(@Path("providerId") providerId: String): ResponseProviderExists

    //Provider Sign-Up
    @POST("provider/sign-up")
    suspend fun providerSignUp(@Body bodyProviderSignUp: BodyProviderSignUp): ResponseProviderSignUp

    @GET("provider/{providerId}")
    suspend fun getProvider(@Path("providerId") providerId: String) : ResponseGetProvider

    @PATCH("provider/{providerId}")
    suspend fun updateProvider(@Path("providerId") providerId: String, @Body bodyUpdateProvider: BodyUpdateProvider) : ResponseUpdateProvider

    @PUT("provider/{providerId}")
    suspend fun updateEssentials(@Path("providerId") providerId: String, @Body bodyUpdateEssentials: BodyUpdateEssentials): ResponseUpdateEssentials
}