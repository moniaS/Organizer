package com.example.android.myapplication.Fragments;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
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
    private Button btn_saveChanges;
    private Button btn_cancel;
    private CheckBox cb_taskStatus;
    private ViewSwitcher viewSwitcherName;
    private ViewSwitcher viewSwitcherDescription;
    private Toolbar toolbar;
    TasksFragment targetFragment;
    View view;

    public interface DetailsTaskFragmentListener{
        void onFinishDetailsTaskDialog(String name, String description, boolean completed);
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            MainActivity activity = (MainActivity) getActivity();
            closeDialog();
            return true;
        }
        return false;
    }

    private void closeDialog () {
        String name = tv_name.getText().toString();
        String description = tv_description.getText().toString();
        boolean isChecked = cb_taskStatus.isChecked();
        targetFragment.onFinishDetailsTaskDialog(name, description, isChecked);
        this.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_details_dialog, container);
        setNoTitleForDialog();
        initUserInterface();
        return view;
    }

    private void setTargetFragmentForDialog () {
        targetFragment = (TasksFragment) getTargetFragment();
    }

    private void setNoTitleForDialog () {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    private void initUserInterface () {
        initToolbar();
        setTargetFragmentForDialog();
        setTextViews();
        setEditViews();
        setCheckbox();
        initViewSwitcher();
        setButtonsWhileEditing();
    }

    private void initToolbar () {
        createToolbar();
        setToolbarOptions();
        addOnToolbarMenuOptionsListener();
    }

    private void createToolbar () {
        toolbar = (Toolbar) view.findViewById(R.id.task_details_toolbar);
        toolbar.inflateMenu(R.menu.menu_task_details);
    }
    private void addOnToolbarMenuOptionsListener () {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.action_edit):
                        addOnEditOptionListener();
                        break;
                    case (R.id.action_delete):
                        addOnDeleteOptionListener();
                        break;
                }
                return false;
            }
        });
    }
    private void addOnEditOptionListener () {
        setViewSwitcher();
        showKeyboardForEdittext();
        changeDialogViewToEditTaskView();
    }

    private void addOnDeleteOptionListener () {
        targetFragment.deleteTask();
        dismiss();
    }

    private void setToolbarOptions () {
        toolbar.setTitle(R.string.title_TaskDetailsDialog);
        setToolbarNavigationIcon();
    }

    private void setToolbarNavigationIcon () {
        toolbar.setNavigationIcon(R.mipmap.icon_back1);
        setToolbarNavigationIconListener();
    }
    private void setToolbarNavigationIconListener () {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_saveChanges.getVisibility() == View.VISIBLE) {
                    changeDialogViewAfterCancel();
                } else {
                    targetFragment.onFinishDetailsTaskDialog(tv_name.getText().toString(), tv_description.getText().toString(), cb_taskStatus.isChecked());
                    dismiss();
                }
            }
        });}

    private void setButtonsWhileEditing () {
        setSaveButton();
        setCancelButton();
        setButtonsVisibility(View.GONE);
    }

    private void setSaveButton () {
        btn_saveChanges = (Button) view.findViewById(R.id.imgbtn_saveChanges);
        setSaveButtonListener();
    }

    private void setSaveButtonListener () {
        btn_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDialogViewAfterEdit();
            }
        });
    }

    private void setCancelButton () {
        btn_cancel = (Button) view.findViewById(R.id.imgbtn_cancel);
        setCancelButtonListener();
    }

    private void setCancelButtonListener () {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDialogViewAfterCancel();
            }
        });
    }
    private void changeDialogViewAfterCancel () {
        setButtonsVisibility(View.GONE);
        setToolbarMenuOptionsVisibility(true);
        changeSwitcherViews();
        changeCheckboxClickableStatus(false);
    }

    private void setButtonsVisibility (int isVisible) {
        btn_saveChanges.setVisibility(isVisible);
        btn_cancel.setVisibility(isVisible);
    }

    private void setToolbarMenuOptionsVisibility (boolean isVisible) {
        toolbar.getMenu().setGroupVisible(R.id.group_menu, isVisible);
    }
    private void initViewSwitcher () {
        viewSwitcherName = (ViewSwitcher) view.findViewById(R.id.vs_name);
        viewSwitcherDescription = (ViewSwitcher) view.findViewById(R.id.vs_description);
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

        changeDialogToolbarWhileEdit();
        changeSwitcherViews();
        changeCheckboxClickableStatus(true);
        setButtonsVisibility(View.VISIBLE);
    }

    private void changeDialogToolbarWhileEdit() {
        setToolbarMenuOptionsVisibility(false);
        setButtonsWhileEditing();
    }
    private void showKeyboard(EditText name) {
        name.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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

    private void setTaskValuesAfterEditing () {
        tv_name.setText(et_name.getText().toString());
        tv_description.setText(et_description.getText().toString());
        cb_taskStatus.setChecked(cb_taskStatus.isChecked());
    }

    private void setTextViews() {
        setTargetFragmentForDialog();
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
