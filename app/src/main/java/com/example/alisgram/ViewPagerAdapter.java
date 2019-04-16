package com.example.alisgram;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitles = new String[]{"Tümü", "Edindiklerim"};
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentAliskanliklarim tab1 = new FragmentAliskanliklarim();
                return tab1;
            case 1:
                FragmentEdinliklerim tab2 = new FragmentEdinliklerim();
                return tab2;
            default:
                return null;
        }
    }
    // overriding getPageTitle()
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
    @Override
    public int getCount() {
        return tabTitles.length;
    }



}