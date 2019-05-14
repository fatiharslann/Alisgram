package com.example.alisgram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilBaska extends AppCompatActivity {

    private String key,uid;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef,takip_ref;
    private CircleImageView image;
    private TextView profil_baskasi_isim,profil_baskasi_bio;
    private ViewPager profil_baska_view;
    private TabLayout profil_tabs;
    private ImageView star;
    public static  String takip_id,deneme;
    private boolean takipte = false;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_baska);
        key = getIntent().getStringExtra("uid");
        deneme = key;
        takip_ref = FirebaseDatabase.getInstance().getReference("Takip");
        takip_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean bayrak = false;
                for(DataSnapshot postShot : dataSnapshot.getChildren()){
                    uid = FirebaseHelper.getCurrentKullanici().getUuid();
                    Takip takip = postShot.getValue(Takip.class);
                    if(takip.getFrom().equals(uid) && takip.getTo().equals(key)){
                        bayrak = true;
                    }
                   // Log.d("TAGIM",takip.getFrom()+" "+takip.getTo()+" uid " +uid+" to "+key);
                }
                if(bayrak){
                    star.setBackgroundResource(R.drawable.ic_star_blue_fill_24dp);
                    //Log.d("TAGIM","deneme");
                }else {
                    star.setBackgroundResource(R.drawable.ic_star_border_blue_24dp);
                    //Log.d("TAGIM","deneme2");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
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

        star = findViewById(R.id.star_image);

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid =  FirebaseHelper.getCurrentKullanici().getUuid();
                takip_ref = FirebaseDatabase.getInstance().getReference("Takip");
                takip_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        takipte = false;
                        for(DataSnapshot postShot : dataSnapshot.getChildren()){
                            Takip takip = postShot.getValue(Takip.class);
                            if(takip.getFrom().equals(uid) && takip.getTo().equals(key)){
                                takipte = true;
                                takip_id = takip.getTakip_id();
                                break;
                            }
                        }
                        if(takipte){
                            Log.d("TAGIM","true");
                            takip_ref.child(takip_id).removeValue();
                            star.setBackgroundResource(R.drawable.ic_star_border_blue_24dp);
                        }else {
                            FirebaseHelper.takipEt(uid,key);
                            star.setBackgroundResource(R.drawable.ic_star_blue_fill_24dp);
                            Log.d("TAGIM","false");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

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
