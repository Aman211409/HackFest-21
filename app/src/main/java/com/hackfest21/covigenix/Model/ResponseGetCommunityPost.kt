package com.hackfest21.covigenix.Model

data class ResponseGetCommunityPost (var code: Int, var message: String, val posts: List<CommunityPostModel>)