package com.example.topik_corona.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.topik_corona.R

class FeatureAdapter(paramContext: Context) : PagerAdapter() {
    private val context: Context = paramContext
    private val titles: Array<String> = arrayOf(
           "Peta Persebaran",
           "Informasi Yang Detail",
           "Update Berita Terkini"
    )

    private val images: Array<Int> = arrayOf(
            R.drawable.ic_feature_map,
            R.drawable.ic_feature_information,
            R.drawable.ic_feature_news
    )

    private val description: Array<String> = arrayOf(
            "Penjelasan peta persebaran",
            "Penjelasan informasi yang detail",
            "Penjelasan update Berita Terkini"
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ==`object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(R.layout.layout_feature, container, false)

        val featureTitle : TextView = view.findViewById(R.id.feature_title)
        val featureImage : ImageView = view.findViewById(R.id.feature_image)
        val featureDescription : TextView = view.findViewById(R.id.feature_description)

        featureTitle.text = titles[position]
        featureImage.setImageResource(images[position])
        featureDescription.text = description[position]

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        return container.removeView(`object` as ConstraintLayout)
    }

    override fun getCount(): Int {
        return titles.size
    }
}