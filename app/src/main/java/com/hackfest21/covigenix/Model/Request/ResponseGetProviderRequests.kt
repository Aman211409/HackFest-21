package com.hackfest21.covigenix.Model.Request

data class ResponseGetProviderRequests(var code: Int, var message: String, var requests: List<PatientRequestModel>)
