package com.example.topik_corona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {
    private TextView tvTittle;
    private TextView tvMessage;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Bundle bundle = getIntent().getExtras();
        tvTittle = findViewById(R.id.tv_title);
        tvMessage = findViewById(R.id.tv_message);
        tvDate = findViewById(R.id.tv_date);

        if(bundle != null)  {
            tvTittle.setText(bundle.getString("title"));
            tvMessage.setText(bundle.getString("message"));
            tvDate.setText(bundle.getString("date"));
        }
    }
}