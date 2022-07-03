package com.kodatos.albumcollector.collection.domain

import com.kodatos.albumcollector.Database
import com.kodatos.albumcollector.album.models.AlbumModel
import com.kodatos.albumcollector.collection.models.CollectionModel
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import comkodatosalbumcollector.Collections_Albums
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionsDomainImpl @Inject constructor(
    private val database: Database
) : CollectionsDomain {

    override suspend fun getAllCollections(): Flow<List<CollectionModel>> {
        return database.collectionQueries.selectAll(::mapToCollectionModel).asFlow().mapToList()
    }

    override suspend fun getAlbumsForCollection(id: Long): Flow<List<AlbumModel>> {
        return database.collections_AlbumsQueries.getAlbumsForCollection(id, ::mapToAlbumModel).asFlow().mapToList()
    }

    override suspend fun addCollection(collectionModel: CollectionModel) {
        database.collectionQueries.insertCollection(collectionModel.name, collectionModel.imageURL)
    }

    override suspend fun deleteCollection(collectionModel: CollectionModel) {
        database.transaction {
            database.collections_AlbumsQueries.deleteCollection(collectionModel.id)
            database.collectionQueries.deleteCollection(collectionModel.id)
        }
    }

    override suspend fun addAlbumToCollection(collectionModel: CollectionModel, albumID: Long) {
        if (collectionModel.id == -1L || albumID == -1L) {
            throw IllegalArgumentException("ID shouldn't be -1")
        }
        database.collections_AlbumsQueries.insertPair(
            Collections_Albums(collectionModel.id, albumID)
        )
    }

    override suspend fun deleteAlbumFromCollection(
        collectionModel: CollectionModel,
        albumID: Long
    ) {
        database.collections_AlbumsQueries.deletePair(collectionModel.id, albumID)
    }

    private fun mapToCollectionModel(
        id: Long,
        name: String,
        imageUrl: String?
    ) = CollectionModel(id = id, name = name, imageURL = imageUrl)

    private fun mapToAlbumModel(
        id: Long,
        title: String,
        artist: String,
        imageUrl: String?,
        link: String?
    ) = AlbumModel(title, artist, imageUrl, link, id)

}