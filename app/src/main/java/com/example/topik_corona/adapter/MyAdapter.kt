package com.example.topik_corona.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.topik_corona.Activity.NewsActivity
import com.example.topik_corona.R
import com.example.topik_corona.Utils.MyHolder
import com.example.topik_corona.model.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_news.view.*

class MyAdapter(private val dataList: MutableList<Data>) : RecyclerView.Adapter<MyHolder>() {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        context = parent.context
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.layout_news, parent, false))
    }

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data = dataList[position]


        val titleTextView = holder.itemView.title_news
        val bannerImageView = holder.itemView.image_news

        val titleRecylerView = data.title

        val countTitle = titleRecylerView.length

        if (countTitle >= 15){
            val editedTitle = titleRecylerView.substring(0..14)
            titleTextView.text = editedTitle + "..."
        } else {
            titleTextView.text = titleRecylerView
        }

        val newsId = data.id.toString()

        val imageRecyclerView = data.image

        Picasso.get().load(imageRecyclerView).into(bannerImageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewsActivity::class.java)
            intent.putExtra("newsId", newsId)
            context.startActivity(intent)
        }
    }
}