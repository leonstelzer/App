package com.mind.simplelogin.events.findevents;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {


    private int numoftabs;

    public PageAdapter(FragmentManager fm, int numoftabs) {
        super(fm);
        this.numoftabs = numoftabs;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new tab1 ();
            case 1:
                return new tab2 ();
            case 2:
                return new tab3 ();
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return numoftabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
