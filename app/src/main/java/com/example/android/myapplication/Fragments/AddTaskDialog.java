package com.example.android.myapplication.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.Interfaces.MyNewTaskListener;
import com.example.android.myapplication.R;

import java.util.Calendar;

public class AddTaskDialog extends android.support.v4.app.DialogFragment {

    private EditText taskName;
    private EditText taskDescription;
    private Button btnSaveTask;
    private Button btnCancelTask;
    private Button btnAddDate;
    private MyNewTaskListener mAddTaskDialogListener;
    private DatePicker datePicker;
    private TextView tvDisplayDate;
    private TextView taskDate;
    private DatePicker dpResult;
    private Button btnChangeDate;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;


    public AddTaskDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAddTaskDialogListener = (MyNewTaskListener) getTargetFragment();
        View view = inflater.inflate(R.layout.dialog_add_task, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initUserInterface(view);
        return view;
    }

    private void initUserInterface(View view) {
        initToolbar(view);
        initEditTexts(view);
        initButtons(view);
        showKeyboard(taskName);
    }

    private void initToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.task_adding_toolbar);
        toolbar.inflateMenu(R.menu.menu_task_adding);
        toolbar.setTitle(R.string.title_AddTaskDialog);
        toolbar.setNavigationIcon(R.mipmap.icon_back1);
    }

    private void initEditTexts(View view) {
        taskName = (EditText) view.findViewById(R.id.txt_new_task_name);
        taskDescription = (EditText) view.findViewById(R.id.txt_new_task_description);
        tvDisplayDate = (TextView) view.findViewById(R.id.tv_chosen_new_task_date);
        taskDate = (TextView) view.findViewById(R.id.tv_new_task_date);
    }

    private void initButtons(View view) {
        initSaveButton(view);
        initCancelButton(view);
        initAddDateButton(view);
    }

    private void initSaveButton(View view) {
        btnSaveTask = (Button) view.findViewById(R.id.btn_saveTask);
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTaskName = taskName.getText().toString().trim();
                if (!newTaskName.isEmpty()) {
                    mAddTaskDialogListener.onNewTask(newTaskName, taskDescription.getText().toString().trim());
                    dismiss();
                } else {
                    hideKeyboard();
                    Toast.makeText(getContext(), "You did not enter a task's name!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initCancelButton(View view) {
        btnCancelTask = (Button) view.findViewById(R.id.btn_cancel);
        btnCancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initAddDateButton (View view) {
        btnAddDate = (Button) view.findViewById(R.id.btn_addDate);
        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(getContext(), year+monthOfYear+dayOfMonth, Toast.LENGTH_LONG).show();
                    }
                }, 2016, 3, 9);
                dialog.show();
            }
        });
    }
    private void showKeyboard(EditText editText) {
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void hideKeyboard() {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setCurrentDateOnView(View view) {

        tvDisplayDate.setVisibility(View.VISIBLE);
        taskDate.setVisibility(View.VISIBLE);
        //dpResult = (DatePicker) view.findViewById(R.id.dp_taskDate);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        // set current date into datepicker
        //dpResult.init(year, month, day, null);

    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

        }
    };

}
