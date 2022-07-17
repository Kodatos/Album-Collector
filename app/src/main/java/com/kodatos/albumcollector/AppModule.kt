package com.kodatos.albumcollector

import android.content.Context
import com.kodatos.albumcollector.core.coroutines.DispatcherProvider
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesSqlDriver(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, Constants.DB_NAME)
    }

    @Provides
    fun providesDatabase(sqlDriver: SqlDriver): Database {
        return Database(sqlDriver)
    }

    @Provides
    fun providesDispatcher(): DispatcherProvider = object : DispatcherProvider {
        override val MAIN = Dispatchers.Main
        override val IO = Dispatchers.IO
        override val DEFAULT = Dispatchers.Default
    }
}