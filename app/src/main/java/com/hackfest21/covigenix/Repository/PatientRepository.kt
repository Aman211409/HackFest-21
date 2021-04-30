package com.hackfest21.covigenix.Repository

import com.hackfest21.covigenix.Model.*
import com.hackfest21.covigenix.Service.PatientService
import com.hackfest21.covigenix.Service.ProviderService
import retrofit2.Retrofit
import retrofit2.http.*

class PatientRepository(retrofit: Retrofit) {

    //ProviderService class for using retrofit
    var patientService: PatientService = retrofit.create(PatientService::class.java)

    suspend fun patientExists(phoneNo: String) = patientService.patientExists(phoneNo)

    suspend fun patientSignUp(bodyPatientSignUp: BodyPatientSignUp) = patientService.patientSignUp(bodyPatientSignUp)

    suspend fun getPatient(patientId: String) = patientService.getPatient(patientId)

    suspend fun updatePatient(patientId: String, bodyUpdatePatient: BodyUpdatePatient) = patientService.updatePatient(patientId, bodyUpdatePatient)
}