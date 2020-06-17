package com.example.topik_corona;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


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

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Profile");

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        int flag = 0;
        mContext = getContext();

        tvName = v.findViewById(R.id.tv_name);
        tvEmail = v.findViewById(R.id.tv_email);
        ivSex = v.findViewById(R.id.iv_sex);
        ivAvatar = v.findViewById(R.id.iv_avatar);
        btnLogout = v.findViewById(R.id.btn_log_out);
        btnLogin = v.findViewById(R.id.btn_log_in);
        btnUpdate = v.findViewById(R.id.btn_update);
        ibEdit = v.findViewById(R.id.ib_edit);

        if(flag == 1){
            tvName.setText("Guest");
            tvEmail.setVisibility(View.GONE);
            ivSex.setVisibility(View.GONE);
            ivAvatar.setImageResource(R.drawable.guest);
            btnLogout.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            ibEdit.setVisibility(View.GONE);
        }

        ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tvName.getText().toString();
                String email = tvEmail.getText().toString();

                Intent intent = new Intent(mContext, EditProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FormStatusActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
