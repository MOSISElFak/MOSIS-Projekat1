package com.example.st.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent poziv = new Intent(getApplicationContext(), MainActivity.class); //Register.class
        //startActivity(poziv);

        Button dugme = (Button) findViewById(R.id.button);

                dugme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent poziv = new Intent(getApplicationContext(), MapsActivity.class); //Register.class
                        startActivity(poziv);

                        //Toast.makeText(getApplicationContext(), "ssdsd", Toast.LENGTH_SHORT).show();

                    }

        });
    }
}
