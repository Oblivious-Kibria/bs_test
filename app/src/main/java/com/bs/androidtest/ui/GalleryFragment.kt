package com.bs.androidtest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.bs.androidtest.R
import com.bs.androidtest.data.ApiResponse
import com.bs.androidtest.data.Picture
import com.bs.androidtest.data.Status
import com.bs.androidtest.ui.adapters.GalleryAdapter
import kotlinx.android.synthetic.main.fragment_gallery.view.*


class GalleryFragment : Fragment() {

    private lateinit var mAdapter: GalleryAdapter
    private lateinit var viewModel: MainViewModel
    private var progressCircular: ContentLoadingProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.fragment_gallery, container, false)
        progressCircular = parentView.progress_circular
        initViewModel()
        setAdapter(parentView)

        return parentView
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getApiResponse().observe(requireActivity(), androidx.lifecycle.Observer {
            consumeResponse(it)
        })
        fetchPictureList()
    }


    private fun fetchPictureList() {
        viewModel.dispatchPictureListRequest()
    }


    private fun setAdapter(view: View) {
        mAdapter = GalleryAdapter(requireContext())
        mAdapter.setToastDisplayListener(object : GalleryAdapter.PictureClickListener {
            override fun onPictureClick(picture: Picture) {
                val bundle = bundleOf("PictureUrl" to picture.downloadUrl)
                view.findNavController().navigate(R.id.action_gallery_fragment_to_picture_display_fragment, bundle)
            }
        })
        view.rvPictureList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
    }


    private fun consumeResponse(apiResponse: ApiResponse) {
        Log.d("ApiTesting", "consumeResponse")
        when (apiResponse.status) {
            Status.LOADING -> {
                showProgressBar()
            }
            Status.SUCCESS -> {
                hideProgressBar()
                val list = apiResponse.data as List<Picture>
                mAdapter.submitListData(list)
            }

            Status.FAILED, Status.ERROR -> {
                hideProgressBar()
            }
        }
    }


    private fun showProgressBar() {
        progressCircular?.visibility = View.VISIBLE
    }


    private fun hideProgressBar() {
        progressCircular?.visibility = View.GONE
    }

}