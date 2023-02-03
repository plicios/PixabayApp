package pl.pgorny.pixabayapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.pgorny.pixabayapp.data.images.ImagesRepository
import pl.pgorny.pixabayapp.data.images.api.ApiWithCacheImagesRepository
import pl.pgorny.pixabayapp.data.images.api.PixabayService
import pl.pgorny.pixabayapp.data.storage.PixabayDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideImagesRepository(
        database: PixabayDatabase,
        apiService: PixabayService
    ): ImagesRepository {
        return ApiWithCacheImagesRepository(
            database,
            apiService
        )
    }
}
