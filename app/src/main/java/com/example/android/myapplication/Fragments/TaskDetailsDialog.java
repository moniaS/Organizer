package com.example.android.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.android.myapplication.Interfaces.MyEditTaskListener;
import com.example.android.myapplication.ObjectClasses.Task;
import com.example.android.myapplication.R;

public class TaskDetailsDialog extends android.support.v4.app.DialogFragment implements AlertDialogOnDeleteTask.DeleteTaskListener {

    private TextView tv_name;
    private TextView tv_description;
    private EditText et_name;
    private EditText et_description;
    private Button btn_saveChanges;
    private Button btn_cancel;
    private CheckBox cb_taskStatus;
    private ViewSwitcher viewSwitcherName;
    private ViewSwitcher viewSwitcherDescription;
    private Toolbar toolbar;
    private MyEditTaskListener mEditTaskListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_details_dialog, container);
        setNoTitleForDialog();
        initToolbar(view);
        initUserInterface(view);
        return view;
    }

    private void setTargetFragmentForDialog() {
        mEditTaskListener = (MyEditTaskListener) getTargetFragment();
    }

    private void setNoTitleForDialog() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    private void initUserInterface(View view) {
        setTargetFragmentForDialog();
        setTextViews(view);
        setEditViews(view);
        setCheckbox(view);
        initViewSwitcher(view);
        setButtonsWhileEditing(view);
    }

    private void initToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.task_details_toolbar);
        toolbar.inflateMenu(R.menu.menu_task_details);
        toolbar.setTitle(R.string.title_TaskDetailsDialog);
        setToolbarNavigationIcon();
        onToolbarMenuOptionsListener();
    }

    private void onToolbarMenuOptionsListener() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.action_edit):
                        onEditOptionListener(toolbar.getRootView());
                        break;
                    case (R.id.action_delete):
                        FragmentTransaction ft = getFragmentManager().beginTransaction();

                        AlertDialogOnDeleteTask alertDialogOnDeleteTask = new AlertDialogOnDeleteTask(TaskDetailsDialog.this);
                        alertDialogOnDeleteTask.show(ft);
                        break;
                }
                return false;
            }
        });
    }

    private void onEditOptionListener(View view) {
        changeDialogViewToEditTaskView(view);
    }

    private void deleteTask() {
        Toast.makeText(getContext(), "in delete", Toast.LENGTH_SHORT).show();
        mEditTaskListener.onDeleteTask();
        dismiss();
    }

    private void setToolbarNavigationIcon() {
        toolbar.setNavigationIcon(R.mipmap.icon_back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_saveChanges.getVisibility() == View.VISIBLE) {
                    changeDialogViewAfterCancel();
                } else {
                    mEditTaskListener.onEditTask(tv_name.getText().toString(), tv_description.getText().toString(), cb_taskStatus.isChecked());
                    dismiss();
                }
            }
        });
    }

    private void setButtonsWhileEditing(View view) {
        setSaveButton(view);
        setCancelButton(view);
        setButtonsVisibility(View.GONE);
    }

    private void setSaveButton(View view) {
        btn_saveChanges = (Button) view.findViewById(R.id.imgbtn_saveChanges);
        setSaveButtonListener();
    }

    private void setSaveButtonListener() {
        btn_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().matches("")) {
                    Toast.makeText(getContext(), "You did not enter a task's name", Toast.LENGTH_SHORT).show();
                } else {
                    hideKeyboard();
                    changeDialogViewAfterEdit();
                }
            }
        });
    }

    private void setCancelButton(View view) {
        btn_cancel = (Button) view.findViewById(R.id.imgbtn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDialogViewAfterCancel();
            }
        });
    }

    private void changeDialogViewAfterCancel() {
        setButtonsVisibility(View.GONE);
        setToolbarMenuOptionsVisibility(true);
        changeSwitcherViews();
        changeCheckboxClickableStatus(false);
    }

    private void setButtonsVisibility(int isVisible) {
        btn_saveChanges.setVisibility(isVisible);
        btn_cancel.setVisibility(isVisible);
    }

    private void setToolbarMenuOptionsVisibility(boolean isVisible) {
        toolbar.getMenu().setGroupVisible(R.id.group_menu, isVisible);
    }

    private void initViewSwitcher(View view) {
        viewSwitcherName = (ViewSwitcher) view.findViewById(R.id.vs_name);
        viewSwitcherDescription = (ViewSwitcher) view.findViewById(R.id.vs_description);
    }


    private void changeDialogViewToEditTaskView(View view) {

        changeDialogToolbarWhileEdit(view);
        changeSwitcherViews();
        changeCheckboxClickableStatus(true);
        setButtonsVisibility(View.VISIBLE);
        et_name.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void changeDialogToolbarWhileEdit(View view) {
        setToolbarMenuOptionsVisibility(false);
        setButtonsWhileEditing(view);
    }

    private void changeCheckboxClickableStatus(boolean clickable) {
        cb_taskStatus.setClickable(clickable);
    }

    private void changeDialogViewAfterEdit() {
        setButtonsVisibility(View.GONE);
        setToolbarMenuOptionsVisibility(true);
        changeSwitcherViews();
        setTaskValuesAfterEditing();
        changeCheckboxClickableStatus(false);
    }

    private void changeSwitcherViews() {
        viewSwitcherName.showNext();
        viewSwitcherDescription.showNext();
    }

    private void setTaskValuesAfterEditing() {
        tv_name.setText(et_name.getText().toString());
        tv_description.setText(et_description.getText().toString());
        cb_taskStatus.setChecked(cb_taskStatus.isChecked());
    }

    private void setTextViews(View view) {
        setTargetFragmentForDialog();
        tv_name = (TextView) view.findViewById(R.id.tv_edit_name);
        tv_description = (TextView) view.findViewById(R.id.tv_edit_description);
        mEditTaskListener.setTextViews(tv_name, tv_description);    }

    private void setEditViews(View view) {
        et_name = (EditText) view.findViewById(R.id.et_edit_name);
        et_description = (EditText) view.findViewById(R.id.et_edit_description);
        mEditTaskListener.setEditTexts(et_name, et_description);

    }

    private void setCheckbox(View view) {
        cb_taskStatus = (CheckBox) view.findViewById(R.id.cb_task_status);
        cb_taskStatus.setClickable(false);
        mEditTaskListener.setCheckbox(cb_taskStatus);
    }

    public Task getTask() {
        return new Task(et_name.getText().toString(), et_description.getText().toString());
    }
    private void hideKeyboard () {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onTaskDeleted() {
        deleteTask();
    }
}
