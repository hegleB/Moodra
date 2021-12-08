package com.quere.moodra.retrofit

import com.google.gson.annotations.SerializedName

data class Credit(
    @SerializedName("cast") var cast: List<Actor>

)

data class Actor(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("profile_path") val profile_path: String?
)