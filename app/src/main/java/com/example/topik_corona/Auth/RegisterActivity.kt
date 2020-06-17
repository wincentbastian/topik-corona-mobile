package com.example.topik_corona.Auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var addressEditText: TextInputEditText
    private lateinit var registerButton: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameEditText = findViewById(R.id.register_name)
        emailEditText = findViewById(R.id.register_email)
        passwordEditText = findViewById(R.id.register_password)
        confirmPasswordEditText = findViewById(R.id.register_confirm_password)
        addressEditText = findViewById(R.id.register_address)
        registerButton = findViewById(R.id.button_register)

        registerButton.setOnClickListener {
            val name = nameEditText.text
            val email = emailEditText.text
            val password = passwordEditText.text
            val confirmPassword = confirmPasswordEditText.text
            val address = addressEditText.text

            if(name!!.isNotBlank() && email!!.isNotBlank() && password!!.isNotBlank() &&
                    confirmPassword!!.isNotBlank() && address!!.isNotBlank()){
                    if ("$password" == "$confirmPassword"){
                        Toast.makeText(this, "Mendaftarkan Akun...", Toast.LENGTH_LONG).show()

                        val requestParams = HashMap<String, String>()
                        requestParams["name"] = "$name"
                        requestParams["email"] = "$email"
                        requestParams["password"] = "$password"
                        requestParams["address"] = "$address"

                        val fileRequestParams = HashMap<String, DataPart>()

                        val volleyMultipartRequest = VolleyMultipartRequest(
                        Request.Method.POST, "register.php", requestParams, fileRequestParams,
                                Response.Listener { response -> onSuccess(response) },
                                Response.ErrorListener { onError() }
                        )
                        Volley.newRequestQueue(this).add(volleyMultipartRequest)
                    }else {
                        Toast.makeText(this, "Sandi dan Konfirmasi Sandi Tidak Sama", Toast.LENGTH_LONG).show()
                    }
            }else {
                Toast.makeText(this, "Mohon Isi Semua Kolom", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun onError() {
        Toast.makeText(this, "Error Connect Server", Toast.LENGTH_LONG).show()
    }

    private fun onSuccess(response: NetworkResponse) {
        val data = String(response.data, charset(HttpHeaderParser.parseCharset(response.headers)))
        val status = JSONObject(data).getInt("status_code")

        if(status == 200){
            val userId = JSONObject(data).getString("id")
            val userName = JSONObject(data).getString("name")

            val sharedPreferences: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("id", userId)
            editor.putString("name", userName)

            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }else{
            Toast.makeText(this, "PHP Server Error", Toast.LENGTH_LONG).show()
        }
    }
}