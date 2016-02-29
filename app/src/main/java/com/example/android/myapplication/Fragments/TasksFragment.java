package com.example.android.myapplication.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.Activity.MainActivity;
import com.example.android.myapplication.Adapters.TasksAdapter;
import com.example.android.myapplication.R;
import com.example.android.myapplication.ObjectClasses.Task;
import com.example.android.myapplication.TasksDatabaseAdapter;

import java.util.List;
public class TasksFragment extends android.support.v4.app.Fragment implements AddTaskDialog.NewTaskFragmentListener, TaskDetailsDialog.DetailsTaskFragmentListener {

    View view;
    private MainActivity activity;
    private ListView lv_tasks;
    private TasksDatabaseAdapter dbTasksAdapter;
    private Cursor todoCursor;
    private List<Task> tasks;
    private TasksAdapter listAdapter;
    android.support.v4.app.FragmentManager fm = getFragmentManager();
    private int chosenTaskPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_to_do, container, false);
        activity = (MainActivity) getActivity();
        initListView();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onFinishEditDialog(String name, String description) {
        this.addTaskToList(name, description);
    }

    @Override
    public void onFinishDetailsTaskDialog(String name, String description) {
        tasks.get(chosenTaskPosition).setName(name);
        tasks.get(chosenTaskPosition).setDescription(description);
        Toast.makeText(getContext(), tasks.get(chosenTaskPosition).getName(), Toast.LENGTH_SHORT);
        dbTasksAdapter.updateTask(tasks.get(chosenTaskPosition).getId(),
                                tasks.get(chosenTaskPosition).getName(),
                                tasks.get(chosenTaskPosition).getDescription(),
                                tasks.get(chosenTaskPosition).isCompleted());
        updateTaskList();
        listAdapter.notifyDataSetChanged();
    }

    private void initListView() {
        lv_tasks = (ListView) view.findViewById(R.id.lv_to_do_items);
        listAdapter = new TasksAdapter(this.getActivity(), tasks);
        dbTasksAdapter = new TasksDatabaseAdapter(getContext());
        tasks = dbTasksAdapter.getAllTasks();
        listAdapter = new TasksAdapter (this.getActivity(), tasks);
        lv_tasks.setAdapter(listAdapter);
        onChosenTaskClickListener();
    }


    private void onChosenTaskClickListener(){
        lv_tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_LONG).show();
                chosenTaskPosition = position;
                activity.showTaskDetailsDialog();
                // createAlertDialog();
            }
        });
    }

    public void updateTask(Task task) {
        int id = task.getId();
        String name = task.getName();
        String description = task.getDescription();
        boolean completed = task.isCompleted();
        dbTasksAdapter.updateTask(id, name, description, completed);
    }

    private void updateTaskList() {
        if(todoCursor != null && todoCursor.moveToFirst()) {
            do {
                int id = todoCursor.getInt(TasksDatabaseAdapter.ID_COLUMN);
                String name = todoCursor.getString(TasksDatabaseAdapter.NAME_COLUMN);
                String description = todoCursor.getString(TasksDatabaseAdapter.DESCRIPTION_COLUMN);
                tasks.add(new Task(id, name, description, false));
            } while(todoCursor.moveToNext());
        }
    }

    public void addTaskToList(String name, String description) {
        tasks.add(new Task(name, description));
        dbTasksAdapter.insertTask(name, description);
        listAdapter.notifyDataSetChanged();
    }

    public void deleteTask() {
        dbTasksAdapter.deleteTask(tasks.get(chosenTaskPosition).getId());
        listAdapter.remove(tasks.get(chosenTaskPosition));
    }

    public void setTextViews(TextView name, TextView description) {
        String taskName = tasks.get(chosenTaskPosition).getName();
        String taskDescription = tasks.get(chosenTaskPosition).getDescription();
        name.setText(taskName);
        description.setText(taskDescription);
    }

    public void setEditTexts(EditText name, EditText description) {
        String taskName = tasks.get(chosenTaskPosition).getName();
        String taskDescription = tasks.get(chosenTaskPosition).getDescription();
        name.setText(taskName);
        description.setText(taskDescription);
    }
}






