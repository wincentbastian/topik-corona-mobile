package com.example.topik_corona;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.topik_corona.Auth.LoginActivity;
import com.example.topik_corona.Utils.Constants;
import com.example.topik_corona.model.Gejala;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TextView tvName;
    private TextView tvEmail;
    private ImageView ivSex;
    private ImageView ivAvatar;
    private Button btnLogout;
    private Button btnLogin;
    private ImageButton ibEdit;
    private Context mContext;
    private Button btnUpdate;
    private SharedPreferences shared;
    private int EDIT_PROFILE = 1;
    private ArrayList<Gejala> gejalaArrayList = new ArrayList<>();
    private boolean[] checkedItems;
    private   String[] listItems;
    private String id;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Profile");

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mContext = getContext();
        shared = mContext.getSharedPreferences("user", MODE_PRIVATE);

        String name = shared.getString("name","");
        String email = shared.getString("email","");
        String gender = shared.getString("gender","");

        Log.d(TAG, name + email + gender);
        Log.d("TANGGAL", shared.getString("tanggal", ""));

        tvName = v.findViewById(R.id.tv_name);
        tvEmail = v.findViewById(R.id.tv_email);
        ivSex = v.findViewById(R.id.iv_sex);
        ivAvatar = v.findViewById(R.id.iv_avatar);
        btnLogout = v.findViewById(R.id.btn_log_out);
        btnLogin = v.findViewById(R.id.btn_log_in);
        btnUpdate = v.findViewById(R.id.btn_update);
        ibEdit = v.findViewById(R.id.ib_edit);


        tvName.setText(name);
        tvEmail.setText(email);

        if (gender.equals("P")){
            ivSex.setImageResource(R.drawable.male_sex);
            ivAvatar.setImageResource(R.drawable.male);
        }
        else if(gender.equals("W")) {
            ivSex.setImageResource(R.drawable.female_sex);
            ivAvatar.setImageResource(R.drawable.female);
        }
        else {
            ivAvatar.setImageResource(R.drawable.guest);
            tvName.setText("Guest");
            tvEmail.setVisibility(View.GONE);
            ivSex.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            ibEdit.setVisibility(View.GONE);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tvName.getText().toString();
                String email = tvEmail.getText().toString();

                Intent intent = new Intent(mContext, EditProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                startActivityForResult(intent, EDIT_PROFILE);
            }
        });

        listItems = getResources().getStringArray(R.array.gejala_array);

        checkedItems = new boolean[listItems.length];

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                RequestQueue queue = Volley.newRequestQueue(mContext);
//                String url = Constants.BASE_URL +"get-gejala.php";
//
//                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                        new Response.Listener<JSONObject>()
//                        {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    JSONArray gejalaArray = response.getJSONArray("gejala");
//
//                                    for (int i = 0; i< gejalaArray.length(); i++){
//                                        Gejala gejala = new Gejala();
//                                        JSONObject jsonObject = gejalaArray.getJSONObject(i);
//                                        gejala.setId(String.valueOf(jsonObject.get("id")));
//                                        gejala.setGejala(String.valueOf(jsonObject.get("gejala")));
//                                        gejala.setKategori_gejala(String.valueOf(jsonObject.get("kategori_gejala_id")));
//                                        gejalaArrayList.add(gejala);
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                final  String[] listItems = new String[13];
//
//                                for (int i = 0; i < gejalaArrayList.size(); i++){
//                                    listItems[i] = gejalaArrayList.get(i).getGejala();
//                                }
//
//                                checkedItems = new boolean[listItems.length];
//                                final ArrayList<Integer> mUserItems = new ArrayList<>();
//
//                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
//                                mBuilder.setTitle("Masukan Gejala");
//                                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
//                                        if (isChecked){
//                                            if(! mUserItems.contains(position)){
//                                                mUserItems.add(position);
//                                            }
//                                            else {
//                                                mUserItems.remove(position);
//                                            }
//                                        }
//
//                                    }
//                                });
//
//                                mBuilder.setCancelable(false);
//                                mBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        String item = "";
//                                        for (int i = 0 ; i < mUserItems.size(); i++){
//                                            item = item + listItems[mUserItems.get(i)];
//                                            if (i != mUserItems.size() -1){
//                                                item = item +", ";
//                                            }
//                                        }
//                                        Log.d(TAG, item);
//                                    }
//                                });
//
//                                mBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//
//                                mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        for(int i= 0 ; i < checkedItems.length; i++){
//                                            checkedItems[i] = false;
//                                            mUserItems.clear();
//
//                                        }
//                                    }
//                                });
//
//                                AlertDialog mDialog = mBuilder.create();
//                                mDialog.show();
//                            }
//                        },
//                        new Response.ErrorListener()
//                        {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.d("Error.Response", error.getMessage());
//                            }
//                        }
//                );
//                queue.add(getRequest);

                if (!shared.getString("tanggal", "").equals(getDateNow()) ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    final String[] gejalaArray = getResources().getStringArray(R.array.gejala_array);
                    final boolean[] checkedGejala = new boolean[]{
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                    };
                    final List<String> gejalaList = Arrays.asList(gejalaArray);
                    builder.setMultiChoiceItems(gejalaArray, checkedGejala, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            checkedGejala[which] = isChecked;
                            String currentItem = gejalaList.get(which);
//                            Toast.makeText(mContext, currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final List<String> gejalaId = new ArrayList<>();
                            for (int i = 0; i < checkedGejala.length; i++) {
                                boolean checked = checkedGejala[i];
                                if (checked) {
                                    gejalaId.add(String.valueOf(i + 1));
                                }
                            }
                            Log.d(TAG, String.valueOf(gejalaId));
//                            Toast.makeText(mContext, String.valueOf(gejalaId), Toast.LENGTH_LONG).show();

                            RequestQueue queue = Volley.newRequestQueue(mContext);
                            String url = Constants.BASE_URL + "insert-gejala.php";

                            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // menampilkan respone
                                            Log.d("Response", response);
                                            SharedPreferences sharedPreferences = mContext.getSharedPreferences("user", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("tanggal", getDateNow());
                                            editor.commit();
                                            Toast.makeText(mContext, "Data berhasil diperbarui", Toast.LENGTH_LONG).show();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // error
                                            Log.d("Error.Response", String.valueOf(error.getMessage()));
                                            Toast.makeText(mContext, "Data gagal diperbarui", Toast.LENGTH_LONG).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {         // Menambahkan parameters post
                                    id = shared.getString("id", "");
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("userId", id);
                                    params.put("gejalaId", gejalaId.toString());

                                    return params;
                                }
                            };
                            queue.add(postRequest);
                        }
                    });

                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            else {
                    Toast.makeText(mContext, "Anda telah mengupdate gejala pada hari ini", Toast.LENGTH_LONG).show();
                }
            }

        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FirebaseInstanceId.getInstance().deleteInstanceId();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Log.e("FCM","NOT LOGGED");

                SharedPreferences.Editor editor  = shared.edit();
                editor.putString("id","");
                editor.putString("name","");
                editor.putString("email","");
                editor.putString("gender","");
                editor.putString("address", "");
                editor.apply();
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE) {
            if(resultCode == Activity.RESULT_OK){
                String name =data.getStringExtra("name");
                tvName.setText(name);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public String getDateNow() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = cal.getTime();
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        System.out.println(formatter.format(date));
        String dateTimeNow = formatter.format(date);
        return dateTimeNow;
    }

}
