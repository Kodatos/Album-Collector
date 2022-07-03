package com.kodatos.albumcollector.album.models

data class AlbumSearchModel(
    val title: String,
    val artist: String,
    val imageURL: String? = null,
    val id: Long
)
