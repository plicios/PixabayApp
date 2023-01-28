package pl.pgorny.pixabayapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.pgorny.pixabayapp.data.Image

class ImagesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ImagesState(listOf(
            Image(
                "",
                "https://cdn.pixabay.com/photo/2017/07/29/15/43/knight-2551859_150.jpg",
                listOf(),
                "test",
                1,
                1,
                1
            ),
        Image(
            "",
            "https://cdn.pixabay.com/photo/2016/09/14/13/41/knight-1669434_150.png",
            listOf(),
            "test2",
            2,
            2,
            2
        )
    )))
    val uiState: StateFlow<ImagesState> = _uiState.asStateFlow()
}