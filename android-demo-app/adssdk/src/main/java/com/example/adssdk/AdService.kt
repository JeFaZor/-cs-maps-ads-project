package com.example.adssdk

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class AdResponse(
    val status: String,
    val data: Ad
)

data class AdsResponse(
    val status: String,
    val count: Int,
    val data: List<Ad>
)

data class AnalyticsRequest(
    val ad_id: Int,
    val location: String? = null
)

interface AdService {

    @GET("api/ads")
    fun getAllAds(): Call<AdsResponse>

    @GET("api/ads/random")
    fun getRandomAd(): Call<AdResponse>

    @POST("api/analytics/impression")
    fun trackImpression(@Body request: AnalyticsRequest): Call<Void>

    @POST("api/analytics/click")
    fun trackClick(@Body request: AnalyticsRequest): Call<Void>
}