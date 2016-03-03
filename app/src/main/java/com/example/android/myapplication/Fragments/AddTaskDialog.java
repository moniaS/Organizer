package com.example.android.myapplication.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.myapplication.Activity.MainActivity;
import com.example.android.myapplication.R;

public class AddTaskDialog extends android.support.v4.app.DialogFragment implements TextView.OnEditorActionListener{

    private View view;
    private EditText taskName;
    private EditText taskDescription;
    private Button btnSaveTask;
    private Button btnCancelTask;
    private TasksFragment targetFragment;
    private Toolbar toolbar;

    public interface NewTaskFragmentListener {
        void onFinishAddTaskDialog(String name, String description);
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            targetFragment.onFinishAddTaskDialog(taskName.getText().toString(), taskDescription.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }

    public AddTaskDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_add_task, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initUserInterface();
        return view;
    }

    private void  initUserInterface () {
        initToolbar();
        initEditTexts();
        initButtons();
        showKeyboard(taskName);
    }

    private void initToolbar () {
        createToolbar();
        setToolbarOptions();
    }

    private void createToolbar () {
        toolbar = (Toolbar) view.findViewById(R.id.task_adding_toolbar);
    }

    private void setToolbarOptions () {
        toolbar.inflateMenu(R.menu.menu_task_adding);
        toolbar.setTitle(R.string.title_AddTaskDialog);
        toolbar.setNavigationIcon(R.mipmap.icon_back1);
    }
    private void initEditTexts() {
        taskName = (EditText) view.findViewById(R.id.txt_new_task_name);
        taskDescription = (EditText) view.findViewById(R.id.txt_new_task_description);
    }

    private void initButtons () {
        initSaveButton();
        initCancelButton();
    }

    private void initSaveButton () {
        btnSaveTask = (Button) view.findViewById(R.id.btn_saveTask);
        setSaveButtonOnClickListener();
    }

    private void setSaveButtonOnClickListener () {
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetFragment = (TasksFragment) getTargetFragment();
                targetFragment.onFinishAddTaskDialog(taskName.getText().toString(), taskDescription.getText().toString());
                dismiss();
            }
        });
    }

    private void initCancelButton () {
        btnCancelTask = (Button) view.findViewById(R.id.btn_cancel);
        btnCancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void showKeyboard(EditText editText) {
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
