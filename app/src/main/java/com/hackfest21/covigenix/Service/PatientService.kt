package com.hackfest21.covigenix.Service

import com.hackfest21.covigenix.Model.*
import retrofit2.http.*

interface PatientService {

    //Patient Exists
    @GET("patient/{patientId}/exists")
    suspend fun patientExists(@Path("phonrNo") phoneNo: String): ResponsePatientExists

    //Patient Sign-Up
    @POST("patient/sign-up")
    suspend fun patientSignUp(@Body bodyPatientSignUp: BodyPatientSignUp): ResponsePatientSignUp

    // Get  My Profile
    @GET("patient/{patientId}")
    suspend fun getPatient(@Path("patientId") patientId: String) : ResponseGetPatient

    //Update My Profile
    @PATCH("patient/{patientId}")
    suspend fun updatePatient(@Path("patientId") patientId: String, @Body updatePatient: BodyUpdatePatient) : ResponseUpdatePatient

}