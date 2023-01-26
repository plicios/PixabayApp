package pl.pgorny.pixabayapp

data class Result<T>(
    val total: Int,
    val totalHits: Int,
    val hits: List<T>
)
