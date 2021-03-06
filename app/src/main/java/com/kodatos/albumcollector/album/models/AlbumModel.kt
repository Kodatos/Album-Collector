package com.kodatos.albumcollector.album.models

data class AlbumModel(
    val title: String,
    val artist: String,
    val imageURL: String? = null,
    val deepLink: String? = null,
    val id: Long = -1
)
