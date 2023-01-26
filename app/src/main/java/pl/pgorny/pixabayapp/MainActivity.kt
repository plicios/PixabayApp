package pl.pgorny.pixabayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val images = listOf(
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
        )
        setContent{
            Images(images = images)
        }
    }
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
    Row {
        AsyncImage(
            model = image.previewURL,
            contentDescription = "Translated description of what the image contains"
        )
        Column {
            Text(text = image.user)
            Text(text = image.tags.toString())
        }
    }
}