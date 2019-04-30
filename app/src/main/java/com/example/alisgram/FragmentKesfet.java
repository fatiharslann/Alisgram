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
    private DatabaseReference mDatabase,mDatabaseKategori;
    RecyclerView recyclerView;
    View view;
    LinearLayout.LayoutParams lp;
    LinearLayout dbLLayout;
    private Button kategoriButon,seninicin;
    private ModelKategori kategori;


    public FragmentKesfet() {
        // Required empty public constructor
    }

    public void kategoriListele(final String kategoriAdi){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<ModelAliskanlik> aliskanliklar = new ArrayList<ModelAliskanlik>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ModelAliskanlik aliskanlik = postSnapshot.getValue(ModelAliskanlik.class);
                    if(kategoriAdi.equals(aliskanlik.getAliskanlikKategori())) {
                        aliskanliklar.add(aliskanlik);
                    }
                    else if(kategoriAdi.equals("seninicin")){
                        aliskanliklar.add(aliskanlik);
                    }
                }
                KesfetAdapter productAdapter = new KesfetAdapter(view.getContext(), aliskanliklar);
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
        dbLLayout=view.findViewById(R.id.dbLLayout);
        lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("aliskanliklar");
        recyclerView = view.findViewById(R.id.kesfetItemList);

        kategoriListele("seninicin");
        seninicin=view.findViewById(R.id.seninicin);
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
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    kategori = postSnapshot.getValue(ModelKategori.class);
                    if (kategori.getUstKategoriAdi().equals("Yok")){
                        kategoriButon=new Button(view.getContext());

                        kategoriButon.setId(kategori.getKategoriId());
                        final int kategoriButonId= kategoriButon.getId();
                        kategoriButon.setText(kategori.getKategoriAdi());
                        final String kategoriAdi= String.valueOf(kategoriButon.getText());
                        Drawable top = getResources().getDrawable(R.drawable.search);
                        kategoriButon.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);

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

