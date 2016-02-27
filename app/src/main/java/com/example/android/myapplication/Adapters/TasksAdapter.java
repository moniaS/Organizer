package com.example.android.myapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.ObjectClasses.Task;
import com.example.android.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Monia on 2016-02-17.
 */
public class TasksAdapter extends ArrayAdapter <Task> {

    private Activity context;
    List <Task> tasks;
    ViewHolder viewHolder;
    int isSelected;

    public TasksAdapter(Activity context, List<Task> tasks) {
        super(context, R.layout.list_to_do_item, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    static class ViewHolder {
        protected TextView tv_name;
        protected TextView tv_descripiton;
        protected CheckBox checkbox;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ;
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.list_to_do_item, null, true);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) rowView.findViewById(R.id.tv_task_name);
            viewHolder.tv_descripiton = (TextView) rowView.findViewById(R.id.tv_task_description);
            viewHolder.checkbox = (CheckBox) rowView.findViewById(R.id.cb_task_item);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        final Task task = tasks.get(position);
        viewHolder.tv_name.setText(task.getName());
        viewHolder.tv_descripiton.setText(task.getDescription());
        if(task.isCompleted())
            viewHolder.checkbox.setChecked(true);
        else
            viewHolder.checkbox.setChecked(false);
        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    task.setCompleted(true);
                }
                else {
                    task.setCompleted(false);                }
                }
        });
        return rowView;
    }
    @Override
    public boolean areAllItemsEnabled(){
        return true;
    }

    @Override
    public boolean isEnabled(int arg0){
        return true;
    }
}

