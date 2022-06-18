package com.kodatos.albumcollector.album

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class AlbumModule {

    @Binds
    abstract fun bindsAlbumRepository(albumRepositoryImpl: AlbumRepositoryImpl): AlbumRepository
}