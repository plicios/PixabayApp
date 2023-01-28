package pl.pgorny.pixabayapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import pl.pgorny.pixabayapp.data.Image

@Preview
@Composable
fun ImagesScreen(
    imagesViewModel: ImagesViewModel = viewModel()
) {
    Column(Modifier.padding(10.dp)) {
        SearchBar {}
        ImagesList(imagesViewModel = imagesViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearchQueryChanged: (String) -> Unit) {
    val searchText = ""
    OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = searchText, onValueChange = onSearchQueryChanged)
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
fun ImagesList(imagesViewModel: ImagesViewModel) {

    val imagesState by imagesViewModel.uiState.collectAsState()
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