package pl.pgorny.pixabayapp.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import pl.pgorny.pixabayapp.R


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    searchText: String = "",
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchAction: () -> Unit = {},
    onClearClick: () -> Unit = {
        onSearchQueryChanged("")
        onSearchAction()
    }
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .interceptKey(Key.Enter) {
//            onSearchQueryChanged("getValueFromComposeView")
                onSearchAction()
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
            },
        value = searchText,
        onValueChange = onSearchQueryChanged,
        placeholder = { Text(stringResource(R.string.search_images_placeholder)) },
        leadingIcon = { Icon(Icons.Filled.Search, null) },
        trailingIcon = {
            IconButton(onClick = { onClearClick() }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.search_clear_search_query)
                )
            }
        },
        singleLine = true,
        // keyboardOptions change the newline key to a search key on the soft keyboard
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        // keyboardActions submits the search query when the search key is pressed
        keyboardActions = KeyboardActions(
            onSearch = {
//                onSearchQueryChanged("getValueFromComposeView")
                onSearchAction()
                keyboardController?.hide()
            }
        )
    )
}

/**
 * Intercepts a key event rather than passing it on to children
 */
private fun Modifier.interceptKey(key: Key, onKeyEvent: () -> Unit): Modifier {
    return this.onPreviewKeyEvent {
        if (it.key == key && it.type == KeyEventType.KeyUp) { // fire onKeyEvent on KeyUp to prevent duplicates
            onKeyEvent()
            true
        } else it.key == key // only pass the key event to children if it's not the chosen key
    }
}
