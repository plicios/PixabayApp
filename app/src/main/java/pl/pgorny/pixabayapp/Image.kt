package pl.pgorny.pixabayapp

data class Image(
    val largeImageURL: String,
    val previewURL: String,
    val tags: List<String>,
    val user: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int
){
    constructor(imageDto: ImageDto) : this(
        imageDto.largeImageURL,
        imageDto.previewURL,
        imageDto.tags.split(", ").toList(),
        imageDto.user,
        imageDto.likes,
        imageDto.downloads,
        imageDto.comments
    )
}
