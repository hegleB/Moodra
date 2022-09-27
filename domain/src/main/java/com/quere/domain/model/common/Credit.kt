package com.quere.domain.model.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credit(
    var cast: List<Actor>

) : Parcelable

@Parcelize
data class Actor(
   val id: Long?,
   val name: String?,
   val profile_path: String?
) : Parcelable