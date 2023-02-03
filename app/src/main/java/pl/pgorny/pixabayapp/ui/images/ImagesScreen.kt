package pl.pgorny.pixabayapp.ui.images

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import pl.pgorny.pixabayapp.R
import pl.pgorny.pixabayapp.model.Image
import pl.pgorny.pixabayapp.ui.BodyText
import pl.pgorny.pixabayapp.ui.ErrorText
import pl.pgorny.pixabayapp.ui.image.ImageScreen
import pl.pgorny.pixabayapp.ui.search.SearchBar

@Composable
fun ImagesRoute(imagesViewModel: ImagesViewModel) {
    val imagesState by imagesViewModel.uiState.collectAsStateWithLifecycle()
    ImagesScreen(
        imagesUiState = imagesState,
        onSearchAction = imagesViewModel::refreshImages,
        onSearchQueryChanged = imagesViewModel::onSearchInputChanged,
        onImageSelected = imagesViewModel::onImageSelected,
        onConfirmShowSelectedImage = imagesViewModel::showSelectedImage,
        onImageClosed = imagesViewModel::onImageClosed
    )
}

@Composable
fun ImagesScreen(
    imagesUiState: ImagesUiState,
    onSearchAction: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onImageSelected: (Image) -> Unit,
    onConfirmShowSelectedImage: () -> Unit,
    onImageClosed: () -> Unit
) {
    Column(Modifier.padding(10.dp)) {
        when(imagesUiState) {
            is ImagesUiState.ShowImage -> {}
            is ImagesUiState.ShowDialog -> {}
            else -> SearchBar(searchText = imagesUiState.searchInput, onSearchAction = onSearchAction, onSearchQueryChanged = onSearchQueryChanged)
        }
        if(imagesUiState.errorMessage.isNotEmpty()){
            ErrorText(text = imagesUiState.errorMessage)
        }
        when(imagesUiState) {
            is ImagesUiState.HasImages -> {
                ImagesList(images = imagesUiState.images, onImageSelected = onImageSelected)
            }
            is ImagesUiState.NoImages -> {
                BodyText(text = stringResource(id = R.string.no_data))
            }
            is ImagesUiState.ShowImage -> ImageScreen(image = imagesUiState.image, onImageClosed = onImageClosed)
            is ImagesUiState.ShowDialog -> ShowImageDialog(
                hideDialog = { onImageClosed() },
                onConfirm = { onConfirmShowSelectedImage() }
            )
        }
    }
}

@Composable
fun ShowImageDialog(hideDialog: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = { hideDialog() },
        title = {Text(text = stringResource(id = R.string.more_details_dialog_title))},
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                },
            ) {
                Text(text = stringResource(id = R.string.more_details_dialog_confirm_button))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    hideDialog()
                },
            ) {
                Text(text = stringResource(id = R.string.more_details_dialog_dismiss_button))
            }
        }
    )
}

@Composable
fun ImagesList(images: List<Image>, onImageSelected: (Image) -> Unit) {
    LazyColumn {
        items(images){image ->
            ImageCard(image = image, onImageSelected)
        }
    }
}

@Composable
fun ImageCard(image: Image, onImageSelected: (Image) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .padding(vertical = 5.dp)
        .clickable { onImageSelected(image) }) {
        AsyncImage(
            modifier = Modifier.size(75.dp, 75.dp),
            model = image.previewURL,
            contentDescription = ""
        )
        Column(Modifier.padding(start = 5.dp)) {
            BodyText(text = image.user)
            BodyText(text = image.tags.joinToString())
        }
    }
}