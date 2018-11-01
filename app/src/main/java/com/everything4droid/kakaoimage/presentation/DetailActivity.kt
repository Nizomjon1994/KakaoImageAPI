package com.everything4droid.kakaoimage.presentation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.everything4droid.kakaoimage.R
import com.everything4droid.kakaoimage.data.entity.Image
import com.everything4droid.kakaoimage.databinding.ActivityDetailBinding
import com.google.gson.Gson

/**
 * Created by Khajiev Nizomjon on 01/11/2018.
 */
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // get Image Object as Json String
        val body = intent.getStringExtra("body")
        val image = Gson().fromJson<Image>(body, Image::class.java)

        // set binding
        binding.image = image

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}