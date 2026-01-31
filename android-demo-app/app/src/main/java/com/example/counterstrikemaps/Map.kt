package com.example.counterstrikemaps

data class Map(
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    val imageRes: Int  // Resource ID for the map image
)