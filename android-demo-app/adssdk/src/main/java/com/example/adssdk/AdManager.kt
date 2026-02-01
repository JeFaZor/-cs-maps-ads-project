package com.example.adssdk

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdManager(private val baseUrl: String) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val adService: AdService = retrofit.create(AdService::class.java)

    // Fetch a random ad
    fun fetchRandomAd(onSuccess: (Ad) -> Unit, onError: (String) -> Unit) {
        adService.getRandomAd().enqueue(object : Callback<AdResponse> {
            override fun onResponse(call: Call<AdResponse>, response: Response<AdResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!.data)
                } else {
                    onError("Failed to fetch ad: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AdResponse>, t: Throwable) {
                onError("Network error: ${t.message}")
            }
        })
    }

    // Track impression
    fun trackImpression(adId: String) {
        val request = AnalyticsRequest(ad_id = adId)
        adService.trackImpression(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                // Impression tracked successfully
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Failed to track impression
            }
        })
    }

    // Track click
    fun trackClick(adId: String) {
        val request = AnalyticsRequest(ad_id = adId)
        adService.trackClick(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                // Click tracked successfully
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Failed to track click
            }
        })
    }
}