package pl.pgorny.pixabayapp.data

sealed interface Result<out T> {
    data class Success<out T>(val data: T) : Result<T>

    data class SuccessWithErrorMessage<out T>(val data: T, val message: String) : Result<T>
    data class Error(val message: String) : Result<Nothing>
}