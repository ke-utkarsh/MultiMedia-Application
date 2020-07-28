package com.example.projecttwo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Tabs extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public Tabs(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Videos videosFragment = new Videos();
                return videosFragment;
            case 1:
                Audios audiosFragment = new Audios();
                return audiosFragment;
            case 2:
                PDF pdfFragment = new PDF();
                return pdfFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
