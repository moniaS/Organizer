package com.example.android.myapplication.ObjectClasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Monia on 2016-02-15.
 */
public class ToDoItem {
    String name;
    String description;
    Date date;
    boolean selected;

    public ToDoItem(String name, String description)
    {
        this.name = name;
        this.description = description;
        selected = false;
    }

    public String getName() { return name; }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {

        return this.name + " " + this.description;
    }
}
