package com.apx.radiance.broadcast;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Your mobile phone is connected to the charger.", Toast.LENGTH_SHORT).show();
    }
}
