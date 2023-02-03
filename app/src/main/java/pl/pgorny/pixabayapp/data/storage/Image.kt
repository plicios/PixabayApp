package pl.pgorny.pixabayapp.data.storage

import androidx.room.Entity

@Entity(tableName = "image", primaryKeys = ["previewURL", "searchQuery"])
data class Image(
    val largeImageURL: String,
    val previewURL: String,
    val searchQuery: String,
    val tags: String,
    val user: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int
) {
    constructor(image: pl.pgorny.pixabayapp.model.Image, searchQuery: String): this(
        image.largeImageURL,
        image.previewURL,
        searchQuery,
        image.tags.joinToString(),
        image.user,
        image.likes,
        image.downloads,
        image.comments
    )

    fun entityToModel() = pl.pgorny.pixabayapp.model.Image(
        largeImageURL,
        previewURL,
        tags.split(", "),
        user,
        likes,
        downloads,
        comments
    )
}
