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

    suspend fun deleteAlbum(albumModel: AlbumModel)

    suspend fun searchAlbumsByName(name: String): List<AlbumSearchModel>
}