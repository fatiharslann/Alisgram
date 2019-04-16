package com.example.alisgram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FragmentAliskanliklarim extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private View view;
    private ArrayList<ModelAliskanlik> aliskanliklar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_fragment_aliskanliklarim, container, false);

        aliskanliklar = new ArrayList<ModelAliskanlik>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("aliskanliklar");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = mDatabase.orderByChild("aliskanlikKullaniciId").equalTo(user.getUid());

        recyclerView = view.findViewById(R.id.kullaniciAliskanliklarimList);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                aliskanliklar.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ModelAliskanlik aliskanlik = postSnapshot.getValue(ModelAliskanlik.class);
                    aliskanliklar.add(aliskanlik);
                }
                AliskanlikAdapter productAdapter = new AliskanlikAdapter(view.getContext(), aliskanliklar);
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
