package com.example.st.myapplication;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Console;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class UserProfile extends AppCompatActivity {


    private ListView lista ;
    private  String value;
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<String> usernamesS = new ArrayList<>();
    private TextView userName;
    private TextView uime;
    private TextView uprezime;
    private TextView uemail;
    private WebView wslika;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private StorageReference mStorageRef;
    private UserClass userClass;
    private String url;
    private TextView upoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        lista = (ListView)findViewById(R.id.AllUsers);

        String s = getIntent().getStringExtra("vrednost");
        SetUser(s.toString());
        userClass = new UserClass();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                value = parent.getItemAtPosition(position).toString();

                userName.setText(value);
                //SetUsers("");
                //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }
        });
        userName=(TextView) findViewById(R.id.UUserName);
        uime=(TextView) findViewById(R.id.UIme);
        uprezime=(TextView) findViewById(R.id.UPrezime);
        uemail=(TextView) findViewById(R.id.UEmail);
        wslika=(WebView) findViewById(R.id.WW);
        upoints = (TextView) findViewById(R.id.UPoints);






    }
    private void SetUser(final String user)
    {
        myRef.child("users").child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userClass= dataSnapshot.getValue(UserClass.class);
                uime.setText(userClass.ime);
                uprezime.setText(userClass.prezime);
                userClass.id=user;
                uemail.setText(userClass.email);
                upoints.setText(userClass.points);
                //ubroj.setText(userClass.getBrojTelefona());
                userName.setText(userClass.id);

                mStorageRef.child("users").child(user).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.v("tag", uri.toString());
                         url = uri.toString();
                        wslika.loadUrl(url);
                    }
                });

                //String url= mStorageRef.child("users/ttt").getDownloadUrl().getResult().toString();


                //mStorageRef.child("users/"+user+".jpg").getDownloadUrl().getResult();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void SetUsers(String user)
    {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, usernamesS);
        lista.setAdapter(arrayAdapter);
        myRef.child("users").child(user).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                value = dataSnapshot.getKey() + " : " + dataSnapshot.getValue().toString(); //+ dataSnapshot.getValue().toString()
                usernamesS.add(value);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
