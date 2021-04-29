package com.hackfest21.covigenix.Model.Request

data class ResponseGetProvidersByEssentialsId(var code: Int, var message: String, var providers: Array<ProviderResponseModel>)
