package com.example.android.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.myapplication.ObjectClasses.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Monia on 2016-02-23.
 */
public class TasksDatabaseAdapter extends SQLiteOpenHelper {

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
    private HashMap hashMap;

    public TasksDatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public boolean insertTask  (String name, String description)
    {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        db.insert(TASKS_TABLE, null, contentValues);
        return true;
    }

    public void updateTask(int id, String name, String description, boolean completed) {
        String where = KEY_ID + "=" + id;
        int completedTask = completed ? 1 : 0;
        ContentValues updateTaskValues = new ContentValues();
        updateTaskValues.put(KEY_NAME, name);
        updateTaskValues.put(KEY_DESCRIPTION, description);
        updateTaskValues.put(KEY_COMPLETED, completedTask);
        db.update(TASKS_TABLE, updateTaskValues, where, null);
    }

    public Cursor getTask(int id){
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return cursor;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numberOfRows = (int) DatabaseUtils.queryNumEntries(db, TASKS_TABLE);
        return numberOfRows;
    }
    public boolean updateTask (int id, String name, String description, int completed)
    {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("completed", completed);
        db.update(TASKS_TABLE, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteTask (int id)
    {
        db = this.getWritableDatabase();
        String where = KEY_ID + "=" + id;
        return db.delete(TASKS_TABLE, where, null);
    }
    public List<Task> getAllTasks()
    {
        ArrayList<Task> tasksList = new ArrayList<Task>();
        db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from tasks", null );
        cursor.moveToFirst();

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
            tasksList.add(task);
        }
        return tasksList;
    }
}