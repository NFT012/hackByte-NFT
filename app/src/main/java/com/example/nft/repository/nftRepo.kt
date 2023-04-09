package com.example.nft.repository

import com.example.nft.di.nftAPI
import javax.inject.Inject

class nftRepo @Inject constructor(private val nftAPI: nftAPI) {

    suspend fun getAllNFT() = nftAPI.getAllNFT()

}