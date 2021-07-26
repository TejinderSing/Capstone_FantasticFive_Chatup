package com.example.chatupgmail.Adapter;

import com.example.chatupgmail.Chat;
import com.example.chatupgmail.Stories;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//import com.example.chatupgmail.Models.Stories;

public class PagerAdapter extends FragmentPagerAdapter {
    int tabcount;


    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Chat();


            case 1:
                return new Stories();

            default:
                return null;
        }



    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
