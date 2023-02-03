package pl.pgorny.pixabayapp.ui.image

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pl.pgorny.pixabayapp.R
import pl.pgorny.pixabayapp.model.Image
import pl.pgorny.pixabayapp.ui.BodyText

@Composable
fun ImageScreen(image: Image, onImageClosed: () -> Unit) {
    BackHandler(enabled = true) {
        onImageClosed()
    }
    Column {
        LabelWithValue(resourceId = R.string.author_user_name, value = image.user)
        LabelWithValue(resourceId = R.string.image_tags, value = image.tags.joinToString())
        LabelWithValue(resourceId = R.string.image_likes, value = image.likes.toString())
        LabelWithValue(resourceId = R.string.image_downloads, value = image.downloads.toString())
        LabelWithValue(resourceId = R.string.image_comments, value = image.comments.toString())
        AsyncImage(
            model = image.largeImageURL,
            contentDescription = ""
        )
    }
}

@Composable
fun LabelWithValue(@StringRes resourceId: Int, value: String){
    Row(modifier = Modifier.padding(vertical = 5.dp)) {
        BodyText(
            modifier = Modifier.padding(end = 5.dp),
            text = stringResource(id = resourceId)
        )
        BodyText(
            text = value
        )
    }
}