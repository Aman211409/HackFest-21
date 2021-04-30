package com.hackfest21.covigenix.Model

data class CommunityPostModel
    (
         var name: String,
         var phone: String,
         var itemName : String ,
          var details : String ,
         var location: Array<Double>,
         var area: String
     )