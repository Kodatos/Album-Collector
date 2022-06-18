package com.kodatos.albumcollector.album

import comkodatosalbumcollector.Albums
import comkodatosalbumcollector.SelectAlbumsByName

interface AlbumRepository {

    suspend fun getAllAlbums(): List<Albums>

    /**
     * @param albumModel New album to insert in the database
     * @return DB-Domain album with an Album attached
     */
    suspend fun addAlbum(albumModel: AlbumModel): Albums

    suspend fun deleteAlbum(albumModel: AlbumModel)

    suspend fun searchAlbumsByName(name: String): List<SelectAlbumsByName>
}