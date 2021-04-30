package com.hackfest21.covigenix.Repository

import com.hackfest21.covigenix.Model.BodyCreateCommunityPost
import com.hackfest21.covigenix.Model.BodyGetCommunityPost
import com.hackfest21.covigenix.Model.BodyPatientSignUp
import com.hackfest21.covigenix.Model.BodyUpdatePatient
import com.hackfest21.covigenix.Service.CommunityService
import com.hackfest21.covigenix.Service.PatientService
import retrofit2.Retrofit

class CommunityRepository(retrofit: Retrofit) {


    var communityService: CommunityService = retrofit.create(CommunityService::class.java)
    // Get Community Posts Nearby

    suspend fun communityPostsNearby(communityPostType : Int , bodyGetCommunityPost: BodyGetCommunityPost) = communityService.getCommunityPost(communityPostType,bodyGetCommunityPost)

    // Create a new Community Post Nearby

    suspend fun createCommunityPost(communityPostType : Int , bodyCreateCommunityPost: BodyCreateCommunityPost) = communityService.createCommunityPost(communityPostType,bodyCreateCommunityPost)

    // Delete Community Post

    suspend fun deleteCommunityPost(communityPostId: String) = communityService.deleteCommunityPost(communityPostId)

    }