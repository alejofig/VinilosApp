package com.mobiles.vinilosapp.models

data class Artist(
    val artistId: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
    val albums: List<Album>,
)
