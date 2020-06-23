package com.example.topik_corona.Activity

import android.annotation.SuppressLint
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.Volley
import com.example.topik_corona.R
import com.example.topik_corona.Utils.DataPart
import com.example.topik_corona.Utils.VolleyMultipartRequest
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap

class NewsActivity : AppCompatActivity() {

    private lateinit var titleNewsPageTextView: TextView
    private lateinit var dateNewsPageTextView: TextView
    private lateinit var imageNewsImageView: ImageView
    private lateinit var descriptionTextView: TextView
    private lateinit var loading: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        titleNewsPageTextView = findViewById(R.id.title_news_page)
        dateNewsPageTextView = findViewById(R.id.date_news_page)
        imageNewsImageView = findViewById(R.id.image_news_page)
        descriptionTextView = findViewById(R.id.description_news_page)
        loading = findViewById(R.id.loading_news_page)

        val newsId = intent.getStringExtra("newsId")


        val requestParams = HashMap<String, String>()
        val fileRequestParams = HashMap<String, DataPart>()

        val volleyMultipartRequest =  VolleyMultipartRequest(
                Request.Method.GET, "showNewsPage.php?news_id=$newsId", requestParams, fileRequestParams,
                Response.Listener { response -> onSuccess(response) },
                Response.ErrorListener { onError() }
        )
        Volley.newRequestQueue(this).add(volleyMultipartRequest)



    }

    private fun onError() {
        Toast.makeText(this, "Failed to connect Server", Toast.LENGTH_LONG).show()
    }


    private fun onSuccess(response: NetworkResponse) {
        val data = String(response.data, charset(HttpHeaderParser.parseCharset(response.headers)))
        val status = JSONObject(data).getInt("status_code")
        val message = JSONObject(data).getString("message")
        loading.visibility = View.GONE

        if(status == 200){
            val titlePage = JSONObject(data).getJSONObject("data").getString("title")
            val datePage = JSONObject(data).getJSONObject("data").getString("date")
            val imagePage = JSONObject(data).getJSONObject("data").getString("image")
            val description = JSONObject(data).getJSONObject("data").getString("description")





            titleNewsPageTextView.text = titlePage
            dateNewsPageTextView.text = "Diterbitkan: " + datePage
            Picasso.get().load(imagePage).fit().into(imageNewsImageView, object : Callback{
                override fun onSuccess() {
                    Log.e("PICASSO", "SUCCESS")
                }

                override fun onError(e: Exception) {
                    e.printStackTrace()
                }

            })
            descriptionTextView.text = description





        }else if (status == 400){
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

    }
}