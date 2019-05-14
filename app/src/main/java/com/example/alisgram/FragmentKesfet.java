package com.example.alisgram;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentKesfet extends Fragment {

    private DatabaseReference mDatabase, mDatabaseKategori;
    RecyclerView recyclerView;
    View view;
    LinearLayout.LayoutParams lp;
    LinearLayout dbLLayout;
    private Button kategoriButon, seninicin;
    private ModelKategori kategori;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    ArrayList<ModelAliskanlik> aliskanliklar;
    ArrayList<ModelKullanici> kullanicilar;
    KesfetAdapter adapter;

    public FragmentKesfet() {

    }

    public void kategoriListele(final String kategoriAdi) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                aliskanliklar.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.child("aliskanliklar").getChildren()) {
                    ModelAliskanlik aliskanlik = postSnapshot.getValue(ModelAliskanlik.class);
                    if (!aliskanlik.getAliskanlikKullaniciId().equals(mCurrentUser.getUid())) {
                        if (kategoriAdi.equals(aliskanlik.getAliskanlikKategori())) {
                            aliskanliklar.add(aliskanlik);
                            adapter.notifyDataSetChanged();
                        } else if (kategoriAdi.equals("seninicin")) {
                            aliskanliklar.add(aliskanlik);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                for (DataSnapshot postSnapshot : dataSnapshot.child("Kullanicilar").getChildren()) {
                    ModelKullanici kullanici = postSnapshot.getValue(ModelKullanici.class);
                    kullanicilar.add(kullanici);
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Firebase ERROR", "loadPost:onCancelled", databaseError.toException());
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

        kullanicilar = new ArrayList<ModelKullanici>();
        aliskanliklar = new ArrayList<ModelAliskanlik>();
        kategoriListele("seninicin");
        adapter = new KesfetAdapter(getContext(), aliskanliklar, kullanicilar);
        recyclerView.setAdapter(adapter);

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
                Log.w("Firebase ERROR", "loadPost:onCancelled", databaseError.toException());
            }
        });


        EditText etSearch = view.findViewById(R.id.etSearch);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listedeAra(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    private void listedeAra(String s) {

        if (s.trim().equals(""))
            adapter.updateList(aliskanliklar);
        else {
            ArrayList<ModelAliskanlik> tempLists = new ArrayList<>();

            for (ModelAliskanlik aliskanlik : aliskanliklar) {
                if (aliskanlik.getAliskanlikEtiket().contains(s)) {
                    tempLists.add(aliskanlik);
                }
            }

            adapter.updateList(tempLists);
        }

    }

}

