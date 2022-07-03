package com.kodatos.albumcollector.collection.models

data class CollectionModel(
    val name: String,
    val imageURL: String? = null,
    val id: Long = -1
)
