package com.hackfest21.covigenix.Model.Request

data class ResponseGetMyRequests(var code: Int, var message: String, var requests: List<MyRequestsModel>)
