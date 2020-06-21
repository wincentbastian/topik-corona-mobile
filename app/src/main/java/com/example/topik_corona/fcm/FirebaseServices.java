package com.example.topik_corona.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.topik_corona.MainActivity;
import com.example.topik_corona.NotificationActivity;
import com.example.topik_corona.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import me.leolin.shortcutbadger.ShortcutBadger;

import static android.content.ContentValues.TAG;

@RequiresApi(api = Build.VERSION_CODES.N)
public class FirebaseServices extends FirebaseMessagingService {

    Context context;
    SharedPreferences shared;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        String dataPayload = data.get("data");

        context = getApplicationContext();
        shared = context.getSharedPreferences("user", MODE_PRIVATE);
        Log.d(TAG, shared.getString("id",""));

            Log.d(TAG, shared.getString("id",""));
            /*
             * Cek jika notif berisi data payload
             * pengiriman data payload dapat dieksekusi secara background atau foreground
             */

            if (remoteMessage.getData().size() > 0) {
                Log.e("TAG", "Message data payload: " + remoteMessage.getData());

                try {
                    JSONObject jsonParse = new JSONObject(dataPayload);
                    showNotif(jsonParse.getString("title"), jsonParse.getString("message"), getDateNow());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            /*
             * Cek jika notif berisi data notification payload
             * hanya dieksekusi ketika aplikasi bejalan secara foreground
             * dan dapat push notif melalui UI Firebase console
             */
            if (remoteMessage.getNotification() != null) {
                Log.e("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
                showNotif(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), getDateNow());
            }
        }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("TAG", "Refreshed token: " + s);
    }

    public void showNotif(String title, String message, String date){
        context = getApplicationContext();
        ShortcutBadger.applyCount(context, 1);
        // passing data title dan message ke MainActivity
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putString("date", date);

// setup intent
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";


        if (context.getApplicationInfo().targetSdkVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, "NotifApps")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.diamond) // icon
                    .setAutoCancel(true) // menghapus notif ketika user melakukan tap pada notif
                    .setLights(200, 200, 200) // light button
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI) // set sound
                    .setOnlyAlertOnce(true) // set alert sound notif
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent); // action notif ketika di tap
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(1, notifBuilder.build());
        }
    }

    public String getDateNow() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = cal.getTime();
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        System.out.println(formatter.format(date));
        String dateTimeNow = formatter.format(date);
        return dateTimeNow;
    }


}
