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

    public FragmentAnasayfa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_fragment_anasayfa, container, false);
        final ArrayList<ModelAliskanlik> aliskanliklar = new ArrayList<ModelAliskanlik>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("aliskanliklar");
        recyclerView = view.findViewById(R.id.anasayfaItemList);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ModelAliskanlik aliskanlik = postSnapshot.getValue(ModelAliskanlik.class);
                    aliskanliklar.add(aliskanlik);
                }
                AnasayfaAdapter productAdapter = new AnasayfaAdapter(view.getContext(), aliskanliklar);
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
