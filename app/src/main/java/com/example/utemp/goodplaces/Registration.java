package com.example.utemp.goodplaces;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends Fragment
{
    Button btnRegister;
    EditText ET_email;
    EditText ET_nick;
    EditText ET_password1;
    EditText ET_password2;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        btnRegister = view.findViewById(R.id.reg_reg);
        ET_email = view.findViewById(R.id.login);
        ET_nick = view.findViewById(R.id.nick);
        ET_password1 = view.findViewById(R.id.password1);
        ET_password2 = view.findViewById(R.id.password2);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");


btnRegister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final String email_1 = ET_email.getText().toString();
        String password_1 = ET_password1.getText().toString();

        if (ET_password1.getText().toString().equals(ET_password2.getText().toString()))
        {
            ////////////////////
            mAuth.createUserWithEmailAndPassword(email_1, password_1)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = mAuth.getCurrentUser();

                                        Log.d("FIREB","sdf");

                                        myRef.child(user.getUid()).child("nickname").setValue(ET_nick.getText().toString());
                                        myRef.child(user.getUid()).child("email").setValue(ET_email.getText().toString());


                                        Toast.makeText(getActivity(), "Регистрация успешна",
                                                Toast.LENGTH_SHORT).show();
                                         getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, new Authorization()).commit();

                                    } else
                                    {
                                        Toast.makeText(getActivity(), "Регистрация не удалась",
                                                Toast.LENGTH_SHORT).show();
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, new Authorization()).commit();

                                    }

                                }


                            }
                    );
        }
        else
        {
            Toast.makeText(getActivity(), "Passwords don`t match.",
                    Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, new Authorization()).commit();
        }

    }
});

        return view;
    }
}



