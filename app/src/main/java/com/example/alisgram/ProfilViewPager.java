package com.example.alisgram;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ProfilViewPager extends FragmentStatePagerAdapter {

    private String[] tabTitles = new String[]{"Tümü", "Edinilenler"};
    public ProfilViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentBaskaAliskanliklar tab1 = new FragmentBaskaAliskanliklar();
                return tab1;
            case 1:
                FragmentBaskaEdinilenler tab2 = new FragmentBaskaEdinilenler();
                return tab2;
            default:
                return null;
        }
    }

    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
