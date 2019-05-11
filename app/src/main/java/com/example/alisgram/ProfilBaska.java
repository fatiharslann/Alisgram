package com.example.alisgram;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilBaska extends AppCompatActivity {

    private String key;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private CircleImageView image;
    private TextView profil_baskasi_isim,profil_baskasi_bio;
    private ViewPager profil_baska_view;
    private TabLayout profil_tabs;
    public static String deneme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_baska);
        key = getIntent().getStringExtra("uid");
        deneme = key;
        Log.d("TAGIM",key);
        tanimla();
    }

    private void tanimla() {
        //Firebase
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("Kullanicilar");
        myRef.keepSynced(true);
        //component
        image = findViewById(R.id.profil_baskasi_img);
        profil_baskasi_bio = findViewById(R.id.profil_baskasi_bio);
        profil_baskasi_isim = findViewById(R.id.profil_baskasi_isim);


        profil_baska_view = findViewById(R.id.profil_baska_view);
        setupViewPager(profil_baska_view);

        profil_tabs = findViewById(R.id.profil_tabs);
        profil_tabs.setupWithViewPager(profil_baska_view);

        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelKullanici kullanici = dataSnapshot.getValue(ModelKullanici.class);
                profil_baskasi_isim.setText(kullanici.getIsim()+ " "+ kullanici.getSoyisim());
                profil_baskasi_bio.setText(kullanici.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ProfilViewPager adapter = new ProfilViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

}
