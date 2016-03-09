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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.Fragments.CalendarFragment;
import com.example.android.myapplication.Fragments.AddTaskDialog;
import com.example.android.myapplication.Fragments.TaskDetailsDialog;
import com.example.android.myapplication.Fragments.TasksFragment;
import com.example.android.myapplication.Interfaces.NewEditDialogListener;
import com.example.android.myapplication.ObjectClasses.Task;
import com.example.android.myapplication.R;
import com.example.android.myapplication.Adapters.SampleFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity implements NewEditDialogListener {

    private SampleFragmentPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AddTaskDialog addTaskDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUserInterface();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                setOnAddActionListener();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_clear_tasks:
                setOnClearTasksOptionListenerForTasksFragment();
                return true;
            default:
                break;
        }
        return false;
    }

    private void initUserInterface () {
        initToolbar();
        initViews();
    }
    private void setOnAddActionListener () {
        if(mViewPager.getCurrentItem() == 0) {
            setOnAddActionListenerForTasksFragment();
        }
        else if(mViewPager.getCurrentItem() == 1) {
            setOnAddActionListenerForCalendarFragment();
        }
    }

    private void setOnAddActionListenerForTasksFragment () {
        TasksFragment tasksFragment = (TasksFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
        initAddTaskDialog();
    }

    private void setOnAddActionListenerForCalendarFragment () {
        CalendarFragment calendarFragment = (CalendarFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
        Toast.makeText(this, "Adding selected", Toast.LENGTH_SHORT)
                .show();
    }

    private void setOnClearTasksOptionListenerForTasksFragment () {
        TasksFragment tasksFragment = (TasksFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
        tasksFragment.clearCompletedTasks();
    }

    private void initAddTaskDialog () {
        createAddTaskDialog();
        showAddTaskDialog();
    }

    private void createAddTaskDialog () {
        TasksFragment tasksFragment = (TasksFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
        addTaskDialog = new AddTaskDialog();
        addTaskDialog.setTargetFragment(tasksFragment, 0);
    }

    private void showAddTaskDialog () {
        FragmentManager fm = getSupportFragmentManager();
        addTaskDialog.show(fm, "dialog_add_task");
    }

    private void initToolbar () {
        createToolbar();
        setToolbarOptions();
    }
    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    private void setToolbarOptions () {
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

    @Override
    public void onInitEditDialog(Task task) {
        TasksFragment tasksFragment = (TasksFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem());
        TaskDetailsDialog taskDetailsDialog = new TaskDetailsDialog();
        taskDetailsDialog.setTargetFragment(tasksFragment, 0);
        FragmentManager fm = getSupportFragmentManager();
        taskDetailsDialog.show(fm, "dialog_task_details");
    }
}
