package com.example.nft

import com.example.nft.ui.ImageUrl
import com.example.nft.ui.MintModel
import com.example.nft.ui.dashboard.NftDetails
import com.example.nft.ui.dashboard.getNFT
import com.example.nft.ui.nft
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


interface UploadService {

    @Multipart
    @POST("upload-image/")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ImageUrl>

    @POST("/api/mint/file/nft")
    suspend fun createNFT(@Body nft: nft): Response<QuickMint>

    @Headers(
        "X-API-Key: sk_live_76ec3775-7189-435d-9481-76cdf013e261",
        "accept: application/json"
    )
    @GET("created")
    suspend fun getMyNFT(
        @Query("walletAddress") wid:String,
        @Query("chain")chain:String
    ):Response<getNFT>

    @GET("nftDetails")
    suspend fun getNftDetails(
        @Query("contractAddress") wid:String,
        @Query("tokenId") token:String,
        @Query("chain") chain:String
    ):Response<NftDetails>


}