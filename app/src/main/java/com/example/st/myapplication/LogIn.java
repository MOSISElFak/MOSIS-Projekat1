package com.example.st.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    private TextView LEmail;
    private TextView LPassword;
    private Button LLogIn;
    private Button LRegister;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        LEmail= (TextView) findViewById(R.id.lEmail);
        LPassword= (TextView) findViewById(R.id.lPassword);
        LLogIn= (Button) findViewById(R.id.lLogIn);
        LRegister= (Button) findViewById(R.id.lRegister);
        firebaseAuth= FirebaseAuth.getInstance();



        LLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(LEmail.getText().toString()))
                {
                    Toast.makeText(LogIn.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(LPassword.getText().toString()))
                {
                    Toast.makeText(LogIn.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(LEmail.getText().toString(), LPassword.getText().toString())
                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(LogIn.this, "signInWithEmail:onComplete:" + task.isSuccessful(),Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    Toast.makeText(LogIn.this, "Faliure!!!:",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        LRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }
}
