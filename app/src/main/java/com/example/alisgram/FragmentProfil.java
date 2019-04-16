package com.example.alisgram;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public FragmentProfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fragment_profil, container, false);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvDescription = view.findViewById(R.id.tvDescription);

        user = FirebaseAuth.getInstance().getCurrentUser();

        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        profilResminiYukle(view, user.getPhotoUrl());
        tvName.setText(user.getDisplayName());
        tvDescription.setText(user.getEmail());

        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
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
}
