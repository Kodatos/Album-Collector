package com.kodatos.albumcollector.collection

import com.kodatos.albumcollector.album.AlbumModel
import com.kodatos.albumcollector.collection.CollectionModel
import comkodatosalbumcollector.Albums
import comkodatosalbumcollector.Collections
import comkodatosalbumcollector.GetAlbumsForCollection
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {

    suspend fun getAllCollections(): Flow<List<Collections>>

    suspend fun getAlbumsForCollection(id: Long): Flow<List<GetAlbumsForCollection>>

    suspend fun addCollection(collectionModel: CollectionModel)

    suspend fun deleteCollection(collectionModel: CollectionModel)

    suspend fun addAlbumToCollection(collectionModel: CollectionModel, albumModel: AlbumModel)

    suspend fun deleteAlbumToCollection(collectionModel: CollectionModel, albumModel: AlbumModel)

}