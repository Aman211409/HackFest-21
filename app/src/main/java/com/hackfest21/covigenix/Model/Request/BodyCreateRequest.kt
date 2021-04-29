package com.hackfest21.covigenix.Model.Request

data class BodyCreateRequest(var patient_id: String, var patient_name: String, var patient_phone: String, var area: String, var coordinates: Array<Double>, var providers: List<ProviderStatusModel>)
