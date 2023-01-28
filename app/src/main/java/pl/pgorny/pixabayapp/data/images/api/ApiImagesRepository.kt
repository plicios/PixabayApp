package pl.pgorny.pixabayapp.data.images.api

import pl.pgorny.pixabayapp.data.images.ImagesRepository
import pl.pgorny.pixabayapp.model.Image
import pl.pgorny.pixabayapp.data.Result
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiImagesRepository : ImagesRepository {
    private val pixabayService = Retrofit.Builder().baseUrl("https://pixabay.com/api")//TODO extract baseUrl
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PixabayService::class.java)

    override suspend fun getImages(searchQuery: String) =
        try{
            Result.Success(pixabayService.getImages(searchQuery).hits.map { Image(it) })
        }catch (e: Exception) {
            Result.Error(e)
        }
}