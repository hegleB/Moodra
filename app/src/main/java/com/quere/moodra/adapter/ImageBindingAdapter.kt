package com.quere.moodra.adapter


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.quere.moodra.R


object ImageBindingAdapter {
    val baseUrl = "https://image.tmdb.org/t/p/w500"
    val THUMBNAIL_URL = {key: String? -> "https://img.youtube.com/vi/$key/hqdefault.jpg"}

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageUrl(imageView : ImageView, uri : String?) {

        Glide.with(imageView.context).load(baseUrl+uri).transition(DrawableTransitionOptions()).into(imageView)
    }


    @BindingAdapter("personImage")
    @JvmStatic
    fun personImage(imageView : ImageView, uri : String?) {

        val baseUrl = "https://image.tmdb.org/t/p/w500"
        Glide.with(imageView.context).load(baseUrl+uri).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bindThumbNail")
    fun bindThumbNail(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(THUMBNAIL_URL(imageUrl))
            .fitCenter()
            .error(R.drawable.movielogo)
            .into(view)
    }
}