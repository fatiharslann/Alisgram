package com.example.alisgram;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AnasayfaPopupActivity extends AppCompatActivity {

    ImageButton btnEkle;

    private DatabaseReference mDatabase;
    FirebaseUser user;
    String aliskanlikId,tarih,key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa_popup);
        tanimla();

        btnEkle = (ImageButton) findViewById(R.id.btnAnasayfaPopupEkle);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        aliskanlikId = extras.getString("aliskanlikId");
        final String aliskanlikAdi = extras.getString("aliskanlikAdi");
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

        getWindow().setLayout((int)(width*.9),(int)(height*.4));

        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query query = mDatabase.orderByChild("aliskanlikId").equalTo(aliskanlikId);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            ModelAliskanlik aliskanlik=postSnapshot.getValue(ModelAliskanlik.class);

                            aliskanlik.setAliskanlikId(key);
                            aliskanlik.setAliskanlikGizlilik(false);
                            aliskanlik.setAliskanlikKullaniciId(user.getUid());
                            aliskanlik.setAliskanlikOlusturulmaTarihi(tarih);
                            aliskanlik.setAliskanlikSeviye(0);
                            aliskanlik.setAliskanlikDurum(0);

                            mDatabase.child(key).setValue(aliskanlik);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("Firebase ERROR", "loadPost:onCancelled", databaseError.toException());
                    }
                });
            }
        });

    }

    private void tanimla() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        tarih = df.format(currentTime);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("aliskanliklar");
        key = mDatabase.push().getKey();

    }
}
