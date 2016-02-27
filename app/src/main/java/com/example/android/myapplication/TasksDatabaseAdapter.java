package com.example.android.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.myapplication.ObjectClasses.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monia on 2016-02-23.
 */
public class TasksDatabaseAdapter {

    private static final String DEBUG_TAG = "SqLiteTodoManager";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static final String TASKS_TABLE = "tasks";
    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KEY_NAME = "name";
    public static final int NAME_COLUMN = 1;
    public static final String NAME_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_DESCRIPTION = "description";
    public static final int DESCRIPTION_COLUMN = 2;
    public static final String DESCRIPTION_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_COMPLETED= "completed";
    public static final String COMPLETED_OPTIONS = "INTEGER DEFAULT 0";
    public static final int COMPLETED_COLUMN = 3;

    private static final String DB_CREATE_TASKS_TABLE =
            "CREATE TABLE " + TASKS_TABLE + "( " +
                    KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_NAME + " " + NAME_OPTIONS + ", " +
                    KEY_DESCRIPTION + " " + DESCRIPTION_OPTIONS +
                    KEY_COMPLETED + " " + COMPLETED_OPTIONS +
                    ");";
    private static final String DROP_TODO_TABLE =
            "DROP TABLE IF EXISTS " + TASKS_TABLE;

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    public TasksDatabaseAdapter(Context context) {
        this.context = context;
    }

    public TasksDatabaseAdapter open(){
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TASKS_TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()){
            int id = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1), description = cursor.getString(2);
            int isCompleted = cursor.getInt(3);
            boolean completed;
            if(isCompleted == 1 )
                completed = true;
            else
                completed = false;

            Task task = new Task(id, name, description, completed);
            taskList.add(task);
        }
        return taskList;
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor getAllTasksFromDatabase() {
        String[] columns = {KEY_ID, KEY_NAME, KEY_DESCRIPTION};
        return db.query(TASKS_TABLE, columns, null, null, null, null, null);
    }

    public void insertTask(String name, String description) {
        ContentValues newTaskValues = new ContentValues();
        open();
        newTaskValues.put(KEY_NAME, name);
        newTaskValues.put(KEY_DESCRIPTION, description);
        db.insert(TASKS_TABLE, null, newTaskValues);
        db.close();
    }

    public boolean updateTask(Task task) {
        int id = task.getId();
        String name = task.getName();
        String description = task.getDescription();
        boolean completed = task.isCompleted();
        return updateTask(id, name, description, completed);
    }

    public boolean deleteTodo(long id){
        open();
        String where = KEY_ID + "=" + id;
        return db.delete(TASKS_TABLE, where, null) > 0;
    }

    public boolean updateTask(int id, String name, String description, boolean completed) {
        String where = KEY_ID + "=" + id;
        int completedTask = completed ? 1 : 0;
        ContentValues updateTaskValues = new ContentValues();
        updateTaskValues.put(KEY_NAME, name);
        updateTaskValues.put(KEY_DESCRIPTION, description);
        updateTaskValues.put(KEY_COMPLETED, completedTask);
        return db.update(TASKS_TABLE, updateTaskValues, where, null) > 0;
    }

    public Cursor getAllTasksFromDb() {
        String[] columns = {KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_COMPLETED};
        return db.query(TASKS_TABLE, columns, null, null, null, null, null);
    }

    public Task getTask(int id) {
        String[] columns = {KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_COMPLETED};
        String where = KEY_ID + "=" + id;
        Cursor cursor = db.query(TASKS_TABLE, columns, where, null, null, null, null);
        Task task = null;
        if(cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(NAME_COLUMN);
            String description = cursor.getString(DESCRIPTION_COLUMN);
            boolean completed = cursor.getInt(COMPLETED_COLUMN) > 0 ? true : false;
            task = new Task(id, name, description, completed);
        }
        return task;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {


        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_TASKS_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + TASKS_TABLE + " ver." + DATABASE_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TODO_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + TASKS_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }
}