package org.android.go.sopt.data.remote.services

import org.android.go.sopt.data.remote.model.RequestSignInDto
import org.android.go.sopt.data.remote.model.ResponseSignInDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("sign-in")
    fun signIn(@Body request: RequestSignInDto)
            : Call<ResponseSignInDto>
}