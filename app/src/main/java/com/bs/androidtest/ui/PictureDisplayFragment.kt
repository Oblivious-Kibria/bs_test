package com.bs.androidtest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bs.androidtest.R
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_picture_display.view.*


class PictureDisplayFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.fragment_picture_display, container, false)
        val pictureUrl = arguments?.getString("PictureUrl")
        pictureUrl?.let {
            loadImage(parentView.ivPicture, it)
        }
        return parentView
    }


    private fun loadImage(ivPicture: AppCompatImageView, optionMenuIconUrl: String?) {
        val options: RequestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
        Glide.with(requireContext().applicationContext).load(optionMenuIconUrl)
                .apply(options)
                .into(ivPicture)
    }


}