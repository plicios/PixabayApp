package pl.pgorny.pixabayapp.data

import pl.pgorny.pixabayapp.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {
    @GET("/")
    suspend fun getImages(@Query("q") searchQuery: String) : Result<ImageDto>
}