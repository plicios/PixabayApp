package pl.pgorny.pixabayapp.ui

import pl.pgorny.pixabayapp.data.Image

data class ImagesState(
    val images: List<Image> = emptyList()
)