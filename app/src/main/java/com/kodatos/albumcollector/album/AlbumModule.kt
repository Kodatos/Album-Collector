package com.kodatos.albumcollector.album

import com.kodatos.albumcollector.album.domain.AlbumDomain
import com.kodatos.albumcollector.album.domain.AlbumDomainImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class AlbumModule {

    @Binds
    abstract fun bindsAlbumDomain(albumDomainImpl: AlbumDomainImpl): AlbumDomain
}