package com.example.cherkassy.galleryphoto.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface ImageDao {

    @Query("SELECT * FROM images WHERE id = :id LIMIT 1")
    fun loadPhoto(id: Int): Flowable<PhotoItem>


    @Query("SELECT * FROM images")
    fun loadAllImages(): Flowable<MutableList<PhotoItem>>

    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(photo: PhotoItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllImages(photos: MutableList<PhotoItem>): List<Long>

    @Query("SELECT COUNT(*) from images")
    fun countImages(): Int

    @Query("SELECT * FROM images WHERE byte_photo == NULL")
    fun getByteImages(): Flowable<MutableList<PhotoItem>>
}