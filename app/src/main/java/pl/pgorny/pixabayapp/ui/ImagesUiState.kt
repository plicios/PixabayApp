package pl.pgorny.pixabayapp.ui

import pl.pgorny.pixabayapp.model.Image

sealed interface ImagesUiState {
    val isLoading: Boolean
    val errorMessages: List<String>
    val searchInput: String

    data class NoImages(
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
        override val searchInput: String
    ) : ImagesUiState

    data class HasImages(
        val images: List<Image>,
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
        override val searchInput: String
    ) : ImagesUiState
}

data class ImagesViewModelState(
    val images: List<Image>? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<String> = emptyList(),
    val searchInput: String = ""
) {
    fun toUiState() = if(images != null && images.isNotEmpty()){
        ImagesUiState.HasImages(images, isLoading, errorMessages, searchInput)
    } else {
        ImagesUiState.NoImages(isLoading, errorMessages, searchInput)
    }
}