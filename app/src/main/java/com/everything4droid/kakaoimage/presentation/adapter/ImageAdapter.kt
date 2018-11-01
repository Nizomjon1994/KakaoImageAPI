package com.everything4droid.kakaoimage.presentation.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.everything4droid.kakaoimage.data.entity.Image
import com.everything4droid.kakaoimage.databinding.ImageItemBinding
import java.util.ArrayList

/**
 * Created by Khajiev Nizomjon on 29/10/2018.
 */
class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    lateinit var listener: OnItemClickListener
    private var imageList: ArrayList<Image> = ArrayList()


    fun addWords(list: List<Image>) {
        imageList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        if (!imageList.isEmpty()) {
            imageList.clear()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        setOnItemClickListener(listener)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(layoutInflater, parent, false)
        return ImageHolder(binding)
    }

    override fun getItemCount(): Int = imageList.size


    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val data = imageList[position]
        holder.binding.image = data
        holder.binding.root.setOnClickListener {
            listener.onClick(it, data)
        }

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onClick(view: View, data: Image)
    }

    class ImageHolder(var binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)
}