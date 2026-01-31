package com.example.adssdk

data class Ad(
    val ad_id: Int,
    val title: String,
    val description: String,
    val image_url: String,
    val link_url: String,
    val location: String? = null,
    val active: Boolean = true,
    val impressions: Int = 0,
    val clicks: Int = 0
)