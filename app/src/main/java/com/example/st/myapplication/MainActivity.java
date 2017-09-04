package com.example.st.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private TextView probniTekst;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth firebaseAuth;
    private Button SStartServise;
    private Button SStopService;
    private Button Insert;
    private EditText MRadious;
    private Button MAddMyPlace;
    private Button MQuiz;


    private ArrayList<UserClass> usernames = new ArrayList<>();
    String[] fruits;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MRadious= (EditText) findViewById(R.id.mRadious);
        MAddMyPlace= (Button) findViewById(R.id.mAddMyPlace);
        MQuiz= (Button) findViewById(R.id.mQuiz);

        probniTekst = (TextView) findViewById(R.id.probaText);
        firebaseAuth = FirebaseAuth.getInstance();
        Insert = (Button) findViewById(R.id.mInsert);

        MQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OdgovoriteNaPitanja.class);
                startActivity(intent);
            }
        });
        MAddMyPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMyPlace.class);
                startActivity(intent);
            }
        });
        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);

//                InputUser.setEmail("sdad");
//                InputUser.setIme("vlada");
//                InputUser.setPrezime("pre");
//                InputUser.setLatitude(42);
//                InputUser.setLongitude(66);
//                myRef.child("users").child("probaKlase").setValue(InputUser);
            }
        });
        if (user == null) {
            finish();
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
        } else {
            probniTekst.setText(user.getEmail());
        }
        SStartServise = (Button) findViewById(R.id.sStartService);
        SStopService = (Button) findViewById(R.id.sStopService);
        SStartServise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyService.class);
                i.putExtra("radius", MRadious.getText().toString());
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

        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent poziv = new Intent(getApplicationContext(), Register.class); //MapsActivity.class
                startActivity(poziv);


                //Toast.makeText(getApplicationContext(), "ssdsd", Toast.LENGTH_SHORT).show();

            }

        });

        Button dugme1 = (Button) findViewById(R.id.button1);

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

