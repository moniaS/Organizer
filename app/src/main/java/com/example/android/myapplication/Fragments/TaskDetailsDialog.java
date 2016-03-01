package com.example.android.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.android.myapplication.Activity.MainActivity;
import com.example.android.myapplication.ObjectClasses.Task;
import com.example.android.myapplication.R;

public class TaskDetailsDialog extends android.support.v4.app.DialogFragment implements TextView.OnEditorActionListener{

    private TextView tv_name;
    private TextView tv_description;
    private TextView tv_status;
    private EditText et_name;
    private EditText et_description;
    private Button btn_back;
    private Button btn_edit;
    private Button btn_delete;
    private Button btn_saveChanges;
    private CheckBox cb_taskStatus;
    private ViewSwitcher viewSwitcherName;
    private ViewSwitcher viewSwitcherDescription;
    TasksFragment targetFragment;
    View view;

    public interface DetailsTaskFragmentListener{
        void onFinishDetailsTaskDialog(String name, String description, Boolean completed);
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            MainActivity activity = (MainActivity) getActivity();
            targetFragment.onFinishDetailsTaskDialog(tv_name.getText().toString(), tv_description.getText().toString(), cb_taskStatus.isChecked());
            this.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_details_dialog, container);
        initViewSwitcher();
        targetFragment = (TasksFragment) getTargetFragment();
        setDialogTitle("@string/details_dialog_title");
        setUserInterace();
        return view;
    }

    private void initViewSwitcher () {
        viewSwitcherName = (ViewSwitcher) view.findViewById(R.id.vs_name);
        viewSwitcherDescription = (ViewSwitcher) view.findViewById(R.id.vs_description);
    }
    private void setDialogTitle (String string) {
        getDialog().setTitle(string);
    }
    private void setUserInterace () {
        addButtons();
        setTextViews();
        setEditViews();
        setCheckbox();
    }
    private void addButtons() {
        setBackButton();
        setEditButton();
        setDeleteButton();
    }

    private void setBackButton() {
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetFragment.onFinishDetailsTaskDialog(tv_name.getText().toString(), tv_description.getText().toString(), cb_taskStatus.isChecked());
                dismiss();
            }
        });
    }

    private void setEditButton() {
        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewSwitcher();
                showKeyboardForEdittext();
                changeDialogViewToEditTaskView();
            }
        });
    }
    private void setViewSwitcher () {
        new AnimationUtils();
        viewSwitcherName.setAnimation(AnimationUtils.makeInAnimation
                (getContext(), true));
    }

    private void showKeyboardForEdittext() {
        EditText et_name = (EditText) viewSwitcherName.getNextView();
        showKeyboard(et_name);
    }
    private void changeDialogViewToEditTaskView() {
        changeSwitcherViews();
        changeCheckboxClickableStatus(true);
        setButtonsVisibilityWhileEditing();
    }
    private void showKeyboard(EditText name) {
        name.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    private void setButtonsVisibilityWhileEditing () {
        btn_edit.setVisibility(View.GONE);
        btn_delete.setVisibility(View.GONE);
        setSaveButton();
        btn_saveChanges.setVisibility(View.VISIBLE);
    }
    private void changeCheckboxClickableStatus(boolean clickable) {
        cb_taskStatus.setClickable(clickable);
    }
    private void setSaveButton() {
        btn_saveChanges = (Button) view.findViewById(R.id.btn_saveChanges);
        btn_saveChanges.setVisibility(View.INVISIBLE);
        btn_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDialogViewAfterEdit();
            }
        });
    }

    private void changeDialogViewAfterEdit() {
        setButtonsVisibility();
        changeSwitcherViews();
        setTaskValuesAfterEditing();
        changeCheckboxClickableStatus(false);
    }
    private void setDeleteButton() {
        btn_delete = (Button) view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetFragment.deleteTask();
                dismiss();
            }
        });
    }

    private void setButtonsVisibility() {
        btn_edit.setVisibility(View.VISIBLE);
        btn_delete.setVisibility(View.VISIBLE);
        btn_saveChanges.setVisibility(View.GONE);
    }

    private void changeSwitcherViews() {
        viewSwitcherName.showNext();
        viewSwitcherDescription.showNext();
    }

    private void setTaskValuesAfterEditing () {
        tv_name.setText(et_name.getText().toString());
        tv_description.setText(et_description.getText().toString());
        cb_taskStatus.setChecked(cb_taskStatus.isChecked());
    }

    private void setTextViews() {
        tv_name = (TextView) view.findViewById(R.id.tv_edit_name);
        tv_description = (TextView) view.findViewById(R.id.tv_edit_description);
        tv_status = (TextView) view.findViewById(R.id.tv_edit_status);
        targetFragment.setTextViews(tv_name, tv_description);
    }

    private void setEditViews() {
        et_name = (EditText) view.findViewById(R.id.et_edit_name);
        et_description = (EditText) view.findViewById(R.id.et_edit_description);
        targetFragment.setEditTexts(et_name, et_description);
    }

    private void setCheckbox () {
        cb_taskStatus = (CheckBox) view.findViewById(R.id.cb_task_status);
        cb_taskStatus.setClickable(false);
        targetFragment.setCheckbox(cb_taskStatus);
    }

    public Task getTask(){
       return new Task(et_name.getText().toString(), et_description.getText().toString());
    }
}
