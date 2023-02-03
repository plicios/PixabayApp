package pl.pgorny.pixabayapp.ui.images

import pl.pgorny.pixabayapp.model.Image

sealed interface ImagesUiState {
    val errorMessage: String
    val searchInput: String

    data class NoImages(
        override val errorMessage: String,
        override val searchInput: String
    ) : ImagesUiState

    data class HasImages(
        val images: List<Image>,
        override val errorMessage: String,
        override val searchInput: String
    ) : ImagesUiState

    data class ShowDialog(
        override val errorMessage: String,
        override val searchInput: String
    ) : ImagesUiState
    data class ShowImage(
        val image: Image,
        override val errorMessage: String,
        override val searchInput: String
    ) : ImagesUiState
}

data class ImagesViewModelState(
    val images: List<Image>? = null,
    val selectedImage: Image? = null,
    val showSelectedImage: Boolean = false,
    val errorMessage: String = "",
    val searchInput: String = ""
) {
    fun toUiState() = when {
        selectedImage != null && showSelectedImage -> ImagesUiState.ShowImage(selectedImage, errorMessage, searchInput)
        selectedImage != null && !showSelectedImage -> ImagesUiState.ShowDialog(errorMessage, searchInput)
        images != null && images.isNotEmpty() -> ImagesUiState.HasImages(images, errorMessage, searchInput)
        else -> ImagesUiState.NoImages(errorMessage, searchInput)
    }
}