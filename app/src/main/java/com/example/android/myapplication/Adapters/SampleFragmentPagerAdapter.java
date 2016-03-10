package com.example.android.myapplication.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.android.myapplication.Fragments.CalendarFragment;
import com.example.android.myapplication.Fragments.NotesFragment;
import com.example.android.myapplication.Fragments.TasksFragment;

public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "To Do", "Calendar", "Notes" };
    private TasksFragment taskFragment;
    private CalendarFragment calendarFragment;
    private NotesFragment notesFragment;

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
                return new TasksFragment();
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
        Fragment fragment = (Fragment) object;
        Fragment mCurrentPrimaryItem = getItem(position);
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                // There's no check to see if a parent fragment is even visible!
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        // save the appropriate reference depending on position
        switch (position) {
            case 0:
                taskFragment = (TasksFragment) createdFragment;
                break;
            case 1:
                calendarFragment = (CalendarFragment) createdFragment;
                break;
            case 2:
                notesFragment = (NotesFragment) createdFragment;
                break;
        }
        return createdFragment;
    }
}
