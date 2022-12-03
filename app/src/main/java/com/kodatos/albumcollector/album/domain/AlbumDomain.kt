package com.kodatos.albumcollector.album.domain

import com.kodatos.albumcollector.album.models.AlbumModel
import com.kodatos.albumcollector.album.models.AlbumSearchModel

interface AlbumDomain {

    suspend fun getAllAlbums(): List<AlbumModel>

    /**
     * @param albumModel New album to insert in the database
     * @return DB-Domain album with an Album attached
     */
    suspend fun addAlbum(albumModel: AlbumModel): AlbumModel

    suspend fun addAlbumWithCollection(albumModel: AlbumModel, collectionID: Long)

    suspend fun deleteAlbum(albumID: Long)

    suspend fun searchAlbumsByName(name: String): List<AlbumSearchModel>

    suspend fun getAlbumByID(id: Long): AlbumModel?

    suspend fun updateAlbum(id: Long, newAlbum: AlbumModel)
}