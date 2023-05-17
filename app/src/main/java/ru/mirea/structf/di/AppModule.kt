package ru.mirea.structf.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mirea.structf.data.database.AppDatabase
import ru.mirea.structf.data.repository.DocRepositoryImpl
import ru.mirea.structf.data.repository.TagRepositoryImpl
import ru.mirea.structf.other.Constants.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDocDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideDocRepository(db: AppDatabase) = DocRepositoryImpl(db.getDocDao())

    @Provides
    @Singleton
    fun provideTagRepository(db: AppDatabase) = TagRepositoryImpl(db.getTagDao())
 }