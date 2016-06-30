package com.frogoutofwell.yullfrogapplication;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Tacademy on 2016-06-08.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService{
    @Override
    public void onTokenRefresh() {
        Log.e("refresh","refresh");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
