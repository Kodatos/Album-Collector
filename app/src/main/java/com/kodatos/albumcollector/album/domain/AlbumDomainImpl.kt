package com.kodatos.albumcollector.album.domain

import com.kodatos.albumcollector.Database
import com.kodatos.albumcollector.album.models.AlbumModel
import com.kodatos.albumcollector.album.models.AlbumSearchModel
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

    override suspend fun deleteAlbum(albumModel: AlbumModel) {
        database.transaction {
            database.collections_AlbumsQueries.deleteAlbum(albumModel.id)
            database.albumQueries.deleteAlbum(albumModel.id)
        }
    }

    override suspend fun searchAlbumsByName(name: String): List<AlbumSearchModel> {
        return database.albumQueries.selectAlbumsByName(name, ::mapToSearchModel).executeAsList()
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