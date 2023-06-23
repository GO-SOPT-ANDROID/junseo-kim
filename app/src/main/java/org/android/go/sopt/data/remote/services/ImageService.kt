package org.android.go.sopt.data.remote.services

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageService {
    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
    ): Call<Unit>
}