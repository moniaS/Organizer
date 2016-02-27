package com.example.android.myapplication.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private CheckBox taskChecked;
    private Button btnSaveTask;
    private Button btnCancelTask;
    private TasksFragment targetFragment;

    public interface NewTaskFragmentListener {
        void onFinishEditDialog(String name, String description);
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            MainActivity activity = (MainActivity) getActivity();
            targetFragment.onFinishEditDialog(taskName.getText().toString(), taskDescription.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }

    public AddTaskDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_add_task, container);
        addEditTexts();
        addButtons();
        getDialog().setTitle("Task Adding");
        showKeyboard(taskName);
        //taskDescription.setOnEditorActionListener(this);

        return view;
    }

    private void addEditTexts() {
        taskName = (EditText) view.findViewById(R.id.txt_new_task_name);
        taskDescription = (EditText) view.findViewById(R.id.txt_new_task_description);
    }

    private  void addButtons(){
        btnSaveTask = (Button) view.findViewById(R.id.btn_saveTask);
        btnCancelTask = (Button) view.findViewById(R.id.btn_cancel);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetFragment = (TasksFragment) getTargetFragment();
                targetFragment.onFinishEditDialog(taskName.getText().toString(), taskDescription.getText().toString());
                dismiss();
            }
        });

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
