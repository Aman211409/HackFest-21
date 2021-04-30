package com.hackfest21.covigenix.Service

import com.hackfest21.covigenix.Model.*
import retrofit2.http.*

interface CommunityService {

    // Get all  Community Posts Nearby
    @POST("communityPost/{communityPostType}/nearby")
    suspend fun getCommunityPost(@Path("communityPostType") communityPostType: Int , @Body getcommunityPost : BodyGetCommunityPost): ResponseGetCommunityPost


    // Create a new Community Post
    @POST("communityPost/{communityPostType}")
    suspend fun createCommunityPost(@Path("communityPostType") communityPostType: Int , @Body createcommunityPost : BodyCreateCommunityPost): ResponseCreateCommunityPost


    // Delete Community Post
    @DELETE("communityPost/{communityPostId}")
    suspend fun deleteCommunityPost(@Path("communityPostId") communityPostId : String) : ResponseDeleteCommunityPost









}