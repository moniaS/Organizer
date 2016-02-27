package com.example.android.myapplication.Fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.myapplication.Adapters.TasksAdapter;
import com.example.android.myapplication.R;
import com.example.android.myapplication.ObjectClasses.Task;
import com.example.android.myapplication.TasksDatabaseAdapter;

import java.util.List;

public class TasksFragment extends Fragment implements AddTaskDialog.NewTaskFragmentListener {

    private ListView lv_tasks;
    private TasksDatabaseAdapter tasksDbAdapter;
    private Cursor todoCursor;
    private List<Task> tasks;
    private TasksAdapter listAdapter;
    private DetailsTaskListItemDialog detailsTaskListItemDialog;
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
        tasks = tasksDbAdapter.getAllTasks();
      //  getAllTasks();
       // initListViewOnItemClick();
        listAdapter = new TasksAdapter (this.getActivity(), tasks);
        lv_tasks.setAdapter(listAdapter);
        lv_tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_LONG).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Task Details");
                alert.setMessage(tasks.get(position).toString());
                alert.setPositiveButton(R.string.btn_OK,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                                dialog.dismiss();

                            }
                        });
                alert.setNeutralButton(R.string.btn_delete,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                tasksDbAdapter.deleteTodo(tasks.get(position).getId());
                                listAdapter.notifyDataSetChanged();
                                dialog.dismiss();

                            }
                        });
                alert.setNegativeButton(R.string.btn_back,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                                dialog.dismiss();

                            }
                        });
                alert.show();
            }
        });
        return view;
    }

  /*  private void getAllTasks() {
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
                tasks.add(new Task(name, description));
            } while(todoCursor.moveToNext());
        }
    }*/
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
  @Override
  public void onFinishEditDialog(String name, String description) {
      this.addTaskToList(name, description, 0);

  }
    public void addTaskToList(String name, String description, int checked) {
        tasks.add(new Task(name, description));
        tasksDbAdapter.insertTask(name, description);
        listAdapter.notifyDataSetChanged();
    }

    private void initListViewOnItemClick() {

        lv_tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                final int itemPosition = position;
                CheckBox checkBox = (CheckBox) getView().findViewById(R.id.cb_task_item);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Task task = tasks.get(itemPosition);
                        if (task.isCompleted()) {
                            tasksDbAdapter.updateTask(task.getId(), task.getName(), task.getDescription(), false);
                        } else {
                            tasksDbAdapter.updateTask(task.getId(), task.getName(), task.getDescription(), true);
                        }
                    }
                });
                        updateListViewData();
            }
        });
    }
    private void updateListViewData() {
            todoCursor.requery();
            tasks.clear();
            updateTaskList();
            listAdapter.notifyDataSetChanged();
        }
    private void updateTaskList() {
        if(todoCursor != null && todoCursor.moveToFirst()) {
            do {
                int id = todoCursor.getInt(tasksDbAdapter.ID_COLUMN);
                String name = todoCursor.getString(tasksDbAdapter.NAME_COLUMN);
                String description = todoCursor.getString(tasksDbAdapter.DESCRIPTION_COLUMN);
                boolean completed = todoCursor.getInt(tasksDbAdapter.COMPLETED_COLUMN) > 0 ? true : false;
                tasks.add(new Task(id, name, description, completed));
            } while(todoCursor.moveToNext());
        }
    }
    }






