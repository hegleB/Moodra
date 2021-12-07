package com.example.moviesimpleapp.retrofit

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Result<T> : Serializable {
    val id: Int = 0
    val page: Int = 0
    val results: List<T> = emptyList()
    val totalPages: Int = 0
    val totalResults: Int = 0

}