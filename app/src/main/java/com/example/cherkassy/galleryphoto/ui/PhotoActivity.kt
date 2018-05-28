package com.example.cherkassy.galleryphoto.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.example.cherkassy.galleryphoto.DataBaseCreator
import com.example.cherkassy.galleryphoto.MyApplication
import com.example.cherkassy.galleryphoto.R
import com.example.cherkassy.galleryphoto.common.AppExecutor
import com.example.cherkassy.galleryphoto.common.Logs
import com.example.cherkassy.galleryphoto.common.Utils
import com.example.cherkassy.galleryphoto.common.manager.NetworkManager
import com.example.cherkassy.galleryphoto.data.ImageDao
import com.example.cherkassy.galleryphoto.data.PhotoItem
import com.example.cherkassy.galleryphoto.ui.adapter.PhotoAdapter
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class PhotoActivity : AppCompatActivity() {

    companion object {
        const val TAG = "PhotoActivity"
    }

    val imageUrl = "http://bipbap.ru/wp-content/uploads/2017/04/000f_7290754.jpg"
    val imageUrl2 = "http://bipbap.ru/wp-content/uploads/2017/09/44_20140925_1955853838-220x220.gif"
    val imageUrl3 = "http://humor.fm/uploads/posts/2016-03/17/umndflr0wjc.jpg"

    @Inject
    lateinit var mNetworkManager: NetworkManager
    @Inject
    lateinit var mExecutor: AppExecutor

    private lateinit var mPhotoAdapter: PhotoAdapter
    private lateinit var mPhotoRecyclerView: RecyclerView
    private lateinit var mPhotoDao: ImageDao
    private lateinit var mNetworkReceiver: BroadcastReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        MyApplication.sApplicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPhotoDao = DataBaseCreator.mDataBase?.imageDao()!!

        initRecyclerView()

        mExecutor.diskIO.execute {
            val count = mPhotoDao?.countImages()
            Logs.d("PhotoActivity", "count: $count")

            showAllImages().subscribe { list ->
                val newList = Utils.getImagesList(list, mNetworkManager.isNetworkAccess())
//                saveImages(list)
                mPhotoDao?.insertAllImages(list)
                runOnUiThread {
                    mPhotoAdapter.swapData(newList)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startNetworkChangeReceiver()
    }

    override fun onPause() {
        super.onPause()
        stopNetworkChangeReceiver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(onQueryTextListener)
        return true
    }

    private fun showAllImages(): Single<MutableList<PhotoItem>> {
        return mPhotoDao?.loadAllImages()?.firstOrError()?.doOnSuccess { list ->
            if (list.isEmpty()) return@doOnSuccess
        }!!
    }

    private fun initRecyclerView() {
        mPhotoAdapter = PhotoAdapter(this)
        mPhotoRecyclerView = recycler_view_photo
        mPhotoRecyclerView.layoutManager = GridLayoutManager(this, 3)
        mPhotoRecyclerView.setHasFixedSize(true)
        mPhotoRecyclerView.adapter = mPhotoAdapter

    }

    private val onQueryTextListener =
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    val isOnline: Boolean = mNetworkManager.isNetworkAccess()

                    return if (query != null && query.isNotEmpty()) {
                        val photoItem = PhotoItem(query)

                        saveImage(photoItem, isOnline)
                        showAddImage(query, photoItem)
//                        startNetworkChangeReceiver()
                        true
                    } else {
                        showError(null, isNetworkAccess = isOnline)
                        true
                    }
                }
            }

    private fun saveImage(photoItem: PhotoItem, isOnline: Boolean = false) {
        mExecutor.diskIO.execute {
            if (isOnline) {
                val bitmap = Utils.getBitmapFromUrl(photoItem.urlLinkPhoto!!)
                photoItem.bytePhoto = Utils.getByteArrayFromBitmap(bitmap)
            }
            saveImageToDb(photoItem)
        }
    }

    private fun saveImages(lisImages: MutableList<PhotoItem>) {
        for (image in lisImages) {
            saveImageToDb(image)
        }
    }

    private fun saveImageToDb(photoItem: PhotoItem): Long {
        return mPhotoDao?.insertImage(photoItem)!!
    }


    private fun showAddImage(query: String?, photoItem: PhotoItem) {
        if (mNetworkManager.isNetworkAccess()) {
            mPhotoAdapter.addItem(photoItem)
        } else showError(query, false)
    }

    private fun showError(query: String? = null, isNetworkAccess: Boolean) {
        if (query == null || query.isEmpty()) {
            Toast.makeText(this, "Invalid links image", Toast.LENGTH_LONG).show()
        }

        if (!isNetworkAccess) {
            Toast.makeText(this, "save url links", Toast.LENGTH_LONG).show()
            Snackbar.make(constraintLayout, "No internet connection", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun startNetworkChangeReceiver() {
        mNetworkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (mNetworkManager.isNetworkAccess()) {
                    mExecutor.diskIO.execute {
                        mPhotoDao.getByteImages()
                                .firstOrError()
                                ?.subscribe({ list ->
                                    val newList = Utils.getImageListForDownloading(list)
                                    mPhotoDao.insertAllImages(newList)
                                })
                    }
                }
            }

        }


        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED")
        registerReceiver(mNetworkReceiver, intentFilter)
    }

    private fun stopNetworkChangeReceiver() {
        unregisterReceiver(mNetworkReceiver)
    }
}