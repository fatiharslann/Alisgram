package com.example.alisgram;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentKesfet extends Fragment {

    FirebaseUser user;
    private DatabaseReference mDatabase, mDatabaseKategori;
    RecyclerView recyclerView;
    View view;
    LinearLayout.LayoutParams lp;
    LinearLayout dbLLayout;
    private Button kategoriButon, seninicin;
    private ModelKategori kategori;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;


    public FragmentKesfet() {
        // Required empty public constructor
    }

    public void kategoriListele(final String kategoriAdi) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final ArrayList<ModelAliskanlik> aliskanliklar = new ArrayList<ModelAliskanlik>();
                final ArrayList<ModelKullanici> kullanicilar = new ArrayList<ModelKullanici>();

                for (DataSnapshot postSnapshot : dataSnapshot.child("aliskanliklar").getChildren()) {
                    ModelAliskanlik aliskanlik = postSnapshot.getValue(ModelAliskanlik.class);
                    String aKullaniciId = aliskanlik.getAliskanlikKullaniciId();

                    final String aUserId = mCurrentUser.getUid();

                    if (!aKullaniciId.equals(aUserId)) {
                        if (kategoriAdi.equals(aliskanlik.getAliskanlikKategori())) {
                            aliskanliklar.add(aliskanlik);
                        } else if (kategoriAdi.equals("seninicin")) {
                            aliskanliklar.add(aliskanlik);
                        }
                    }

                }

                for (DataSnapshot postSnapshot : dataSnapshot.child("Kullanicilar").getChildren()) {
                    ModelKullanici kullanici = postSnapshot.getValue(ModelKullanici.class);
                    kullanicilar.add(kullanici);
                }

                KesfetAdapter productAdapter = new KesfetAdapter(view.getContext(), aliskanliklar, kullanicilar);
                recyclerView.setAdapter(productAdapter);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Firebase ERROR", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_fragment_kesfet, container, false);
        dbLLayout = view.findViewById(R.id.dbLLayout);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = view.findViewById(R.id.kesfetItemList);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        kategoriListele("seninicin");
        seninicin = view.findViewById(R.id.seninicin);
        seninicin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kategoriListele("seninicin");
            }
        });


        mDatabaseKategori = FirebaseDatabase.getInstance().getReference("kategoriler");

        mDatabaseKategori.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    kategori = postSnapshot.getValue(ModelKategori.class);
                    if (kategori.getUstKategoriAdi().equals("Yok")) {
                        kategoriButon = new Button(view.getContext());

                        kategoriButon.setId(kategori.getKategoriId());
                        final int kategoriButonId = kategoriButon.getId();
                        kategoriButon.setText(kategori.getKategoriAdi());
                        final String kategoriAdi = String.valueOf(kategoriButon.getText());
                        Drawable top = getResources().getDrawable(R.drawable.search);
                        kategoriButon.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);

                        kategoriButon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                kategoriListele(kategoriAdi);
                            }
                        });

                        dbLLayout.addView(kategoriButon);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Firebase ERROR", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


        return view;
    }

}

