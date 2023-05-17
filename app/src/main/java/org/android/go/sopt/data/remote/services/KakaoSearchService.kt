package org.android.go.sopt.data.remote.services

import org.android.go.sopt.BuildConfig
import org.android.go.sopt.data.remote.model.ResponseKakaoSearchDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoSearchService {
    @GET("search/web")
    fun search(
        @Header("Authorization") token: String = BuildConfig.KAKAO_REST_API_KEY,
        @Query("query") keyword: String,
    )
            : Call<ResponseKakaoSearchDto>
}