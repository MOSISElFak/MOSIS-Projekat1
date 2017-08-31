package com.example.st.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private TextView probniTekst;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth firebaseAuth;
    private Button SStartServise;
    private Button SStopService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent poziv = new Intent(getApplicationContext(), MainActivity.class); //Register.class
        //startActivity(poziv);
        probniTekst = (TextView) findViewById(R.id.probaText);
        firebaseAuth=FirebaseAuth.getInstance();
        if(user==null)
        {
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
        }
        else
        {
            probniTekst.setText(user.getEmail());
        }
        SStartServise= (Button) findViewById(R.id.sStartService);
        SStopService= (Button) findViewById(R.id.sStopService);
        SStartServise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyService.class);
                startService(i);
            }
        });
        SStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyService.class);
                stopService(i);
            }
        });
        final Button dugme = (Button) findViewById(R.id.button);
        dugme.setText("Register");

                dugme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent poziv = new Intent(getApplicationContext(), Register.class); //MapsActivity.class
                        startActivity(poziv);


                        //Toast.makeText(getApplicationContext(), "ssdsd", Toast.LENGTH_SHORT).show();

                    }

        });

        Button dugme1 = (Button) findViewById(R.id.button1);
        dugme1.setText("ALLUserList");

        dugme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent poziv = new Intent(getApplicationContext(), AllUsersList.class); //Register.class
                startActivity(poziv);


                //Toast.makeText(getApplicationContext(), "ssdsd", Toast.LENGTH_SHORT).show();

            }

        });
        Button SignOut = (Button) findViewById(R.id.mSignOut);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
            }
        });
    }
}
