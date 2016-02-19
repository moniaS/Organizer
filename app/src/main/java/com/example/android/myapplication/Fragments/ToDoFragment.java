package com.example.android.myapplication.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.myapplication.Adapters.InteractiveArrayAdapter;
import com.example.android.myapplication.R;
import com.example.android.myapplication.ObjectClasses.ToDoItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends Fragment {

    ListView listview;
    ArrayList<ToDoItem> list;
    ArrayAdapter <ToDoItem> adapter;
    private Parcelable state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_do, container, false);
        listview = (ListView) view.findViewById(R.id.lv_to_do_items);
        adapter = new InteractiveArrayAdapter(this.getContext(), R.layout.list_to_do_item, R.id.tv_task_name, getTaskList());
        listview.setAdapter(adapter);
        return view;
    }
    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", (Serializable) list);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //probably orientation change
            list = (ArrayList<ToDoItem>) savedInstanceState.getSerializable("list");
        } else {
            if (list != null) {
                //returning from backstack, data is fine, do nothing
            } else {
                //newly created, compute data
                list = getTaskList();
            }
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("onOptionsItemSelected", "yes");
        switch (item.getItemId()) {
            case R.id.action_add:
            {
                Toast.makeText(this.getContext(), "Adding selected", Toast.LENGTH_SHORT)
                    .show();
                addTaskToList();
                return true;
            }
            case R.id.action_settings:
                return false;
            default:
                break;
        }
        return false;
    }

    private void addTaskToList()
    {
        list.add(new ToDoItem("name" + list.size(), "description" + list.size()));
        adapter.notifyDataSetChanged();
    }
    public ArrayList<ToDoItem> getTaskList() {
        list = new ArrayList<ToDoItem>();
        return list;
    }

    private ToDoItem get(String name, String description) {
        return new ToDoItem(name, description);
    }
}




