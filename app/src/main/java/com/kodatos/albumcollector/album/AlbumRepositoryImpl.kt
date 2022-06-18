package com.kodatos.albumcollector.album

import com.kodatos.albumcollector.Database
import comkodatosalbumcollector.Albums
import comkodatosalbumcollector.SelectAlbumsByName
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val database: Database
) : AlbumRepository {

    override suspend fun getAllAlbums(): List<Albums> {
        return database.albumQueries.selectAll().executeAsList()
    }

    override suspend fun addAlbum(albumModel: AlbumModel): Albums {
        return database.albumQueries.transactionWithResult {
            database.albumQueries.insertAlbum(
                albumModel.title,
                albumModel.artist,
                albumModel.deepLink,
                albumModel.imageURL
            )
            val lastInsertedID = database.albumQueries.selectLastInserted().executeAsOne()
            database.albumQueries.selectAlbumById(lastInsertedID).executeAsOne()
        }
    }

    override suspend fun deleteAlbum(albumModel: AlbumModel) {
        database.transaction {
            database.collections_AlbumsQueries.deleteAlbum(albumModel.id)
            database.albumQueries.deleteAlbum(albumModel.id)
        }
    }

    override suspend fun searchAlbumsByName(name: String): List<SelectAlbumsByName> {
        return database.albumQueries.selectAlbumsByName(name).executeAsList()
    }
}