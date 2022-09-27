package com.quere.presenation.view.adapter.BindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.quere.presenation.R
import jp.wasabeef.glide.transformations.BlurTransformation

object ImageBindingAdapter {

    val THUMBNAIL_URL = { key: String? -> "https://img.youtube.com/vi/$key/hqdefault.jpg" }
    val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageUrl(imageView: ImageView, uri: String?) {


        Glide.with(imageView.context).load(BASE_IMAGE_URL + uri)
            .error(R.drawable.img_movielogo).diskCacheStrategy(
            DiskCacheStrategy.ALL
        ).into(imageView)

    }

    @JvmStatic
    @BindingAdapter("bindThumbNail")
    fun bindThumbNail(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .asBitmap()
            .load(THUMBNAIL_URL(imageUrl))
            .fitCenter()
            .error(R.drawable.img_movielogo)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("detailBackdropImage")
    fun detailBackdropImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(BASE_IMAGE_URL + imageUrl)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(13, 3)))
            .error(R.drawable.img_movielogo)
            .into(
                view
            )
    }

}