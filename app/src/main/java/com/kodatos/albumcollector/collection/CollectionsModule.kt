package com.kodatos.albumcollector.collection

import com.kodatos.albumcollector.collection.domain.CollectionsDomain
import com.kodatos.albumcollector.collection.domain.CollectionsDomainImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CollectionsModule {

    @Binds
    @Singleton
    fun bindCollectionsDomain(collectionsDomainImpl: CollectionsDomainImpl): CollectionsDomain
}