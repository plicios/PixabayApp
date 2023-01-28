package pl.pgorny.pixabayapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.pgorny.pixabayapp.data.images.ImagesRepository
import pl.pgorny.pixabayapp.data.images.MockImagesRepository
import pl.pgorny.pixabayapp.data.Result
import pl.pgorny.pixabayapp.data.images.api.ApiImagesRepository
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
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            viewModelState.update {
                Timber.d("check")
                val result = imagesRepository.getImages(it.searchInput)
                Timber.d(result.toString())
                when (result) {
                    is Result.Success -> it.copy(images = result.data, isLoading = false)
                    is Result.Error -> {
                        val errorMessages = it.errorMessages + (result.exception.message ?: "")
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }
            }
        }
    }

    fun onSearchInputChanged(searchInput: String) {
        viewModelState.update {
            it.copy(searchInput = searchInput)
        }
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