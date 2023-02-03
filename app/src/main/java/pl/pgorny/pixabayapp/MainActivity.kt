package pl.pgorny.pixabayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.pgorny.pixabayapp.data.images.api.ApiWithCacheImagesRepository
import pl.pgorny.pixabayapp.ui.PixabayTheme
import pl.pgorny.pixabayapp.ui.images.ImagesRoute
import pl.pgorny.pixabayapp.ui.images.ImagesScreen
import pl.pgorny.pixabayapp.ui.images.ImagesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            PixabayTheme {
                val imagesViewModel: ImagesViewModel =
                    viewModel(factory = ImagesViewModel.Factory(ApiWithCacheImagesRepository(applicationContext)))
                ImagesRoute(imagesViewModel)
            }
        }
    }
}
