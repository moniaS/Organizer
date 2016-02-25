package com.example.android.myapplication.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.myapplication.Adapters.TasksAdapter;
import com.example.android.myapplication.R;
import com.example.android.myapplication.ObjectClasses.Task;
import com.example.android.myapplication.TasksDatabaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    ListView lv_tasks;
    List<Task> tasksList;
    private TasksDatabaseAdapter tasksDbAdapter;
    private Cursor todoCursor;
    private List<Task> tasks;
    private TasksAdapter listAdapter;


    FragmentManager fm = getFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_do, container, false);
        lv_tasks = (ListView) view.findViewById(R.id.lv_to_do_items);
        listAdapter = new TasksAdapter(this.getActivity(), tasks);
        tasksDbAdapter = new TasksDatabaseAdapter(getContext());
        tasksDbAdapter.open();
        tasks = tasksDbAdapter.getAllTasks1();
        getAllTasks();
        listAdapter = new TasksAdapter (this.getActivity(), tasks);
        lv_tasks.setAdapter(listAdapter);
        return view;
    }

    private void getAllTasks() {
        tasks = new ArrayList<Task>();
        todoCursor = getAllEntriesFromDb();
        updateTaskList();
    }

    private Cursor getAllEntriesFromDb() {
        todoCursor = tasksDbAdapter.getAllTasks();
        if(todoCursor != null) {
            getActivity().startManagingCursor(todoCursor);
            todoCursor.moveToFirst();
        }
        return todoCursor;
    }

    private void updateTaskList() {
        if(todoCursor != null && todoCursor.moveToFirst()) {
            do {
                int id = todoCursor.getInt(TasksDatabaseAdapter.ID_COLUMN);
                String name = todoCursor.getString(TasksDatabaseAdapter.NAME_COLUMN);
                String description = todoCursor.getString(TasksDatabaseAdapter.DESCRIPTION_COLUMN);
                boolean completed = todoCursor.getInt(TasksDatabaseAdapter.COMPLETED_COLUMN) > 0 ? true : false;
                tasks.add(new Task(id, name, description));
            } while(todoCursor.moveToNext());
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }
  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("onOptionsItemSelected", "yes");
        switch (item.getItemId()) {
            case R.id.action_add: {
                Toast.makeText(this.getContext(), "Adding selected", Toast.LENGTH_SHORT)
                        .show();
                return true;
            }
            case R.id.action_settings:
                return false;
            default:
                break;
        }
        return false;
    }*/

    public void addTaskToList(String name, String description) {
        tasks.add(new Task(0, name, description));
        listAdapter.notifyDataSetChanged();
    }
}




