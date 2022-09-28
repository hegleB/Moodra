package com.quere.domain.model.common

import java.io.Serializable


data class Credit(
    var cast: List<Actor>

) : Serializable


data class Actor(
   val id: Long?,
   val name: String?,
   val profile_path: String?
) : Serializable