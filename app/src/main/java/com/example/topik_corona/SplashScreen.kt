package com.example.topik_corona

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.Volley
import com.example.topik_corona.Utils.DataPart
import com.example.topik_corona.Utils.VolleyMultipartRequest
import org.json.JSONObject
import java.util.HashMap

class SplashScreen : AppCompatActivity() {

    private lateinit var percobaan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        percobaan = findViewById(R.id.percobaan)

        val requestParams = HashMap<String, String>()
        val fileRequestParams = HashMap<String, DataPart>()


        //creating volley string request
        val volleyMultipartRequest = VolleyMultipartRequest(
                Request.Method.GET, "testing.php", requestParams, fileRequestParams,
                Response.Listener { response -> onSuccess(response) },
                Response.ErrorListener { onError() }
        )
        Volley.newRequestQueue(this).add(volleyMultipartRequest)
    }

    private fun onError() {
        Toast.makeText(this, "Gagal Connect Database", Toast.LENGTH_LONG).show()
    }

    private fun onSuccess(response: NetworkResponse) {
        val value = String(response.data, charset(HttpHeaderParser.parseCharset(response.headers)))
        percobaan.setText(value)
    }
}
