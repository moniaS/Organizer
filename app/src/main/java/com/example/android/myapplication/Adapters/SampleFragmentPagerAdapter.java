package com.example.android.myapplication.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.myapplication.Fragments.CalendarFragment;
import com.example.android.myapplication.Fragments.NotesFragment;
import com.example.android.myapplication.Fragments.ToDoFragment;

public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "To Do", "Calendar", "Notes" };
    Fragment mCurrentFragment;

    public SampleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new ToDoFragment();
            case 1:
                return new CalendarFragment();
            case 2:
                return new NotesFragment();
            default:
                return null;
        }
    }
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mCurrentFragment != object) {
            mCurrentFragment = (Fragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
