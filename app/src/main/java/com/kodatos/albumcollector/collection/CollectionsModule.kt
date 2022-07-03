package com.kodatos.albumcollector.collection

import com.kodatos.albumcollector.collection.domain.CollectionsDomain
import com.kodatos.albumcollector.collection.domain.CollectionsDomainImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CollectionsModule {

    @Binds
    abstract fun bindCollectionsDomain(collectionsDomainImpl: CollectionsDomainImpl): CollectionsDomain

}