package com.example.topik_corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.topik_corona.Utils.Constants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private SharedPreferences shared;
    private TextInputEditText etEmail;
    private TextInputEditText etName;
    private TextInputEditText etAddress;
    private ImageView ivProfile;
    private MaterialButton btnUpdate;
    private Context mContext;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mContext = getApplicationContext();
        shared = this.getSharedPreferences("user", MODE_PRIVATE);
        String name = shared.getString("name","");
        String email = shared.getString("email","");
        String gender = shared.getString("gender","");
        String address = shared.getString("address","");

        etEmail = findViewById(R.id.et_email);
        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        ivProfile = findViewById(R.id.iv_profile);
        btnUpdate = findViewById(R.id.btn_update);

        etEmail.setText(email);
        etName.setText(name);
        etAddress.setText(address);

        if (gender.equals("P")){
            ivProfile.setImageResource(R.drawable.male);
        }
        else {
            ivProfile.setImageResource(R.drawable.female);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String address = etAddress.getText().toString();
                id = shared.getString("id","");

                Log.d("name", name.toString());
                Log.d("email", address.toString());

                if (!name.isEmpty() && !address.isEmpty()){
                    Toast.makeText(mContext, "Sedang memperbarui...", Toast.LENGTH_LONG).show();

                    RequestQueue queue = Volley.newRequestQueue(mContext);
                    String url = Constants.BASE_URL +"edit-profile.php";

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // menampilkan respone
                                    Log.d("Response", response);
                                    Toast.makeText(mContext, "Data berhasil diperbarui", Toast.LENGTH_LONG).show();
                                    SharedPreferences.Editor editor = shared.edit();
                                    editor.putString("name", name);
                                    editor.putString("address", address);
                                    editor.apply();
                                    Intent intent = new Intent();
                                    intent.putExtra("name", name);
                                    intent.putExtra("address", address);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();

                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Log.d("Error.Response", error.getMessage());
                                    Toast.makeText(mContext, "Data gagal diperbarui", Toast.LENGTH_LONG).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {         // Menambahkan parameters post
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("id", id);
                            params.put("name", name);
                            params.put("address", address);

                            return params;
                        }
                    };
                    queue.add(postRequest);

                }
                else {
                    Toast.makeText(getApplicationContext(),"Mohon isi semua kolom", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}