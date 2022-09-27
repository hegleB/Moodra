package com.quere.presenation.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quere.presenation.R


class SearchContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        println("SearchContainerFragment")

        return inflater.inflate(R.layout.fragment_search_container, container, false)
    }
}