package com.kodatos.albumcollector.collection

import android.content.Context
import com.kodatos.albumcollector.Constants
import com.kodatos.albumcollector.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CollectionsModule {

    @Binds
    abstract fun bindCollectionsRepository(collectionsRepositoryImpl: CollectionsRepositoryImpl): CollectionsRepository

}