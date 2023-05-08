package ru.mirea.structf.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mirea.structf.data.DocDatabase
import ru.mirea.structf.data.DocRepository
import ru.mirea.structf.data.DocRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDocDatabase(app: Application): DocDatabase {
        return Room.databaseBuilder(
            app,
            DocDatabase::class.java,
            "doc_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDocRepository(db: DocDatabase): DocRepository {
        return DocRepositoryImpl(db.dao)
    }
 }