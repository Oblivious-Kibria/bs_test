package com.bs.androidtest.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bs.androidtest.R
import com.bs.androidtest.data.Picture
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_picture_layout.view.*

class GalleryAdapter(private val mContext: Context) :
        RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {

    private lateinit var pictureClickListener: PictureClickListener



    private val diffUtilsCallBack = object : DiffUtil.ItemCallback<Picture>() {
        override fun areItemsTheSame(
                oldItem: Picture,
                newItem: Picture
        ): Boolean {
            return oldItem.author == newItem.author
        }

        override fun areContentsTheSame(
                oldItem: Picture,
                newItem: Picture
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }



    var differ: AsyncListDiffer<Picture> =
            AsyncListDiffer<Picture>(this, diffUtilsCallBack)



    fun setToastDisplayListener(pictureClickListener: PictureClickListener) {
        this.pictureClickListener = pictureClickListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_picture_layout, parent, false)
        )
    }



    override fun getItemCount(): Int {
        return differ.currentList.size
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data, position)
    }



    fun submitListData(dataList: List<Picture>) {
        differ.submitList(dataList)
    }


    inner class MyViewHolder(inflatedView: View) : RecyclerView.ViewHolder(inflatedView) {
        private val view: View = inflatedView
        private val tvAuthorName: AppCompatTextView = view.tvAuthorName
        private val ivPicture: AppCompatImageView = view.ivPicture
        var llItem: LinearLayoutCompat = view.llItem


        fun bind(data: Picture, position: Int) {
            tvAuthorName.text = data.author
            loadImage(data.downloadUrl)

            llItem.setOnClickListener {
                pictureClickListener.onPictureClick(differ.currentList[position])
            }
        }

        private fun loadImage(optionMenuIconUrl: String?) {
            val options: RequestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
            Glide.with(mContext.applicationContext).load(optionMenuIconUrl)
                    .apply(options)
                    .into(ivPicture)
        }
    }


    interface PictureClickListener {
        fun onPictureClick(picture: Picture)
    }

}