package com.example.nft

import com.example.nft.ui.ImageUrl
import com.example.nft.ui.MintModel
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
}