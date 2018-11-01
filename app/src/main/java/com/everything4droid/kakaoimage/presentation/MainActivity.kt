package com.everything4droid.kakaoimage.presentation

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.everything4droid.kakaoimage.R
import com.everything4droid.kakaoimage.data.entity.Image
import com.everything4droid.kakaoimage.data.util.observeWith
import com.everything4droid.kakaoimage.mvvm.SearchImageVM
import com.everything4droid.kakaoimage.mvvm.SearchImageVMF
import com.everything4droid.kakaoimage.presentation.adapter.ImageAdapter
import org.koin.android.ext.android.inject
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.everything4droid.kakaoimage.data.util.ERROR_STATUS
import com.everything4droid.kakaoimage.data.util.EndlessRecyclerOnScrollListener
import com.everything4droid.kakaoimage.data.util.ErrorKit
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var vm: SearchImageVM
    private val vmFactory by inject<SearchImageVMF>()

    lateinit var endlessRecyclerScrollListener: EndlessRecyclerOnScrollListener

    lateinit var imageAdapter: ImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // load ViewModel
        vm = ViewModelProviders.of(this, vmFactory).get(SearchImageVM::class.java)

        // observing ViewModel attributes
        vm.uiModel.observeWith(this, this::onImageList)
        vm.isSearching.observeWith(this, this::onSearching)
        vm.isEmpty.observeWith(this, this::onEmpty)
        vm.isError.observeWith(this, this::onError)

        // init ImageAdapter
        imageAdapter = ImageAdapter()

        // handle ImageAdapter click
        imageAdapter.setOnItemClickListener(object : ImageAdapter.OnItemClickListener {
            override fun onClick(view: View, data: Image) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                val jsonBody = Gson().toJson(data)
                intent.putExtra("body", jsonBody)
                startActivity(intent)
            }

        })

        // add loadMore function
        endlessRecyclerScrollListener = object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                vm.search(keywordT.text.toString())
            }
        }

        // init recyclerView attrs
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = imageAdapter
            addOnScrollListener(endlessRecyclerScrollListener)
        }


        // listen EditText
        keywordT.addTextChangedListener(
            object : TextWatcher {

                private var timer = Timer()
                private val DELAY: Long = 2000
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun afterTextChanged(s: Editable) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(
                        object : TimerTask() {
                            override fun run() {
                                runOnUiThread {
                                    recyclerView.scrollToPosition(0)
                                }
                                vm.reset()
                                imageAdapter.clear()
                                vm.search(keywordT.text.toString())
                            }
                        },
                        DELAY
                    )
                }
            }
        )
    }

    private fun onError(errorKit: ErrorKit) = when (errorKit.errorStatus) {
        ERROR_STATUS.INTERNAL_SERVER -> {
            Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show()
        }
        ERROR_STATUS.NETWORK -> {
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
        }
        else -> {
            Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            Toast.makeText(this, "Can not find it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSearching(isSearching: Boolean) {
        progressBar.visibility = if (isSearching) View.VISIBLE else View.GONE
    }

    private fun onImageList(imageList: List<Image>) {
        recyclerView.recycledViewPool.clear()
        imageAdapter.addWords(imageList)
    }
}
