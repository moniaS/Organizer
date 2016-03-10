package com.example.android.myapplication.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
    public void onFinishAddTaskDialog(String name, String description) {
        this.addTaskToList(name, description);
    }

    private void updateTaskValues (Task task){
        tasks.get(chosenTaskPosition).setName(task.getName());
        tasks.get(chosenTaskPosition).setDescription(task.getDescription());
        tasks.get(chosenTaskPosition).setCompleted(task.isCompleted());
    }

    private void updateTaskInDbAdapter () {
        dbTasksAdapter.updateTask(tasks.get(chosenTaskPosition).getId(),
                tasks.get(chosenTaskPosition).getName(),
                tasks.get(chosenTaskPosition).getDescription(),
                tasks.get(chosenTaskPosition).isCompleted());
    }

    private void initListView() {
        lv_tasks = (ListView) view.findViewById(R.id.lv_to_do_items);
        dbTasksAdapter = new TasksDatabaseAdapter(getContext());
        tasks = dbTasksAdapter.getAllTasks();
        listAdapter = new TasksAdapter (this.getActivity(), tasks, this);
        lv_tasks.setAdapter(listAdapter);
        onChosenTaskClickListener();
    }

    private void onChosenTaskClickListener(){
        lv_tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                chosenTaskPosition = position;
                activity.initTaskDetailsDialog();
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

    public void updateTaskList() {
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

    public void setCheckbox (CheckBox taskStatus) {
        Boolean isChecked = tasks.get(chosenTaskPosition).isCompleted();
        taskStatus.setChecked(isChecked);
    }

    public void clearCompletedTasks() {
        if (todoCursor != null) {
            Log.d("tag", "w clear");
            while (todoCursor.moveToNext()) {
                if (todoCursor.getInt(dbTasksAdapter.COMPLETED_COLUMN) == 1) {
                    int id = todoCursor.getInt(dbTasksAdapter.ID_COLUMN);
                    dbTasksAdapter.deleteTask(id);
                }
            }
        }
    }

    @Override
    public void onFinishDetailsTaskDialog(String name, String description, boolean completed) {

        Task task = new Task(name, description, completed);
        updateTaskValues(task);
        updateTaskInDbAdapter();
        updateTaskList();
        listAdapter.notifyDataSetChanged();

    }
}






