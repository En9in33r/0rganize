package com.x_c0re.a0rganize;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NewTaskFragment extends Fragment
{
    private Button mButton;
    private EditText mEditText;

    FragmentManager manager;
    Fragment fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.new_task_fragment, container, false);
        mEditText = view.findViewById(R.id.editText);

        mButton = view.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MainActivity.message = mEditText.getText().toString();
                CurrentTasksFragment.data.add(MainActivity.message);

                Toast toast = Toast.makeText(getContext(), "Succesfully!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        return view;
    }
}
