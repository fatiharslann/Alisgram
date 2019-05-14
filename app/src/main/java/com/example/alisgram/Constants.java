package com.example.alisgram;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Constants {
    public static void profilResminiYukle(View viev, final String userId) {
        final ImageView ivUserProfil = viev.findViewById(R.id.ivProfile);
        FirebaseHelper.getKullaniciBilgisi(userId, new FirebaseHelper.IKullaniciBilgisi() {
            @Override
            public void onCallback(ModelKullanici userInfo) {
                if (!userInfo.getProfilUri().equals("?"))
                    Picasso.get().load(Uri.parse(userInfo.getProfilUri())).into(ivUserProfil);
            }
        });
    }
}
