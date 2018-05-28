package com.example.cherkassy.galleryphoto

import android.arch.persistence.room.Room
import android.content.Context
import com.example.cherkassy.galleryphoto.data.AppDatabase
import com.example.cherkassy.galleryphoto.data.AppDatabase.Companion.DATABASE_NAME
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicBoolean


object DataBaseCreator {

     var mDataBase: AppDatabase? = null

    private val mInitializing = AtomicBoolean(true)

    fun createDataBase(context: Context) {
        if (mInitializing.compareAndSet(true, false).not()) {
            return
        }

        Completable.fromAction {
            mDataBase = Room.databaseBuilder(context,
                    AppDatabase::class.java, DATABASE_NAME)
                    .build()
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { it.printStackTrace() })

    }
}