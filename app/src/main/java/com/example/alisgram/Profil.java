package com.example.alisgram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class Profil extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentAnasayfa fragmentAnasayfa = new FragmentAnasayfa();
                    loadFragment(fragmentAnasayfa);
                    return true;
                case R.id.navigation_kesfet:
                    FragmentKesfet fragmentKesfet = new FragmentKesfet();
                    loadFragment(fragmentKesfet);
                    return true;
                case R.id.navigation_ekle:
                    FragmentEkle fragmentEkle = new FragmentEkle();
                    loadFragment(fragmentEkle);
                    return true;
                case R.id.navigation_profil:
                    FragmentProfil fragmentProfil = new FragmentProfil();
                    loadFragment(fragmentProfil);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_profil);

        FragmentProfil fragmentProfil = new FragmentProfil();
        loadFragment(fragmentProfil);

        //alt menu kapatma
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fram,fragment,"FragmentName");
        fragmentTransaction.commit();
    }
}
