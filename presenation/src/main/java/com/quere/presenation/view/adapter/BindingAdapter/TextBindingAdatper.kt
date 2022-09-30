package com.quere.presenation.view.adapter.BindingAdapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.quere.domain.model.common.Genre

object TextBindingAdatper {

    @BindingAdapter("detailruntime")
    @JvmStatic
    fun runtimeText(textView: TextView, runtime: Int?) {

        if (runtime != null) {
            textView.setText(runtime.toString() + "분")
        } else {
            textView.setText("")
        }
    }

    @BindingAdapter("detailGenre")
    @JvmStatic
    fun genreText(textView: TextView, genreList: List<Genre>?) {
        var genre = ""

        if (genreList != null) {
            for (g in genreList) {
                genre += "${g.name}, "
            }
            if(genre.length>=2) {
                textView.setText(genre.substring(0, genre.length - 2))
            } else {
                textView.setText("")
            }

        } else {
            textView.setText("")
        }
    }

    @BindingAdapter("postionViewPager")
    @JvmStatic
    fun setPositionViewPager(textView: TextView, page: Int) {
        val currentPage = "${page%20+1} / 20"

        textView.setText(currentPage)
    }
}