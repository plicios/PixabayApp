package pl.pgorny.pixabayapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import pl.pgorny.pixabayapp.R
import pl.pgorny.pixabayapp.model.Image

@Preview
@Composable
fun ImagesScreen(
    imagesViewModel: ImagesViewModel = viewModel(factory = ImagesViewModel.Factory())
) {
    val imagesState by imagesViewModel.uiState.collectAsState()
    Column(Modifier.padding(10.dp)) {
        SearchBar(searchText = imagesState.searchInput) { value -> imagesViewModel.onSearchInputChanged(value) }
        when(imagesState) {
            is ImagesUiState.HasImages -> ImagesList(imagesState as ImagesUiState.HasImages)
            is ImagesUiState.NoImages -> Text(text = "No data")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(searchText: String = "", onSearchQueryChanged: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().interceptKey(Key.Enter) {
//            onSearchQueryChanged("getValueFromComposeView")
            keyboardController?.hide()
            focusManager.clearFocus(force = true)
        },
        value = searchText,
        onValueChange = onSearchQueryChanged,
        placeholder = { Text(stringResource(R.string.search_images_placeholder)) },
        leadingIcon = { Icon(Icons.Filled.Search, null) },
        singleLine = true,
        // keyboardOptions change the newline key to a search key on the soft keyboard
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        // keyboardActions submits the search query when the search key is pressed
        keyboardActions = KeyboardActions(
            onSearch = {
//                onSearchQueryChanged("getValueFromComposeView")
                keyboardController?.hide()
            }
        )
    )
}


/**
 * Intercepts a key event rather than passing it on to children
 */
fun Modifier.interceptKey(key: Key, onKeyEvent: () -> Unit): Modifier {
    return this.onPreviewKeyEvent {
        if (it.key == key && it.type == KeyEventType.KeyUp) { // fire onKeyEvent on KeyUp to prevent duplicates
            onKeyEvent()
            true
        } else it.key == key // only pass the key event to children if it's not the chosen key
    }
}

//@Composable
//fun SearchBar2(onSearchQueryChanged: () -> Unit){
//    OutlinedTextField(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 2.dp)
//            .onFocusChanged { focusState ->
//                showClearButton = (focusState.isFocused)
//            }
//            .focusRequester(focusRequester),
//        value = searchText,
//        onValueChange = onSearchTextChanged,
//        placeholder = {
//            Text(text = placeholderText)
//        },
//        colors = TextFieldDefaults.textFieldColors(
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            backgroundColor = Color.Transparent,
//            cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
//        ),
//        trailingIcon = {
//            AnimatedVisibility(
//                visible = showClearButton,
//                enter = fadeIn(),
//                exit = fadeOut()
//            ) {
//                IconButton(onClick = { onClearClick() }) {
//                    Icon(
//                        imageVector = Icons.Filled.Close,
//                        contentDescription = ""
//                    )
//                }
//
//            }
//        },
//        maxLines = 1,
//        singleLine = true,
//        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//        keyboardActions = KeyboardActions(onDone = {
//            keyboardController?.hide()
//        }),
//    )
//}

@Composable
fun ImagesList(imagesState: ImagesUiState.HasImages) {
    Images(images = imagesState.images)
}

@Composable
fun Images(images: List<Image>) {
    LazyColumn {
        items(images){image ->
            ImageCard(image = image)
        }
    }
}

@Composable
fun ImageCard(image: Image) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            modifier = Modifier.size(75.dp, 75.dp),
            model = image.previewURL,
            contentDescription = ""
        )
        Column {
            Text(text = image.user)
            Text(text = image.tags.toString())
        }
    }
}