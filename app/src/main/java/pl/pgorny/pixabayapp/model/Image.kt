package pl.pgorny.pixabayapp.model

import pl.pgorny.pixabayapp.data.images.api.PixabayImage

data class Image(
    val largeImageURL: String,
    val previewURL: String,
    val tags: List<String>,
    val user: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int
){
    constructor(pixabayImage: PixabayImage) : this(
        pixabayImage.largeImageURL,
        pixabayImage.previewURL,
        pixabayImage.tags.split(", ").toList(),
        pixabayImage.user,
        pixabayImage.likes,
        pixabayImage.downloads,
        pixabayImage.comments
    )
}
