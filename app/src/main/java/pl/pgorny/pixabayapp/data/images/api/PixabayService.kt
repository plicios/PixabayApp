package pl.pgorny.pixabayapp.data.images.api

import pl.pgorny.pixabayapp.BuildConfig
import pl.pgorny.pixabayapp.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {
    @GET(".")
    suspend fun getImages(@Query("q") searchQuery: String, @Query("key") key: String = BuildConfig.PIXABAY_API_KEY) : Result<PixabayImage>
}