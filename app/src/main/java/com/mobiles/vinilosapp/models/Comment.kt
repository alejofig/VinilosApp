package com.mobiles.vinilosapp.models

data class Comment (
    val id: Int?,
    val description: String,
    val rating: Int,
    val collector: CollectorComment
)

data class CollectorComment (
    val id: Int
)
