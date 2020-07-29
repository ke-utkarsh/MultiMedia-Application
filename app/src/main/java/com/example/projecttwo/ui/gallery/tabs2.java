package com.example.projecttwo.ui.gallery;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.projecttwo.Login;
import com.example.projecttwo.Signup;

 class Tabs2 extends FragmentPagerAdapter {
    Context context2;
    int totalTabs2;
    public Tabs2(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context2 = c;
        this.totalTabs2 = totalTabs;
    }
    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                Login loginFragment = new Login();
                return loginFragment;
            case 1:
                Signup signupFragment = new Signup();
                return signupFragment;
            default:
                 return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs2;
    }
}
