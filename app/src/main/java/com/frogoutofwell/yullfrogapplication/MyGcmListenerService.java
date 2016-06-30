package com.frogoutofwell.yullfrogapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.frogoutofwell.yullfrogapplication.login.MyResult;
import com.frogoutofwell.yullfrogapplication.login.User;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.google.android.gms.gcm.GcmListenerService;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Tacademy on 2016-06-08.
 */
public class MyGcmListenerService extends GcmListenerService{

    private static final String TAG = "MyGcmListenerService";

    public static final String ACTION_CHAT = "com.begentgroup.miniapplication.action.chat";
    public static final String EXTRA_SENDER_ID = "senderid";
    public static final String EXTRA_RESULT = "result";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String type = data.getString("type");
        String message = data.getString("message");
        Log.e("message",message+", "+type);

        sendNotification(message, type);

    }

    private void sendNotification(String message, String type) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_TYPE, type);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setTicker("GCM message")
                .setSmallIcon(R.drawable.froglogo)
                .setContentTitle("우물밖개구리")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
