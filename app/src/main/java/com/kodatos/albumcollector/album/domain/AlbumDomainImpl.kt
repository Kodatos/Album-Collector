package com.kodatos.albumcollector.album.domain

import com.kodatos.albumcollector.Database
import com.kodatos.albumcollector.album.models.AlbumModel
import com.kodatos.albumcollector.album.models.AlbumSearchModel
import comkodatosalbumcollector.Collections_Albums
import javax.inject.Inject

class AlbumDomainImpl @Inject constructor(
    private val database: Database
) : AlbumDomain {

    override suspend fun getAllAlbums(): List<AlbumModel> {
        return database.albumQueries.selectAll(::mapToAlbumModel).executeAsList()
    }

    override suspend fun addAlbum(albumModel: AlbumModel): AlbumModel {
        return database.albumQueries.transactionWithResult {
            database.albumQueries.insertAlbum(
                albumModel.title,
                albumModel.artist,
                albumModel.deepLink,
                albumModel.imageURL
            )
            val lastInsertedID = database.albumQueries.selectLastInserted().executeAsOne()
            database.albumQueries.selectAlbumById(lastInsertedID, ::mapToAlbumModel).executeAsOne()
        }
    }

    override suspend fun addAlbumWithCollection(albumModel: AlbumModel, collectionID: Long) {
        return database.albumQueries.transaction {
            database.albumQueries.insertAlbum(
                albumModel.title,
                albumModel.artist,
                albumModel.deepLink,
                albumModel.imageURL
            )
            val lastInsertedID = database.albumQueries.selectLastInserted().executeAsOne()
            database.collections_AlbumsQueries.insertPair(
                Collections_Albums(
                    collectionID,
                    lastInsertedID
                )
            )
        }
    }

    override suspend fun deleteAlbum(albumID: Long) {
        database.transaction {
            database.collections_AlbumsQueries.deleteAlbum(albumID)
            database.albumQueries.deleteAlbum(albumID)
        }
    }

    override suspend fun searchAlbumsByName(name: String): List<AlbumSearchModel> {
        return database.albumQueries.selectAlbumsByName(name, ::mapToSearchModel).executeAsList()
    }

    override suspend fun getAlbumByID(id: Long): AlbumModel? {
        return database.albumQueries.selectAlbumById(id, ::mapToAlbumModel).executeAsOneOrNull()
    }

    override suspend fun updateAlbum(id: Long, newAlbum: AlbumModel) {
        database.albumQueries.updateAlbum(
            newAlbum.title,
            newAlbum.artist,
            newAlbum.deepLink,
            newAlbum.imageURL,
            id
        )
    }

    private fun mapToAlbumModel(
        id: Long,
        title: String,
        artist: String,
        link: String?,
        imageUrl: String?
    ) = AlbumModel(title, artist, imageUrl, link, id)

    private fun mapToSearchModel(
        id: Long,
        title: String,
        imageUrl: String?,
        artist: String
    ) = AlbumSearchModel(title, artist, imageUrl, id)
}