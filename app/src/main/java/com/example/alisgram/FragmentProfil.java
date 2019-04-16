package com.example.alisgram;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class FragmentProfil extends Fragment {

    FirebaseAuth auth;

    public FragmentProfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_profil, container, false);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        Button bt_cikis = view.findViewById(R.id.bt_cikis);


        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        Uri resimyolu = user.getPhotoUrl();
        if (resimyolu != null)
            profilResminiYukle(view, resimyolu);


        tvName.setText(user.getDisplayName());
        tvDescription.setText(user.getEmail());

        return view;
    }

    private void profilResminiYukle(View viev, Uri profilResim) {
        ImageView ivUserProfil = viev.findViewById(R.id.ivProfile);
        Picasso.get().load(profilResim).into(ivUserProfil);
    }

}
