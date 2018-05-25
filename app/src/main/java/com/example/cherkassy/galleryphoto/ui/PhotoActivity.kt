package com.example.cherkassy.galleryphoto.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.example.cherkassy.galleryphoto.MyApplication
import com.example.cherkassy.galleryphoto.R
import com.example.cherkassy.galleryphoto.common.manager.NetworkManager
import com.example.cherkassy.galleryphoto.ui.adapter.PhotoAdapter
import com.example.cherkassy.galleryphoto.ui.model.PhotoItem
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class PhotoActivity : AppCompatActivity() {

    val TAG = "PhotoActivity"
    val imageUrl = "http://bipbap.ru/wp-content/uploads/2017/04/000f_7290754.jpg"

    @Inject
    lateinit var mNetworkManager: NetworkManager

    private val mItems: ArrayList<PhotoItem> = ArrayList()
    private lateinit var mPhotoAdapter: PhotoAdapter
    private lateinit var photoRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        MyApplication.sApplicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(onQueryTextListener)

        //region
        /* searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
             override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                 return true
             }

             override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                 // Do something when collapsed
 //                mImDbAdapter.setSearchResult(mListImDb)
                 return true // Return true to collapse action view
             }
         })
         return true*/
        //endregion
        return true
    }

    private fun initRecyclerView() {
        mPhotoAdapter = PhotoAdapter(this)
        photoRecyclerView = recycler_view_photo
        photoRecyclerView.layoutManager = LinearLayoutManager(this)
        photoRecyclerView.setHasFixedSize(true)
        photoRecyclerView.adapter = mPhotoAdapter
    }

    private val onQueryTextListener =
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null && query.isNotEmpty() && mNetworkManager.isOnline()) {
//                        mNetworkManager.isImageReachable(query)
//                        val photoItem = PhotoItem(query)
                        val photoItem = PhotoItem(imageUrl)
                        mItems.add(photoItem)
                        mPhotoAdapter.setSearchResult(mItems)
                        return true
                        //TODO() savetoDb
                    } else {
                        showError()
                    }
                    return false
                }
            }


    private fun showError() {
        Toast.makeText(this, "Invalid links image", Toast.LENGTH_LONG).show()
    }
}









