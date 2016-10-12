package com.kondee.testmodule.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kondee.testmodule.fragment.activity_main.MainFragment;
import com.kondee.testmodule.fragment.activity_main.SecondFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public MainFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        fragmentList.add(MainFragment.newInstance());
        fragmentList.add(SecondFragment.newInstance());
        titleList.add("ONE");
        titleList.add("TWO");
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
