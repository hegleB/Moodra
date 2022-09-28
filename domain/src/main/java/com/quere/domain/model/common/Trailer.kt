package com.quere.domain.model.common


import java.io.Serializable

data class Trailers(
    val id: Integer,
    val results: List<Trailer>

) : Serializable

data class Trailer(
    val name: String,
    val key: String,
    val site: String,
    val size: Integer,
    val type: String,
    val id: String
) : Serializable