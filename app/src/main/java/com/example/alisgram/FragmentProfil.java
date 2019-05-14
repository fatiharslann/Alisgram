package com.example.alisgram;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class FragmentProfil extends Fragment {

    private FirebaseUser user;
    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentActivity myContext;
    private FirebaseAuth auth;

    public FragmentProfil() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_profil, container, false);

        TextView tvName = view.findViewById(R.id.profil_baskasi_isim);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        Button bt_cikis = view.findViewById(R.id.bt_cikis);
        Button bt_guncelleme = view.findViewById(R.id.bt_guncelleme);


        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        Uri resimyolu = user.getPhotoUrl();
        if (resimyolu != null)
            profilResminiYukle(view, resimyolu);

        bt_cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth != null) {
                    auth.signOut();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }
        });

        bt_guncelleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth != null) {
                    openDialog();
                }
            }
        });

        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        profilResminiYukle(view, user.getPhotoUrl());
        tvName.setText(user.getDisplayName());
        tvDescription.setText(user.getEmail());
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(myContext.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void profilResminiYukle(View viev, Uri profilResim) {
        ImageView ivUserProfil = viev.findViewById(R.id.ivProfile);
        Picasso.get().load(profilResim).into(ivUserProfil);
    }

    private void openDialog() {
        CustomFragmentDialog dialog = new CustomFragmentDialog();
        dialog.show(getFragmentManager(), "dialogTag");
    }

}
