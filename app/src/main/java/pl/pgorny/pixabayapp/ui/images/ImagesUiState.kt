package pl.pgorny.pixabayapp.ui.images

import pl.pgorny.pixabayapp.model.Image

sealed interface ImagesUiState {
    val errorMessages: List<String>
    val searchInput: String

    data class NoImages(
        override val errorMessages: List<String>,
        override val searchInput: String
    ) : ImagesUiState

    data class HasImages(
        val images: List<Image>,
        override val errorMessages: List<String>,
        override val searchInput: String
    ) : ImagesUiState

    data class ShowDialog(
        override val errorMessages: List<String>,
        override val searchInput: String
    ) : ImagesUiState
    data class ShowImage(
        val image: Image,
        override val errorMessages: List<String>,
        override val searchInput: String
    ) : ImagesUiState
}

data class ImagesViewModelState(
    val images: List<Image>? = null,
    val selectedImage: Image? = null,
    val showSelectedImage: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessages: List<String> = emptyList(),
    val searchInput: String = ""
) {
    fun toUiState() = when {
        selectedImage != null && showSelectedImage -> ImagesUiState.ShowImage(selectedImage, errorMessages, searchInput)
        selectedImage != null && !showSelectedImage -> ImagesUiState.ShowDialog(errorMessages, searchInput)
        images != null && images.isNotEmpty() -> ImagesUiState.HasImages(images, errorMessages, searchInput)
        else -> ImagesUiState.NoImages(errorMessages, searchInput)
    }
}