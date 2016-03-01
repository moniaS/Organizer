package com.example.android.myapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.Fragments.TasksFragment;
import com.example.android.myapplication.ObjectClasses.Task;
import com.example.android.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Monia on 2016-02-17.
 */
public class TasksAdapter extends ArrayAdapter <Task> {

    private Activity context;
    private View rowView;
    private List <Task> tasks;
    private ViewHolder viewHolder;
    private TasksFragment targetFragment;

    public TasksAdapter(Activity context, List<Task> tasks, TasksFragment fragment) {
        super(context, R.layout.list_to_do_item, tasks);
        this.context = context;
        this.tasks = tasks;
        this.targetFragment = fragment;
    }

    static class ViewHolder {
        protected TextView tv_name;
        protected CheckBox checkbox;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        rowView = convertView;
        if(rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.list_to_do_item, null, true);
            createViewHolder();
        }
        else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        setViewHolder(position);
        return rowView;
    }

    private void createViewHolder() {
        viewHolder = new ViewHolder();
        viewHolder.tv_name = (TextView) rowView.findViewById(R.id.tv_task_name);
        viewHolder.checkbox = (CheckBox) rowView.findViewById(R.id.cb_task_item);
        rowView.setTag(viewHolder);
    }
    private void setViewHolder(int position) {
        viewHolder.tv_name.setText(tasks.get(position).getName());
        setViewHolderCheckbox(position);
    }

    private void setViewHolderCheckbox(int position) {
        if(tasks.get(position).isCompleted())
            viewHolder.checkbox.setChecked(true);
        else
            viewHolder.checkbox.setChecked(false);
        setViewHolderCheckboxListener(position);
    }

    private void setViewHolderCheckboxListener (final int position) {
        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tasks.get(position).setCompleted(true);
                }
                else {
                    tasks.get(position).setCompleted(false);
                }

                targetFragment.updateTask(tasks.get(position));
            }
        });
    }
}

