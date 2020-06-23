package com.example.topik_corona.adapter

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
import kotlinx.android.synthetic.main.layout_banner_news.view.*

class BannerAdapter(private val bannerDataList: MutableList<Data>): RecyclerView.Adapter<MyHolder>() {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        context = parent.context
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.layout_banner_news, parent, false))
    }

    override fun getItemCount(): Int = bannerDataList.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data = bannerDataList[position]

        val titleBanner = holder.itemView.banner_title
        val imageBanner = holder.itemView.banner_image

        val dataTitle = data.title

        val countTitle = dataTitle.length

        if (countTitle >= 20){
            val editedTitle = dataTitle.substring(0..19)
            titleBanner.text = editedTitle + "..."
        } else {
            titleBanner.text = dataTitle
        }

        val newsId = data.id.toString()

        val dataImage = data.image

        titleBanner.text = dataTitle

        Picasso.get().load(dataImage).fit().into(imageBanner)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewsActivity::class.java)
            intent.putExtra("newsId", newsId)
            context.startActivity(intent)
        }
    }
}