package com.example.topik_corona.Activity

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.topik_corona.Auth.LoginActivity
import com.example.topik_corona.R
import com.example.topik_corona.adapter.FeatureAdapter
import com.google.android.material.button.MaterialButton

class FeatureActivity : AppCompatActivity() {

    private lateinit var featureViewPager : ViewPager
    private lateinit var featureAdapter : PagerAdapter
    private lateinit var featureDotWrapper : LinearLayout
    private lateinit var featureButton : MaterialButton
    private val context : Context = this
    private var page : Int = 0
    private var totalPage : Int = 0

    private fun refresh() {
        featureDotWrapper.removeAllViews()

        for (i in 0 until totalPage) {
            val imageParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(25, 25)
            imageParams.setMargins(8, 0, 8, 0)
            val image = ImageView(context)
            val drawable : Drawable = ContextCompat.getDrawable(context, R.drawable.circle)!!.mutate()
            @Suppress("DEPRECATION")
            drawable.setColorFilter(ContextCompat.getColor(context,
                    if (i == page) R.color.black else R.color.colorPrimary), PorterDuff.Mode.LIGHTEN)
            image.setImageDrawable(drawable)
            image.layoutParams = imageParams

            image.setOnClickListener { onClickCircle(i) }

            featureDotWrapper.addView(image)
        }
    }

    private fun onClickCircle(position : Int) {
        page = position
        featureViewPager.setCurrentItem(page, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)

        featureViewPager = findViewById(R.id.feature_view_pager)
        featureDotWrapper = findViewById(R.id.feature_dot_wrapper)
        featureButton = findViewById(R.id.feature_button)

        featureAdapter = FeatureAdapter(context)
        featureViewPager.adapter = featureAdapter
        featureViewPager.setCurrentItem(page, true)
        totalPage = featureAdapter.count
        featureButton.text = if (page == totalPage - 1) "Login" else "Lanjut"

        refresh()

        featureViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
            ) {
                page = position
            }

            override fun onPageSelected(position: Int) {
                page = position
                featureButton.text = if (page == totalPage - 1) "Login" else "Lanjut"
                refresh()
            }
        })
    }

    fun onClickFeature(view: View) {
        if (view.id == R.id.feature_button) {
            if (page == totalPage - 1) {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                page++
                featureViewPager.setCurrentItem(page, true)
            }
        }
    }
}
