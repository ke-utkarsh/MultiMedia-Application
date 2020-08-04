package com.example.projecttwo.ui.gallery;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.projecttwo.Login;
import com.example.projecttwo.Signup;

 class tabs2 extends FragmentPagerAdapter {
    Context context2;
    int totaltabs2;
    public tabs2(Context c, FragmentManager fm, int totaltabs) {
        super(fm);
        context2 = c;
        this.totaltabs2 = totaltabs;
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
        return totaltabs2;
    }
}
