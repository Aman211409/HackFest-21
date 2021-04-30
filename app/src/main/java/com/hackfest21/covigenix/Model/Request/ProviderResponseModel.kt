package com.hackfest21.covigenix.Model.Request

data class ProviderResponseModel(var _id: String, var name: String, var phone: String, var location: Array<Double>, var area: String, var checkStatus: Int = -1)
