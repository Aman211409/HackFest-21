package com.hackfest21.covigenix.Model.Request

import java.util.*

data class PatientRequestModel(var location: Array<Double>, var area: String, var name: String, var phone: String, var address: String, var essentials_id: Int, var date: Date)
