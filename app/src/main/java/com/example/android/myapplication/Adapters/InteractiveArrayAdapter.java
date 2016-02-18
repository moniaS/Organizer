package com.example.android.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.android.myapplication.ObjectClasses.ToDoItem;
import com.example.android.myapplication.R;

import java.util.List;

/**
 * Created by Monia on 2016-02-17.
 */
public class InteractiveArrayAdapter extends ArrayAdapter <ToDoItem> {

    Context context;
    List <ToDoItem> listToDoItems;

    public InteractiveArrayAdapter(Context context, int resource, int textViewResourceId, List<ToDoItem> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.listToDoItems = objects;
    }


    static class ViewHolder {
        protected TextView tv_name;
        protected TextView tv_descripiton;
        protected CheckBox checkbox;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_to_do_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_task_name);
            viewHolder.tv_descripiton = (TextView) view.findViewById(R.id.tv_task_description);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.cb_task_item);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            ToDoItem element = (ToDoItem) viewHolder.checkbox
                                    .getTag();
                            element.setSelected(buttonView.isChecked());

                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(listToDoItems.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(listToDoItems.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tv_name.setText(listToDoItems.get(position).getName());
        holder.tv_descripiton.setText(listToDoItems.get(position).getDescription());
        holder.checkbox.setChecked(listToDoItems.get(position).isSelected());
        return view;
    }
}

