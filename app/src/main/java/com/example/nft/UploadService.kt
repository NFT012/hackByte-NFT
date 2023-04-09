package com.example.nft

import com.example.nft.ui.ImageUrl
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadService {

    @Multipart
    @POST("api/mint/file/")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ImageUrl>
}