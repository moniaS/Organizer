package com.example.android.myapplication.Fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.android.myapplication.Activity.MainActivity;
import com.example.android.myapplication.ObjectClasses.Task;
import com.example.android.myapplication.R;

public class TaskDetailsDialog extends android.support.v4.app.DialogFragment implements TextView.OnEditorActionListener{

    private TextView tv_name;
    private TextView tv_description;
    private EditText et_name;
    private EditText et_description;
    private Button btn_back;
    private Button btn_edit;
    private Button btn_delete;
    private Button btn_saveChanges;
    private ViewSwitcher viewSwitcherName;
    private ViewSwitcher viewSwitcherDescription;
    TasksFragment targetFragment;
    View view;

    public interface DetailsTaskFragmentListener{
        void onFinishDetailsTaskDialog(String name, String description);
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            MainActivity activity = (MainActivity) getActivity();
            targetFragment.onFinishDetailsTaskDialog(tv_name.getText().toString(), tv_description.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_details_dialog, container);
        viewSwitcherName = (ViewSwitcher) view.findViewById(R.id.vs_name);
        viewSwitcherDescription = (ViewSwitcher) view.findViewById(R.id.vs_description);
        targetFragment = (TasksFragment) getTargetFragment();
        getDialog().setTitle("Task Details");
        addButtons();
        addTextViews();
        addEditViews();
        return view;
    }

    private void addButtons() {
        addBackButton();
        addEditButton();
        addDeleteButton();
        addSaveButton();
    }

    private void addBackButton() {
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void addEditButton() {
        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AnimationUtils();
                viewSwitcherName.setAnimation(AnimationUtils.makeInAnimation
                        (getContext(), true));

                EditText et_name = (EditText) viewSwitcherName.getNextView();
                EditText et_description = (EditText) viewSwitcherDescription.getNextView();

                viewSwitcherName.showNext();
                viewSwitcherDescription.showNext();
                et_name.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(et_name, 1);
                getDialog().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                btn_edit.setVisibility(View.GONE);
                btn_delete.setVisibility(View.GONE);
                btn_saveChanges.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addDeleteButton() {
        btn_delete = (Button) view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetFragment.deleteTask();
                dismiss();
            }
        });
    }

    private void addSaveButton() {
        btn_saveChanges = (Button) view.findViewById(R.id.btn_saveChanges);
        btn_saveChanges.setVisibility(View.INVISIBLE);
        btn_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_edit.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
                btn_saveChanges.setVisibility(View.GONE);

                viewSwitcherName.showNext();
                viewSwitcherDescription.showNext();

                tv_name.setText(et_name.getText().toString());
                tv_description.setText(et_description.getText().toString());
            }
        });
    }

    private void addTextViews() {
        tv_name = (TextView) view.findViewById(R.id.tv_edit_name);
        tv_description = (TextView) view.findViewById(R.id.tv_edit_description);
        targetFragment.setTextViews(tv_name, tv_description);
    }

    private void addEditViews() {
        et_name = (EditText) view.findViewById(R.id.et_edit_name);
        et_description = (EditText) view.findViewById(R.id.et_edit_description);
        targetFragment.setEditTexts(et_name, et_description);
    }

    public Task getTask(){
       return new Task(et_name.getText().toString(), et_description.getText().toString());
    }
}
