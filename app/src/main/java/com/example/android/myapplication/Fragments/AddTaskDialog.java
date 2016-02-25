package com.example.android.myapplication.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.myapplication.Activity.MainActivity;
import com.example.android.myapplication.R;

public class AddTaskDialog extends android.support.v4.app.DialogFragment implements TextView.OnEditorActionListener {

    private EditText taskName;
    private EditText taskDescription;
    private DatePicker taskDate;

    public interface NewTaskFragmentListener {
        void onFinishEditDialog(String name, String description);
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            MainActivity activity = (MainActivity) getActivity();
            activity.onFinishEditDialog(taskName.getText().toString(), taskDescription.getText().toString());
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
        View view = inflater.inflate(R.layout.dialog_add_task, container);
        taskName = (EditText) view.findViewById(R.id.txt_new_task_name);
        taskDescription = (EditText) view.findViewById(R.id.txt_new_task_description);
        getDialog().setTitle("Task Adding");

        showKeyboard(taskName);
        taskDescription.setOnEditorActionListener(this);

        return view;
    }

    private void showKeyboard(EditText editText) {
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

}
