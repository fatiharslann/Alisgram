package com.example.alisgram;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Alisgram extends Application {

    public static final String KANAL_1="KANAL1";

    @Override
    public void onCreate() {
        super.onCreate();
        tanimla();
    }
    private void tanimla() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel kanal1 = new NotificationChannel(
                    KANAL_1,
                    "Alışkanlık Hatırlatıcı",
                    NotificationManager.IMPORTANCE_LOW
            );
            kanal1.setDescription("Alışkanlık Kanalı");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(kanal1);

        }


    }

}
