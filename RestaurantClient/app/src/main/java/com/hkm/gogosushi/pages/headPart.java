package com.hkm.gogosushi.pages;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkm.gogosushi.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;

import java.util.ArrayList;


/**
 * Created by hesk on 4/12/2015.
 */
public class headPart extends Fragment {

    static final int ITEMS = 10;
    ViewPager mViewPager;
    static ArrayList<String> CHESSES = new ArrayList<String>();
    MyAdapter myAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CHESSES.add("Chesse0");
        final View view = inflater.inflate(R.layout.coverslider, null);
        myAdapter = new MyAdapter(getFragmentManager());


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(getActivity())
                .add(R.string.new_tab, ArrayListFragment.class)
                .add(R.string.outlets, ArrayListFragment.class)
                .add(R.string.food, ArrayListFragment.class)
                .add(R.string.beauty, ArrayListFragment.class)
                .add(R.string.luxury, ArrayListFragment.class)
                .add(R.string.home, ArrayListFragment.class)
                .add(R.string.deco, ArrayListFragment.class)
                .add(R.string.fashion, ArrayListFragment.class)
                .add(R.string.digital, ArrayListFragment.class)
                .add(R.string.travel, ArrayListFragment.class)
                .add(R.string.fun, ArrayListFragment.class)
                .add(R.string.health, ArrayListFragment.class)
                .create());

        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(mViewPager);

        return view;
    }

  /*  public void MyOnClick(View view) {
        int id = view.getId();
        int position;

        switch (id) {
            case R.id.goto_previous:
                position = mViewPager.getCurrentItem();
                CHESSES.add("Chesse" + (position + 1));
                if (position > 0) {
                    mViewPager.setCurrentItem(position - 1);
                } else {
                    Toast.makeText(getActivity(), "This is First Fragment", Toast.LENGTH_LONG).show();
                }
                myAdapter.notifyDataSetChanged();
                break;

            case R.id.goto_next:
                position = mViewPager.getCurrentItem();
                CHESSES.add("Chesse" + (position + 1));
                if (position < (ITEMS - 1)) {
                    mViewPager.setCurrentItem(position + 1);
                } else {
                    Toast.makeText(getActivity(), "This is Last Fragment", Toast.LENGTH_LONG).show();
                }
                myAdapter.notifyDataSetChanged();
                break;
        }
    }*/

    static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(getClass().getSimpleName(), position + "");
            return ArrayListFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return ITEMS;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

}