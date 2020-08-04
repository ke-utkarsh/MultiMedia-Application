package com.example.projecttwo.ui.gallery;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.projecttwo.R;

public class GalleryFragment extends Fragment {
    TabLayout tabLayouts;
    ViewPager viewPagers;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        tabLayouts=root.findViewById(R.id.tabLayoutS);
        viewPagers=root.findViewById(R.id.viewPagerS);

        tabLayouts.addTab(tabLayouts.newTab().setText("Login"));
        tabLayouts.addTab(tabLayouts.newTab().setText("Signup"));
        tabLayouts.setTabGravity(TabLayout.GRAVITY_FILL);

        final tabs2 adapter= new tabs2(getActivity(),getChildFragmentManager(),tabLayouts.getTabCount());
        viewPagers.setAdapter(adapter);

        viewPagers.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayouts));
        tabLayouts.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagers.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        return root;
    }
}
