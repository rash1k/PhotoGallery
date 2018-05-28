package com.example.cherkassy.galleryphoto.ui.adapter

import android.content.Context
import android.support.annotation.Nullable
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.cherkassy.galleryphoto.R
import com.example.cherkassy.galleryphoto.data.PhotoItem
import kotlinx.android.synthetic.main.item_photo.view.*


class PhotoAdapter(context: Context,
                   @Nullable
                   private val mImageClickListener: OnItemClickListener? = null)
    : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private var mItemsPhoto: MutableList<PhotoItem> = mutableListOf()
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)



    fun addItem(photoItem: PhotoItem) {
        mItemsPhoto.add(photoItem)
        notifyItemInserted(mItemsPhoto.size - 1)
    }

    fun clearData() {
        mItemsPhoto.clear()
        notifyDataSetChanged()
    }

    internal fun swapData(newRestaurants: MutableList<PhotoItem>) {
        if (mItemsPhoto.isEmpty()) {
            mItemsPhoto = newRestaurants
            notifyDataSetChanged()
        } else {
            /*
           * Otherwise we use DiffUtil to calculate the changes and update accordingly. This
            * shows the four methods you need to override to return a DiffUtil callback. The
            * old list is the current list stored in mForecast, where the new list is the new
            * values passed in from the observing the database.
            */
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mItemsPhoto.size
                }

                override fun getNewListSize(): Int {
                    return newRestaurants.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mItemsPhoto[oldItemPosition].id == newRestaurants[newItemPosition].id

                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldPhoto = mItemsPhoto[oldItemPosition]
                    val newPhoto = newRestaurants[newItemPosition]
                    return oldPhoto.id == newPhoto.id && oldPhoto.urlLinkPhoto == (newPhoto.urlLinkPhoto)
                }
            })
            mItemsPhoto = newRestaurants
            result.dispatchUpdatesTo(this)
        }
    }

    fun setSearchResult(photoItems: List<PhotoItem>) {
        mItemsPhoto.clear()
        mItemsPhoto.addAll(photoItems)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = layoutInflater
                .inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view, mImageClickListener)
    }

    override fun getItemCount(): Int {
        return mItemsPhoto.size
    }

    override fun onViewRecycled(holder: PhotoViewHolder) {
        super.onViewRecycled(holder)
        holder.unBindData()
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoItem = mItemsPhoto[position]
        holder.bindData(photoItem)
    }


    class PhotoViewHolder(view: View,
                          @Nullable
                          private val mImageClickListener: OnItemClickListener? = null)
        : RecyclerView.ViewHolder(view) {

        private val mImageViewPhoto = view.image_view_photo

        fun bindData(itemPhoto: PhotoItem) {

            if (itemPhoto.bytePhoto != null) {
                Glide.with(itemView.context)
                        .load(itemPhoto.bytePhoto)
                        .into(mImageViewPhoto)

            } else {
                Glide.with(itemView.context)
                        .load(itemPhoto.urlLinkPhoto)
                        .into(mImageViewPhoto)
            }

            mImageViewPhoto.setOnClickListener {
                mImageClickListener?.itemDetailClick(itemPhoto)
            }
        }

        fun unBindData() {
            mImageViewPhoto.setImageBitmap(null)
            mImageViewPhoto.setOnClickListener(null)
        }

    }

    interface OnItemClickListener {
        fun itemDetailClick(photoItem: PhotoItem)
    }
}