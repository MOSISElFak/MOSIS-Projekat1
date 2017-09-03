package com.example.st.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestion extends AppCompatActivity {
    private EditText pitanje;
    private   EditText  tacanOdgovor;
    private EditText netacanOdgovor1;
    private EditText netacanOdgovor2;
    private EditText netacanOdgovor3;
    private Button saljiOdgovor;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        saljiOdgovor = (Button) findViewById(R.id.dugmeZaSlanje);
        pitanje = (EditText) findViewById(R.id.pitanje);
        tacanOdgovor = (EditText) findViewById(R.id.tacanOdgovor);
        netacanOdgovor1 = (EditText) findViewById(R.id.pogresan1);
        netacanOdgovor2 = (EditText) findViewById(R.id.pogresan2);
        netacanOdgovor3 = (EditText) findViewById(R.id.pogresan3);

        saljiOdgovor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pitanje.length() >0 && netacanOdgovor1.length() > 0 && netacanOdgovor2.length() > 0 && netacanOdgovor3.length() > 0 && tacanOdgovor.length() > 0) {

                    String mesto = getIntent().getExtras().getString("mesto");

                    mDatabase.child("questions").child(mesto).child("pitanje").setValue(pitanje.getText().toString());
                    mDatabase.child("questions").child(mesto).child("correct").setValue(tacanOdgovor.getText().toString());
                    mDatabase.child("questions").child(mesto).child("answer1").setValue(netacanOdgovor1.getText().toString());
                    mDatabase.child("questions").child(mesto).child("answer2").setValue(netacanOdgovor2.getText().toString());
                    mDatabase.child("questions").child(mesto).child("answer3").setValue(netacanOdgovor3.getText().toString());

                }
            }
        });
    }
}

