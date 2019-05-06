package com.example.alisgram;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BildirimServisi extends IntentService {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private ArrayList<String> saatler;
    private ArrayList<String> gunler;
    private NotificationManagerCompat notificationManagerCompat;
    private RemoteViews remoteViews;


    public BildirimServisi() {
        super("BildirimServisi");
        Log.d("TAGIM","servis");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mAuth = FirebaseAuth.getInstance();
        saatler = new ArrayList<String>();
        gunler = new ArrayList<String>();
        if(mAuth != null){
            mDatabase = FirebaseDatabase.getInstance();
            myRef = mDatabase.getReference("aliskanliklar");
           //notification_build("-LcbHcRFhr5FPi_oCQCM");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(mAuth.getCurrentUser() != null){
                        for (DataSnapshot aliskanlik_snapshot : dataSnapshot.getChildren()){
                            ModelAliskanlik aliskanlik = aliskanlik_snapshot.getValue(ModelAliskanlik.class);
                            if(mAuth.getCurrentUser().getUid().equals(aliskanlik.getAliskanlikKullaniciId())){
                                saatler=aliskanlik.getAliskanlikSaatler();
                                gunler=aliskanlik.getAliskanlikGunler();
                                String uid=aliskanlik.getAliskanlikId();
                                bildirim_yolla(gunler,saatler,uid);
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });//*/
        }
    }
    private boolean bildirim_yolla(ArrayList<String> gunler, ArrayList<String> saatler,String uid) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        String gun="",saat="";

        SimpleDateFormat format = new SimpleDateFormat("HH", Locale.ENGLISH);
        String hour = format.format(new Date());

        SimpleDateFormat dakika_format = new SimpleDateFormat("mm", Locale.ENGLISH);
        String dakika = dakika_format.format(new Date());

        ArrayList<String> week = new ArrayList<>();
        ArrayList <String> hafta = new ArrayList<>();

        hafta.add("Pazartesi");hafta.add("Salı");hafta.add("Çarşamba");hafta.add("Perşembe");hafta.add("Cuma");hafta.add("Cumartesi");hafta.add("Pazar");
        week.add("Monday");week.add("Tuesday");week.add("Wednesday");week.add("Thursday");week.add("Friday");week.add("Saturday");week.add("Sunday");

        if(week.contains(dayOfTheWeek)){
            gun = hafta.get(week.indexOf(dayOfTheWeek));
        }

        if(Integer.valueOf(dakika)<=9){
            saat = String.valueOf(hour)+":0"+String.valueOf(dakika);
        }
        else{
            saat = String.valueOf(hour)+":"+String.valueOf(dakika);
        }
        /*int alt_dakika = Integer.valueOf(dakika);
        if(alt_dakika>=5){
            alt_dakika=alt_dakika-5;
        }
        int ust_dakika = Integer.valueOf(dakika);
        if(ust_dakika<=55){
            ust_dakika=ust_dakika+5;
        }
        String str_altdakika= String.valueOf(alt_dakika);
        String str_ustdakika= String.valueOf(ust_dakika);
*/

        for(String aliskanlik_gun : gunler){
            if(aliskanlik_gun.equals(gun)){
                for(String aliskanlik_saat : saatler){
                    if(aliskanlik_saat.equals(saat)){
                        notification_build(uid);
                    }
                }
            }
        }


        return false;
    }
    private void notification_build(String uid){

        FirebaseDatabase.getInstance().getReference("aliskanliklar").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ModelAliskanlik aliskanlik = dataSnapshot.getValue(ModelAliskanlik.class);
                int progress = aliskanlik.getAliskanlikSeviye();
                Intent evet_intent = new Intent(getApplicationContext(),MainActivity.class);
                evet_intent.putExtra("evet",progress);
                evet_intent.putExtra("uid",aliskanlik.getAliskanlikId());
                PendingIntent evet_action = PendingIntent.getActivity(getApplicationContext(),
                        1,evet_intent,0);

                Intent hayir_intent = new Intent(getApplicationContext(),MainActivity.class);
                hayir_intent.putExtra("hayir",String.valueOf(progress));
                hayir_intent.putExtra("uid",aliskanlik.getAliskanlikId());
                PendingIntent hayir_action = PendingIntent.getActivity(getApplicationContext(),
                        2,hayir_intent,0);

                notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.logo);

                Notification notification = new NotificationCompat.Builder(
                        getApplicationContext(),
                        Alisgram.KANAL_1)
                        .setSmallIcon(R.drawable.logo,1)
                        .setContentTitle(aliskanlik.getAliskanlikEtiket())
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setColor(Color.BLUE)
                        .setLargeIcon(largeIcon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(aliskanlik.getAliskanlikDetay()+" \n\n" +
                                "Alışkanlığını tekrarlama zamanı geldi. Alışkanlığını gerçekleştirdin mi?"))
                        .addAction(R.drawable.ic_open_mainactivity_action,"Evet",evet_action)
                        .addAction(R.drawable.ic_show_toast_action,"Hayır",hayir_action)
                        .setAutoCancel(true)
                        .build();

                notificationManagerCompat.notify(1,notification);//*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
      /* Intent start_intent = new Intent(this,MainActivity.class);
       PendingIntent contentIntent = PendingIntent.getActivity(this,
               0,start_intent,0);

        Intent evet_intent = new Intent(this,BootReceiver.class);
        evet_intent.putExtra("evet",uid);
        PendingIntent evet_action = PendingIntent.getBroadcast(this,
                1,evet_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent hayir_intent = new Intent(this,BootReceiver.class);
        hayir_intent.putExtra("hayir",uid);
        PendingIntent hayir_action = PendingIntent.getBroadcast(this,
                2,hayir_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.logo);

           Notification notification = new NotificationCompat.Builder(
                   getApplicationContext(),
                   Alisgram.KANAL_1)
                   .setSmallIcon(R.drawable.logo,1)
                   .setContentTitle("Alışkanlık Hatırlatıcı")
                   .setPriority(NotificationCompat.PRIORITY_LOW)
                   .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                   .setColor(Color.BLUE)
                   .setLargeIcon(largeIcon)
                   .setStyle(new NotificationCompat.BigTextStyle().bigText("Deneme"))
                   .addAction(R.drawable.ic_open_mainactivity_action,"Evet",evet_action)
                   .addAction(R.drawable.ic_show_toast_action,"Hayır",hayir_action)
                   .setAutoCancel(true)
                   .build();

           notificationManagerCompat.notify(1,notification);//*/
           //notificationManagerCompat.cancel(1);

           /*PugNotification.with(this)
                   .load()
                   .title("Alışkanlık")
                   .message("Mesaj kısmı")
                   .smallIcon(R.drawable.pugnotification_ic_launcher)
                   .largeIcon(R.drawable.pugnotification_ic_launcher)
                   .flags(Notification.DEFAULT_ALL)
                   .simple()
                   .build();//*/



    }

}
