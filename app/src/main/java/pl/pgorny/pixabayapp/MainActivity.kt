package pl.pgorny.pixabayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import pl.pgorny.pixabayapp.ui.PixabayTheme
import pl.pgorny.pixabayapp.ui.images.ImagesRoute
import pl.pgorny.pixabayapp.ui.images.ImagesViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            PixabayTheme {
                val imagesViewModel: ImagesViewModel = hiltViewModel()
                ImagesRoute(imagesViewModel)
            }
        }
    }
}
