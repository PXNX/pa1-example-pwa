package util

sealed class Status<out T> {
    class Loading<T> : Status<T>()
    data class Success<out T>(val data: T) : Status<T>()
    data class Failure<out Exception>(val exception: Exception) : Status<Exception>()
}