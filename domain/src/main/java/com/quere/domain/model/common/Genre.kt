package com.quere.domain.model.common

import java.io.Serializable


data class Genres(
    val id: Integer,
    val results: List<Detail>
) : Serializable


data class Genre(
    val id: Int,
    val name: String?
) : Serializable