package com.example.topik_corona.Auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.Volley
import com.example.topik_corona.MainActivity
import com.example.topik_corona.R
import com.example.topik_corona.Utils.DataPart
import com.example.topik_corona.Utils.VolleyMultipartRequest
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import java.util.HashMap
import java.util.HashSet

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var toMainButton: MaterialButton
    private lateinit var registerButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.login_email)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.button_login)
        toMainButton = findViewById(R.id.to_main_button)
        registerButton = findViewById(R.id.to_register_button)

        loginButton.setOnClickListener {

            val email = emailEditText.text
            val password = passwordEditText.text
            if (email!!.isNotBlank() && password!!.isNotBlank()){

                Toast.makeText(this, "Sedang Login...", Toast.LENGTH_LONG).show()

                val requestParams = HashMap<String, String>()
                requestParams["email"] = "$email"
                requestParams["password"] = "$password"

                val fileRequestParams = HashMap<String, DataPart>()

                val volleyMultipartRequest =  VolleyMultipartRequest(
                        Request.Method.POST, "login.php", requestParams, fileRequestParams,
                        Response.Listener { response -> onSuccess(response) },
                        Response.ErrorListener { onError() }
                )
                Volley.newRequestQueue(this).add(volleyMultipartRequest)

            }else{
                Toast.makeText(this, "Mohon Isi Semua Kolom", Toast.LENGTH_LONG).show()
            }
        }

        toMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }

    }

    private fun onError() {
        Toast.makeText(this, "Error Connect Server", Toast.LENGTH_LONG).show()
    }

    private fun onSuccess(response: NetworkResponse) {
        val data = String(response.data, charset(HttpHeaderParser.parseCharset(response.headers)))
        val status_code = JSONObject(data).getInt("status_code")
        val userId = JSONObject(data).getString("id")
        val userName = JSONObject(data).getString("name")


        if (status_code == 200){
            val sharedPreferences: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("id", userId)
            editor.putString("name", userName)

            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }else if(status_code == 100){
            Toast.makeText(this, "Email atau Kata Sandi Salah", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "PHP Server Error", Toast.LENGTH_LONG).show()
        }
    }
}
