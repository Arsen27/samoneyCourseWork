package com.example.samoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class AddNewActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager;
    AddNewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        tabLayout = findViewById(R.id.addNewTabBar);
        pager = (ViewPager2)findViewById(R.id.addNewPager);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new AddNewAdapter(fm, getLifecycle());
        pager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Operation"));
        tabLayout.addTab(tabLayout.newTab().setText("Bill"));
        tabLayout.addTab(tabLayout.newTab().setText("Limit"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    //        FragmentStateAdapter pageAdapter = new AddNewAdapter(AddNewActivity.this);
    //        pager.setAdapter(pageAdapter);
    }
}