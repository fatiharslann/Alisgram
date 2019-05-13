package com.example.alisgram;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {

    static FirebaseUser user;
    static ModelKullanici kullanici;

    public interface MyCallback {
        void onCallback(ModelKullanici value);
    }

    public interface IKullaniciResmi {
        void onCallback(String userImage);
    }

    public static void takipEt(String from,String to){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Takip");

        String key = myRef.push().getKey();
        Takip takip = new Takip();
        takip.setFrom(from);
        takip.setTo(to);
        takip.setTakip_id(key);

        myRef.child(key).setValue(takip);

    }


    private static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }


    public static ModelKullanici getCurrentKullanici() {

        if (isNullUser(user))
            user = getCurrentUser();

        kullanici = new ModelKullanici();

        /*
        readData(new MyCallback() {
            @Override
            public void onCallback(ModelKullanici user) {
                    kullanici=user;
            }
        });*/

        kullanici.setEmail(user.getEmail());
        kullanici.setUuid(user.getUid());
        String[] displayName = user.getDisplayName().split(" ");

        kullanici.setSoyisim(displayName[displayName.length - 1]);
        kullanici.setIsim(displayName[0]);

        return kullanici;
    }


    public static void readData(final MyCallback myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("Kullanicilar/" + getCurrentUser().getUid());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                kullanici = dataSnapshot.getValue(ModelKullanici.class);

                myCallback.onCallback(kullanici);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(postListener);

    }


    public static void setUpdateCurrentUser(ModelKullanici veri) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Kullanicilar");
        ref.child(veri.getUuid()).setValue(veri);
    }


    public static void ekleKullanici() {

        user = getCurrentUser();

        if (!isNullUser(user)) {
            ModelKullanici kullanici = getCurrentKullanici();
            kullanici.setProfilUri(user.getPhotoUrl().toString());
            kullaniciEkle(kullanici);
            //Log.d("FirebaseHelper", user.getPhotoUrl().toString());
        }
    }

    public static void ekleKullanici(String isim) {

        user = getCurrentUser();

        if (!isNullUser(user)) {
            ModelKullanici kullanici = getCurrentKullanici();
            kullanici.setIsim(isim);
            kullaniciEkle(kullanici);
        }
    }

    private static void kullaniciEkle(final ModelKullanici veri) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Kullanicilar/" + veri.getUuid());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                if (count == 0)
                    ref.setValue(veri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        ref.addListenerForSingleValueEvent(valueEventListener);

    }

    private static Boolean isNullUser(FirebaseUser user) {
        if (user != null)
            return false;
        else
            return true;
    }

    public static void getKullaniciResmi(String userId, final IKullaniciResmi callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("Kullanicilar/" + userId);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                kullanici = dataSnapshot.getValue(ModelKullanici.class);

                callback.onCallback(kullanici.getProfilUri());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(postListener);
    }
}
