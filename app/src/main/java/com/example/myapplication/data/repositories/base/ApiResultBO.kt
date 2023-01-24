package com.example.myapplication.data.repositories.base

sealed class ApiResultBO<out T> {
    data class Success<out T>(val data: T) : ApiResultBO<T>()
    object Loading : ApiResultBO<Nothing>()
    data class Error(val errorMessage: String) : ApiResultBO<Nothing>()
}