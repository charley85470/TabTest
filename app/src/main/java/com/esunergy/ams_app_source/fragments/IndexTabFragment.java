package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.esunergy.ams_app_source.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexTabFragment extends BaseFragment {

    private final String PAGE_TAG = "IndexTabFragment";
    private int tabPosition = 0;
    private Context ctx;
    private View topLayoutView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private TabAdapter adapter;

    public IndexTabFragment() {
        // Required empty public constructor
        fragments = new ArrayList<>();
        fragments.add(new ActionListFragment());
        fragments.add(new ActionOverdueListFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_index_tab, container, false);
        tabLayout = topLayoutView.findViewById(R.id.tabs);
        viewPager = topLayoutView.findViewById(R.id.view_pager);

        if (viewPager != null) {
            setViewPager();
        }

        tabLayout.setupWithViewPager(viewPager);

        return topLayoutView;
    }

    private void setViewPager() {
        if (adapter == null) {
            adapter = new TabAdapter(getFragmentManager());
            adapter.addFragment(new ActionTodoListFragment(), "待辦行動");
            adapter.addFragment(new ActionListFragment(), "所有行動");
            adapter.addFragment(new ActionOverdueListFragment(), "逾期行動");

        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        //setViewPager();
        viewPager.getAdapter().notifyDataSetChanged();
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(tabPosition);
    }

    private class TabAdapter extends FragmentStatePagerAdapter {

        private Context ctx;
        private List<Fragment> fragments;
        private List<String> titles;
        private HashMap<Integer, String> mFragmentTags;

        public TabAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            titles = new ArrayList<>();
            mFragmentTags = new HashMap<>();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return FragmentStatePagerAdapter.POSITION_NONE;
        }
    }

}
