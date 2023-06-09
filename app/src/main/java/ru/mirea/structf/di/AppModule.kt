package ru.mirea.structf.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mirea.structf.data.database.AppDatabase
import ru.mirea.structf.data.repository.CloudRepositoryImpl
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

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideCloudStorage() = Firebase.storage.reference

    @Provides
    @Singleton
    fun provideCloudRepository(storageRef: StorageReference) = CloudRepositoryImpl(storageRef)
 }