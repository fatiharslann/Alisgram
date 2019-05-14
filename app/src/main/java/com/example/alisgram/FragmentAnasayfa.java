package com.example.alisgram;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class FragmentAnasayfa extends Fragment {

    FirebaseUser user;
    private DatabaseReference mDatabase;
    RecyclerView recyclerView;
    View view;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    boolean durum;

    public FragmentAnasayfa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_fragment_anasayfa, container, false);

        final ArrayList<ModelAliskanlik> aliskanliklar = new ArrayList<ModelAliskanlik>();
        final ArrayList<ModelKullanici> kullanicilar = new ArrayList<ModelKullanici>();
        final ArrayList<ModelTakip> takipler = new ArrayList<ModelTakip>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = view.findViewById(R.id.anasayfaItemList);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        final String aUserId = mCurrentUser.getUid();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                aliskanliklar.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.child("aliskanliklar").getChildren()) {
                    ModelAliskanlik aliskanlik = postSnapshot.getValue(ModelAliskanlik.class);
                    String aKullaniciId = aliskanlik.getAliskanlikKullaniciId();
                    if (!aKullaniciId.equals(aUserId)) {
                        aliskanliklar.add(aliskanlik);
                    }
                }
                for (DataSnapshot postSnapshot : dataSnapshot.child("Kullanicilar").getChildren()) {
                    ModelKullanici kullanici = postSnapshot.getValue(ModelKullanici.class);
                    kullanicilar.add(kullanici);
                }
                for (DataSnapshot postSnapshot : dataSnapshot.child("Takip").getChildren()) {
                    ModelTakip takip = postSnapshot.getValue(ModelTakip.class);
                    takipler.add(takip);
                }

                final ArrayList<ModelAliskanlik> taliskanliklar = new ArrayList<ModelAliskanlik>();

                for (int i = 0; i < aliskanliklar.size(); i++) {

                    durum=false;

                    for (int j = 0; j < takipler.size(); j++) {

                        if (takipler.get(j).getFrom().equals(aUserId)) {
                            if (aliskanliklar.get(i).getAliskanlikKullaniciId().equals(takipler.get(j).getTo())) {
                                durum=true;
                                break;
                            }
                        }
                    }

                    if (durum){
                        taliskanliklar.add(aliskanliklar.get(i));
                    }

                }

                AnasayfaAdapter productAdapter = new AnasayfaAdapter(view.getContext(), taliskanliklar, kullanicilar);
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


        return view;
    }

}
