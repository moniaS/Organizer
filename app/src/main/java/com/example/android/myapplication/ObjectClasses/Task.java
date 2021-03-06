package com.example.android.myapplication.ObjectClasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Monia on 2016-02-15.
 */
public class Task {
    int id;
    String name;
    String description;
    boolean completed;
    static int maxId;

    public Task(String name, String description){
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, boolean isCompleted){
        this.name = name;
        this.description = description;
        this.completed = isCompleted;
    }
    public Task(int id, String name, String description, boolean completed)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
    }

    public String getName() { return name; }

    public String getDescription() {
        return description;
    }

    public int getId(){
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {

        return this.name + " " + this.description;
    }
}
