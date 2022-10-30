package com.kodatos.albumcollector

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@SuppressWarnings("InjectDispatcher")
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesSqlDriver(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, Constants.DB_NAME)
    }

    @Provides
    @Singleton
    fun providesDatabase(sqlDriver: SqlDriver): Database {
        return Database(sqlDriver)
    }

    @Provides
    @Named(IO_DISPATCHER)
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named(MAIN_DISPATCHER)
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Named(DEFAULT_DISPATCHER)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    const val IO_DISPATCHER = "io"
    const val MAIN_DISPATCHER = "main"
    const val DEFAULT_DISPATCHER = "default"
}