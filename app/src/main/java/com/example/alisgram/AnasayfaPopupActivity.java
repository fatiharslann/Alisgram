package com.example.alisgram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RatingBar;
import android.widget.TextView;

public class AnasayfaPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa_popup);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String aliskanlikAdi = extras.getString("aliskanlikAdi");
        String aliskanlikKullanici = extras.getString("aliskanlikKullanici");
        float aliskanlikSeviye = extras.getFloat("aliskanlikSeviye");

        TextView txtAliskanlikAdi = findViewById(R.id.anasayfaPopupAliskanlikAdi);
        TextView txtAliskanlikKullanici = findViewById(R.id.anasayfaPopupKullaniciAdi);
        RatingBar rtSeviye = findViewById(R.id.anasayfaPopupRatingBar);

        txtAliskanlikAdi.setText(aliskanlikAdi);
        txtAliskanlikKullanici.setText(aliskanlikKullanici);
        rtSeviye.setRating(aliskanlikSeviye);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.3));
    }
}
