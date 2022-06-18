package com.kodatos.albumcollector.collection

import com.kodatos.albumcollector.Database
import com.kodatos.albumcollector.album.AlbumModel
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import comkodatosalbumcollector.Collections
import comkodatosalbumcollector.Collections_Albums
import comkodatosalbumcollector.GetAlbumsForCollection
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalArgumentException
import javax.inject.Inject

class CollectionsRepositoryImpl @Inject constructor(
    private val database: Database
) : CollectionsRepository {

    override suspend fun getAllCollections(): Flow<List<Collections>> {
        return database.collectionQueries.selectAll().asFlow().mapToList()
    }

    override suspend fun getAlbumsForCollection(id: Long): Flow<List<GetAlbumsForCollection>> {
        return database.collections_AlbumsQueries.getAlbumsForCollection(id).asFlow().mapToList()
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

    override suspend fun addAlbumToCollection(
        collectionModel: CollectionModel,
        albumModel: AlbumModel
    ) {
        if (collectionModel.id == -1L || albumModel.id == -1L) {
            throw IllegalArgumentException("ID shouldn't be -1")
        }
        database.collections_AlbumsQueries.insertPair(
            Collections_Albums(collectionModel.id, albumModel.id)
        )
    }

    override suspend fun deleteAlbumToCollection(
        collectionModel: CollectionModel,
        albumModel: AlbumModel
    ) {
        database.collections_AlbumsQueries.deletePair(collectionModel.id, albumModel.id)
    }

}