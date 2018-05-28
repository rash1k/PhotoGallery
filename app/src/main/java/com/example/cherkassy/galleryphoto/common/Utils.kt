package com.example.cherkassy.galleryphoto.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.cherkassy.galleryphoto.data.PhotoItem
import java.io.ByteArrayOutputStream
import java.net.URL


object Utils {

    fun getBitmapFromUrl(utlStr: String): Bitmap {
        val ins = URL(utlStr).openStream()
        return BitmapFactory.decodeStream(ins)
    }


    fun getBitmapFromByteArray(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    fun getByteArrayFromBitmap(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun getImagesList(list: MutableList<PhotoItem>, isOnline: Boolean): MutableList<PhotoItem> {
        val newList = mutableListOf<PhotoItem>()

        for (item in list) {
            if (item.bytePhoto != null) {
                newList.add(item)
            } else if (item.bytePhoto == null && isOnline) {
                val bitmap = getBitmapFromUrl(item.urlLinkPhoto!!)
                item.bytePhoto = getByteArrayFromBitmap(bitmap)
                newList.add(item)
            }
        }
        return newList
    }

    fun getImageListForDownloading(list: MutableList<PhotoItem>): MutableList<PhotoItem> {
        for (item in list) {
            if (item.urlLinkPhoto != null) {
                item.bytePhoto = getByteArrayFromBitmap(getBitmapFromUrl(item.urlLinkPhoto))
            }
        }
        return list
    }
}