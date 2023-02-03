package pl.pgorny.pixabayapp.ui.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.pgorny.pixabayapp.data.images.ImagesRepository
import pl.pgorny.pixabayapp.data.Result
import pl.pgorny.pixabayapp.data.images.api.ApiImagesRepository
import pl.pgorny.pixabayapp.model.Image
import timber.log.Timber
import java.util.*

class ImagesViewModel(private val imagesRepository: ImagesRepository) : ViewModel() {
    private val viewModelState = MutableStateFlow(ImagesViewModelState(searchInput = "fruits"))
    val uiState: StateFlow<ImagesUiState> = viewModelState.map { it.toUiState() }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        viewModelState.value.toUiState()
    )

    init {
        refreshImages()
    }

    fun refreshImages() {
        viewModelScope.launch {
            viewModelState.update {
                val result = imagesRepository.getImages(it.searchInput)
                Timber.d(result.toString())
                when (result) {
                    is Result.Success -> it.copy(images = result.data, errorMessage = "")
                    is Result.SuccessWithErrorMessage -> it.copy(images = result.data, errorMessage = result.message)
                    is Result.Error -> it.copy(images = null, errorMessage = result.message)
                }
            }
        }
    }

    fun onSearchInputChanged(searchInput: String) {
        viewModelState.update {
            it.copy(searchInput = searchInput)
        }
    }

    fun onImageSelected(image: Image) {
        viewModelState.update { it.copy(selectedImage = image) }
    }

    fun showSelectedImage() {
        viewModelState.update { it.copy(showSelectedImage = true) }
    }

    fun onImageClosed() {
        viewModelState.update { it.copy(showSelectedImage = false, selectedImage = null) }
    }

    class Factory(
        private val imagesRepository: ImagesRepository = ApiImagesRepository()
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ImagesViewModel(imagesRepository) as T
        }
    }
}