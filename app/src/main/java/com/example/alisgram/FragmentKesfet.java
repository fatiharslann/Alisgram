package com.example.alisgram;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class FragmentKesfet extends Fragment {

    private DatabaseReference mDatabase, mDatabaseKategori,mDataMining;
    RecyclerView recyclerView;
    View view;
    LinearLayout.LayoutParams lp;
    LinearLayout dbLLayout;
    private Button kategoriButon, seninicin;
    private ModelKategori kategori;
    private ModelAliskanlik kategoriler;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    ArrayList<ModelAliskanlik> aliskanliklar,aliskanliklar2;
    ArrayList<ModelKullanici> kullanicilar;
    KesfetAdapter adapter;

    private Knn knn=new Knn();
    private int ALISKANLIK_SAYISI=1;
    private ArrayList<ModelAliskanlik> listVeriMadenciligiAliskanliklar;
    private int[][] donen;
    private int sayac=0;
    private int tempSayac=0;
    private ArrayList<int[]> listKomsular;

    int[][] bizimAliskanlikIdDizisi,digerAliskanlikIdDizisi;
    ArrayList<Integer> bizimAltKategoriId=new  ArrayList<>(),bizimUstKategoriId=new ArrayList<>();
    ArrayList<Integer> digerUstKategoriId=new ArrayList<>(),digerAltKategoriId=new ArrayList<>();

    public FragmentKesfet() {

    }

    public void kategoriListele(final String kategoriAdi) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                aliskanliklar.clear();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                for (DataSnapshot postSnapshot : dataSnapshot.child("aliskanliklar").getChildren()) {
                    ModelAliskanlik aliskanlik = postSnapshot.getValue(ModelAliskanlik.class);
                        if (kategoriAdi.equals(aliskanlik.getAliskanlikKategori())) {
                            aliskanliklar.add(aliskanlik);
                            adapter.notifyDataSetChanged();
                        } else if (kategoriAdi.equals("seninicin")) {
                            kategoriler = postSnapshot.getValue(ModelAliskanlik.class);

                            if(user.getUid().equals(kategoriler.getAliskanlikKullaniciId())){
                                int ustKategoriId=Integer.parseInt(kategoriler.getAliskanlikKategori());
                                int altKategoriId=Integer.parseInt(kategoriler.getAliskanlikAltKategori());

                                if(!bizimAltKategoriId.contains(altKategoriId)){
                                    bizimAltKategoriId.add(altKategoriId);
                                    bizimUstKategoriId.add(ustKategoriId);
                                }

                            }
                        }

                }

                for (DataSnapshot postSnapshot2 : dataSnapshot.child("aliskanliklar").getChildren()) {
                    if (kategoriAdi.equals("seninicin")) {
                        kategoriler = postSnapshot2.getValue(ModelAliskanlik.class);
                        if (!user.getUid().equals(kategoriler.getAliskanlikKullaniciId())) {
                            int ustKategoriId = Integer.parseInt(kategoriler.getAliskanlikKategori());
                            int altKategoriId = Integer.parseInt(kategoriler.getAliskanlikAltKategori());
                            if(!bizimAltKategoriId.contains(altKategoriId)){
                                digerAltKategoriId.add(altKategoriId);
                                digerUstKategoriId.add(ustKategoriId);
                            }

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

        if(kategoriAdi!="seninicin")
            return;

        bizimAliskanlikIdDizisi=new int[bizimUstKategoriId.size()][2];
        for (int i=0;i<bizimUstKategoriId.size();i++){
            for (int j=0;j<2;j++){
                if (j==0){
                    bizimAliskanlikIdDizisi[i][j]=bizimUstKategoriId.get(i);
                }else{
                    bizimAliskanlikIdDizisi[i][j]=bizimAltKategoriId.get(i);
                }
                // bizimAliskanlikIdDizisi[i][j]=bizimAliskanlikIdDizisi[bizimUstKategoriId.get()][bizimAltKategoriId.get(j)];
                Log.i("bizimUstKategori", bizimAliskanlikIdDizisi[i][j]+"");
            }
        }
        digerAliskanlikIdDizisi=new int[digerUstKategoriId.size()][2];
        for (int i=0;i<digerUstKategoriId.size();i++) {
            for (int j = 0; j < 2; j++) {
                if (j == 0) {
                    digerAliskanlikIdDizisi[i][j] = digerUstKategoriId.get(i);
                    Log.i("digerKategori", digerUstKategoriId.get(i) + "");
                } else {
                    digerAliskanlikIdDizisi[i][j] = digerAltKategoriId.get(i);
                    Log.i("digerKategori", digerAltKategoriId.get(i) + "");
                }

                // Log.i("digerKategori", digerAliskanlikIdDizisi[i][j] + "");

            }
        }

        aliskanliklar.clear();
        listKomsular = new ArrayList<>();
        for(int i=0;i<bizimAliskanlikIdDizisi.length;i++){
            donen=knn.uzaklikHesapla(bizimAliskanlikIdDizisi[i],digerAliskanlikIdDizisi,2);
            for (int j = 0;j<donen.length;j++)
                listKomsular.add(donen[j]);
        }

        mDataMining.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (sayac=0;sayac<listKomsular.size();sayac++) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ModelAliskanlik aliskanlik = postSnapshot.getValue(ModelAliskanlik.class);
                        if (listKomsular.get(sayac)[0] == Integer.parseInt(aliskanlik.getAliskanlikKategori()) && listKomsular.get(sayac)[1] == Integer.parseInt(aliskanlik.getAliskanlikAltKategori())) {
                            aliskanliklar.add(aliskanlik);
                            tempSayac++;
                        }
                        if (tempSayac == ALISKANLIK_SAYISI) {
                            tempSayac = 0;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

        mDataMining=FirebaseDatabase.getInstance().getReference("aliskanliklar");
        listVeriMadenciligiAliskanliklar = new ArrayList<ModelAliskanlik>();

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
                        final int kategoriAdi = kategori.getKategoriId();
                        kategoriButon.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.kesfet_button));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                        params.setMargins(10, 0, 0, 0);                        //Drawable top = getResources().getDrawable(R.drawable.search);
                        kategoriButon.setLayoutParams(params);

                        kategoriButon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                kategoriListele(kategoriAdi+"");
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

