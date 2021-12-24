package com.quere.moodra.imagebind


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.quere.moodra.AppConstants
import com.quere.moodra.R
import jp.wasabeef.glide.transformations.BlurTransformation


object ImageBindingAdapter {

    val THUMBNAIL_URL = { key: String? -> "https://img.youtube.com/vi/$key/hqdefault.jpg" }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageUrl(imageView: ImageView, uri: String?) {


            Glide.with(imageView.context).load(AppConstants.BASE_IMAGE_URL + uri).diskCacheStrategy(
                DiskCacheStrategy.ALL
            ).into(imageView)

    }


    @BindingAdapter("personImage")
    @JvmStatic
    fun personImage(imageView: ImageView, uri: String?) {

        Glide.with(imageView.context).load(AppConstants.BASE_IMAGE_URL+uri).diskCacheStrategy(DiskCacheStrategy.DATA).into(imageView)

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
            .load(AppConstants.BASE_IMAGE_URL + imageUrl)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(13, 3)))
            .error(R.drawable.img_movielogo)
            .into(
                view
            )
    }

    @JvmStatic
    @BindingAdapter("detailPosterImage")
    fun detailPosterImage(view: ImageView, imageUrl: String?) {

        Glide.with(view.context).load(AppConstants.BASE_IMAGE_URL + imageUrl)
            .error(R.drawable.img_movielogo).into(
                view
            )
    }
}