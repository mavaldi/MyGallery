package com.example.mygallery.model

import java.util.Date

data class GalleryImage(
    val id: String,
    val title: String,
    val imageUrl: String,
    val date: String = Date().toString(),
    val isLocal: Boolean = false
)