package com.kodatos.albumcollector.collection.domain

import com.kodatos.albumcollector.album.models.AlbumModel
import com.kodatos.albumcollector.collection.models.CollectionModel
import kotlinx.coroutines.flow.Flow

interface CollectionsDomain {

    fun getAllCollections(): Flow<List<CollectionModel>>

    fun getCollectionforId(id: Long): CollectionModel?

    fun getAlbumsForCollection(id: Long): Flow<List<AlbumModel>>

    suspend fun addCollection(collectionModel: CollectionModel)

    suspend fun deleteCollection(collectionModel: CollectionModel)

    suspend fun addAlbumToCollection(collectionModel: CollectionModel, albumID: Long)

    suspend fun deleteAlbumFromCollection(collectionModel: CollectionModel, albumID: Long)

    suspend fun updateCollection(id: Long, newCollectionModel: CollectionModel)
}