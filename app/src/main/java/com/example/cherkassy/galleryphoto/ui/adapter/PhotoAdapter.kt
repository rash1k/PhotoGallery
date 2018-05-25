package com.example.cherkassy.galleryphoto.ui.adapter

import android.content.Context
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.cherkassy.galleryphoto.R
import com.example.cherkassy.galleryphoto.ui.model.PhotoItem
import kotlinx.android.synthetic.main.item_photo.view.*


class PhotoAdapter(context: Context,
                   @Nullable
                   private val mImageClickListener: OnItemClickListener? = null)
    : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private var mItems: ArrayList<PhotoItem> = arrayListOf()
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)


    fun setSearchResult(mItems: ArrayList<PhotoItem>) {
        this.mItems = mItems
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = layoutInflater
                .inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view, mImageClickListener)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onViewRecycled(holder: PhotoViewHolder) {
        super.onViewRecycled(holder)
        holder.unBindData()
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoItem = mItems[position]
        holder.bindData(photoItem)
    }


    class PhotoViewHolder(view: View,
                          @Nullable
                          private val mImageClickListener: OnItemClickListener? = null)
        : RecyclerView.ViewHolder(view) {

        private val mImageViewPhoto = view.image_view_photo

        val imageUrl = "http://bipbap.ru/wp-content/uploads/2017/04/000f_7290754.jpg"
        fun bindData(itemPhoto: PhotoItem) {

            Glide.with(itemView.context)
                    .load(itemPhoto.urlLinksPhoto)
                    .into(mImageViewPhoto)

            /* Picasso.get()
                     .load(itemPhoto.urlLinksPhoto)
                     .into(mImageViewPhoto)*/
//                    .centerCrop()
//                    .fit()
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)


            mImageViewPhoto.setOnClickListener {
                mImageClickListener?.itemDetailClick(itemPhoto)
            }

//          progressView.setVisibility(View.GONE);
//          Picasso.get().load(R.drawable.landing_screen).into(imageView1);
//          Picasso.get().load("file:///android_asset/DvpvklR.png").into(imageView2);
//         Picasso.get().load(new File(...)).into(imageView3);
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