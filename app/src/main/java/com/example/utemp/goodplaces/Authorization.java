package com.example.utemp.goodplaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Authorization extends Fragment {
    Button btnRegister;
    Button btnEnter;
    EditText email;
    EditText password;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authorization, container, false);
        btnRegister = view.findViewById(R.id.reg);
        btnEnter = view.findViewById(R.id.enter);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        btnEnter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email_new=email.getText().toString();
                String password_new=password.getText().toString();

                mAuth.signInWithEmailAndPassword(email_new, password_new)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getActivity(), "Авторизация получилась",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i =new Intent(getActivity(), MapActivity2.class);
                                    startActivity(i);



                                } else
                                {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(getActivity(), "Авторизация не получилась",
                                            Toast.LENGTH_SHORT).show();
                                    ;
                                }

                                // ...
                            }
                        });

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, new Registration()).commit();


            }
        });
        return view;
    }
}
