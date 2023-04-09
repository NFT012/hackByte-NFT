package com.example.nft.di

import com.example.nft.model.NftModel
import retrofit2.Response
import retrofit2.http.GET

interface nftAPI {

    @GET("/api/mint/transaction")
    suspend fun getAllNFT() : Response<NftModel>
}