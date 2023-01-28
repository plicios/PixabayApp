package pl.pgorny.pixabayapp.data.images

import pl.pgorny.pixabayapp.data.Result
import pl.pgorny.pixabayapp.model.Image

interface ImagesRepository {
    suspend fun getImages(searchQuery: String) : Result<List<Image>>
}