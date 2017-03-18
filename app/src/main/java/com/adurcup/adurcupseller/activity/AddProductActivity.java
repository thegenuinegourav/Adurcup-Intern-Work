package com.adurcup.adurcupseller.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.fragment.PickupScheduleFragment;
import com.adurcup.adurcupseller.fragment.TrackStatusFragment;

public class AddProductActivity extends AppCompatActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        pager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int index) {
                switch (index) {
                    case 0:
                        return PickupScheduleFragment.newInstance();
                    default:
                        return TrackStatusFragment.newInstance();
                }

            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Schedule PickUp";
                    default:
                        return "Track Status";
                }

            }
        };

        //Adding adapter to pager
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }


    public void switchToNext()
    {
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }
}
