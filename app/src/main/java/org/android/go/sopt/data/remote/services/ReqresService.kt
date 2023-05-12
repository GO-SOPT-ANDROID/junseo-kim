package org.android.go.sopt.data.remote.services

import org.android.go.sopt.data.remote.model.ResponseReqresDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("users")
    fun getUsers(@Query("page") page: Int)
            : Call<ResponseReqresDto>
}