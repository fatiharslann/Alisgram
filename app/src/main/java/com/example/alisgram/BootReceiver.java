package com.example.alisgram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

public class BootReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    private String evet,hayir,uid;
    NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onReceive(Context context, Intent intent) {

        /*notificationManagerCompat = NotificationManagerCompat.from(context);

        evet = intent.getStringExtra("evet");
        if(evet != null){
           // Toast.makeText(context, "Alışkanlık Seviyesi Arttırıldı.", Toast.LENGTH_SHORT).show();
            uid = intent.getStringExtra("uid");
            notificationManagerCompat.cancel(1);

        }
        notificationManagerCompat = NotificationManagerCompat.from(context);
        hayir = intent.getStringExtra("hayir");
        if(hayir != null){
            Toast.makeText(context, ":(((", Toast.LENGTH_SHORT).show();
            notificationManagerCompat.cancel(2);
        }//*/

        Intent service_intent = new Intent(context,BildirimServisi.class);
        context.startService(service_intent);//*/


    }
}
