package org.android.go.sopt.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.android.go.sopt.BuildConfig
import org.android.go.sopt.data.remote.services.KakaoSearchService
import org.android.go.sopt.data.remote.services.ReqresService
import org.android.go.sopt.data.remote.services.SignInService
import org.android.go.sopt.data.remote.services.SignUpService
import retrofit2.Retrofit

object ApiFactory {
    private val client by lazy {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    val retrofitForAuth: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BuildConfig.AUTH_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client).build()
    }

    inline fun <reified T> createAuthService(): T = retrofitForAuth.create<T>(T::class.java)

    val retrofitForReqres: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BuildConfig.REQRES_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client).build()
    }

    inline fun <reified T> createReqresService(): T = retrofitForReqres.create<T>(T::class.java)

    val retrofitForKakao: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BuildConfig.KAKAO_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> createKakaoService(): T = retrofitForKakao.create<T>(T::class.java)
}

object ServicePool {
    val signUpService = ApiFactory.createAuthService<SignUpService>()
    val signInService = ApiFactory.createAuthService<SignInService>()
    val reqresService = ApiFactory.createReqresService<ReqresService>()
    val kakaoSearchService = ApiFactory.createKakaoService<KakaoSearchService>()
}