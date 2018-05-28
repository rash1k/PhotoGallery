package com.example.cherkassy.galleryphoto.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.ColumnInfo.BLOB
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "images")
class PhotoItem(@ColumnInfo(name = "url_links_photo")
                val urlLinkPhoto: String?) {

    @PrimaryKey(autoGenerate = true)
    var id = 0


    @ColumnInfo(typeAffinity = BLOB,name = "byte_photo")
    var bytePhoto: ByteArray? = null
}

