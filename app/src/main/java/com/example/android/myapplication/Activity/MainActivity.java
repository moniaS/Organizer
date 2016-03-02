package com.example.android.myapplication.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.myapplication.Fragments.CalendarFragment;
import com.example.android.myapplication.Fragments.AddTaskDialog;
import com.example.android.myapplication.Fragments.TaskDetailsDialog;
import com.example.android.myapplication.Fragments.TasksFragment;
import com.example.android.myapplication.R;
import com.example.android.myapplication.Adapters.SampleFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private SampleFragmentPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TasksFragment tasksFragment;
    private TaskDetailsDialog taskDetailsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        initViews();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        // TODO Auto-generated method stub
        super.onAttachFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add:
                if(mViewPager.getCurrentItem() == 0) {
                    TasksFragment taskFragment = (TasksFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
                    showEditDialog();
                }
                else if(mViewPager.getCurrentItem() == 1) {
                    CalendarFragment calendarFragment = (CalendarFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
                    Toast.makeText(this, "Adding selected", Toast.LENGTH_SHORT)
                            .show();
                }
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                return true;
            case R.id.action_clear_tasks:
                tasksFragment = (TasksFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
                Toast.makeText(this, "Clearing selected", Toast.LENGTH_SHORT)
                        .show();
                tasksFragment.clearCompletedTasks();
            return true;
            default:
                break;
        }
        return false;
    }

    public void showEditDialog() {
        tasksFragment = (TasksFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
        FragmentManager fm = getSupportFragmentManager();
        AddTaskDialog addingTaskDialog = new AddTaskDialog();
        addingTaskDialog.setTargetFragment(tasksFragment, 0 );
        addingTaskDialog.show(fm, "dialog_add_task");
    }

    public void showTaskDetailsDialog() {
        tasksFragment = (TasksFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
        FragmentManager fm = getSupportFragmentManager();
        taskDetailsDialog = new TaskDetailsDialog();
        taskDetailsDialog.setTargetFragment(tasksFragment, 0);
        taskDetailsDialog.show(fm, "dialog_task_details");
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.icon_app);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    private void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
