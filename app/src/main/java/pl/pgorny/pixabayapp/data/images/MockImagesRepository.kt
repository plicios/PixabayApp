package pl.pgorny.pixabayapp.data.images

import pl.pgorny.pixabayapp.data.Result
import pl.pgorny.pixabayapp.model.Image

class MockImagesRepository : ImagesRepository {
    override suspend fun getImages(searchQuery: String): Result<List<Image>> = Result.Success(listOf(
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
        ))
}