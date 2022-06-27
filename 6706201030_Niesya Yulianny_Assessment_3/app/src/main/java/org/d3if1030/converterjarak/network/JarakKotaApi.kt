package org.d3if1030.converterjarak.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if1030.converterjarak.model.JarakKota
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/Niesyayulianny/AssessmentMobpro1/master/api/"
private const val BASE_URL_IMG = "https://raw.githubusercontent.com/Niesyayulianny/AssessmentMobpro1/master/api/img/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface JarakKotaService {
    @GET("jarak-kota.json")
    suspend fun getResult(): List<JarakKota>
}

object JarakKotaApi {
    val service: JarakKotaService by lazy {
        retrofit.create(JarakKotaService::class.java)
    }
    fun getJarakKotaUrl(gambar: String): String {
        return "$BASE_URL_IMG$gambar"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }