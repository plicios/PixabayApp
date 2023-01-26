package pl.pgorny.pixabayapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ImagesRepository {
    private val pixabayService = Retrofit.Builder().baseUrl("https://pixabay.com/api")//TODO extract baseUrl
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PixabayService::class.java)

    suspend fun getImages(searchQuery: String) = pixabayService.getImages(searchQuery).hits.map { Image(it) }
}