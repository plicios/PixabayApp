package pl.pgorny.pixabayapp.data

data class ImageDto(
    val largeImageURL: String,
    val previewURL: String,
    val tags: String,
    val user: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int
)
