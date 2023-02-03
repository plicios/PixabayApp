package pl.pgorny.pixabayapp.data.images.api

import pl.pgorny.pixabayapp.data.images.ImagesRepository
import pl.pgorny.pixabayapp.model.Image
import pl.pgorny.pixabayapp.data.Result
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class ApiImagesRepository(private val pixabayService: PixabayService) : ImagesRepository {
    override suspend fun getImages(searchQuery: String) =
        try{
            Result.Success(pixabayService.getImages(searchQuery.replace(" ", "+")).hits.map { Image(it) })
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e.message ?: "")
        }
}