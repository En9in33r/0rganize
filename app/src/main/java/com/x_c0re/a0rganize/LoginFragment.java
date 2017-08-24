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
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment
{
    private EditText mLoginField;
    private EditText mPasswordField;
    private Button mLoginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        TextView mSignInText = view.findViewById(R.id.textViewSignIn);
        mSignInText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), SigninActivity.class);
                startActivity(intent);
            }
        });

        mLoginField = view.findViewById(R.id.editTextLogin);
        mPasswordField = view.findViewById(R.id.editTextPassword);
        mLoginButton = view.findViewById(R.id.buttonLogin);
        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if ((mLoginField.getText().toString().equals("admin")) && (mPasswordField.getText().toString().equals("qwerty123")))
                {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    Toast toast = Toast.makeText(getContext(), "Logged as admin", Toast.LENGTH_LONG);
                    toast.show();
                    startActivity(intent);
                }
                else
                {
                    Toast toast = Toast.makeText(getContext(), "Incorrect login or password", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        return view;
    }
}
