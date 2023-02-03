package pl.pgorny.pixabayapp.data.images.api

import android.content.Context
import androidx.room.Room
import pl.pgorny.pixabayapp.data.images.ImagesRepository
import pl.pgorny.pixabayapp.model.Image
import pl.pgorny.pixabayapp.data.Result
import pl.pgorny.pixabayapp.data.storage.PixabayDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class ApiWithCacheImagesRepository(applicationContext: Context) : ImagesRepository {
    private val pixabayService = Retrofit.Builder().baseUrl("https://pixabay.com/api/")//TODO extract baseUrl
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PixabayService::class.java)
    private val pixabayDatabase = Room.databaseBuilder(
        applicationContext,
        PixabayDatabase::class.java, "PixabayDatabase"
    ).build()


    private suspend fun cacheImages(images: List<Image>, searchQuery: String) {
        pixabayDatabase.imageDao().insertAll(*images.map { pl.pgorny.pixabayapp.data.storage.Image(it, searchQuery) }.toTypedArray())
    }

    private suspend fun getImagesFromCache(searchQuery: String) : List<Image>? {
        val images =
            pixabayDatabase.imageDao().getAllMatchingSearchQuery(searchQuery).map { it.entityToModel() }
        return images.ifEmpty { null }
    }

    override suspend fun getImages(searchQuery: String) =
        try{
            val images =
                pixabayService.getImages(searchQuery.replace(" ", "+")).hits.map { Image(it) }
            cacheImages(images, searchQuery)
            Result.Success(images)
        } catch (e: Exception) {
            Timber.e(e)
            val images = getImagesFromCache(searchQuery)
            if(images != null){
                Result.SuccessWithErrorMessage(images, "Data from local cache")
            } else {
                Result.Error("Error connecting to internet resources, check Your internet connection")
            }
        }
}