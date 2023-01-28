package pl.pgorny.pixabayapp.data.images.api

data class PixabayImage(
    val largeImageURL: String,
    val previewURL: String,
    val tags: String,
    val user: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int
)
