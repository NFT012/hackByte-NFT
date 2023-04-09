package com.example.nft.di

import com.example.nft.model.LoginRequest
import com.example.nft.model.LoginResponse
import com.example.nft.model.NftModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface nftAPI {

    @GET("/api/mint/transaction")
    suspend fun getAllNFT() : Response<NftModel>

    @POST("/api/auth/user/register")
    suspend fun UserLogin(@Body loginRequest: LoginRequest) : Response<LoginResponse>
}