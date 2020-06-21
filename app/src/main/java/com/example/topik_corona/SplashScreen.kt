package com.example.topik_corona

import android.content.Context
import android.content.Intent
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
import com.example.topik_corona.Activity.FeatureActivity
import com.example.topik_corona.Auth.LoginActivity
import com.example.topik_corona.Utils.DataPart
import com.example.topik_corona.Utils.VolleyMultipartRequest
import org.json.JSONObject
import java.util.*
import kotlin.concurrent.schedule

class SplashScreen : AppCompatActivity() {

    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Timer("SplashScreenTime").schedule(3000){
            val sharedPreferences: SharedPreferences = getSharedPreferences("user",
                    Context.MODE_PRIVATE)
            val intent = Intent(context, if(sharedPreferences.getString("id","") != "")
                MainActivity::class.java else FeatureActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }
}
