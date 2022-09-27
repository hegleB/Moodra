package com.quere.presenation.view.detail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.quere.presenation.R
import com.quere.presenation.databinding.FragmentVideoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : DialogFragment() {

    lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate<FragmentVideoBinding>(
            inflater,
            R.layout.fragment_video,
            container,
            false
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()


        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mArgs = arguments
        val mValue = mArgs!!.getString("key")
        binding.apply {


            youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    val videoId = mValue
                    youTubePlayer.loadVideo(videoId!!, 0.0f)


                }
            })

            videoIvFinishButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    dismiss()
                }

            })

        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        return super.onCreateDialog(savedInstanceState)
    }
}