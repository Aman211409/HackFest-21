package com.hackfest21.covigenix.Model.Request

import java.util.*

data class MyRequestsModel(var area: String, var providers: List<ProviderStatusModel>, var essentials_id: Int, var date: Date, var completed: Boolean, var id: String)
