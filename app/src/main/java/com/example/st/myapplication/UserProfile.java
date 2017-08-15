package com.example.st.myapplication;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private TextView ubroj;
    private WebView wslika;
    private ImageView uslika;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        lista = (ListView)findViewById(R.id.AllUsers);

        String s = getIntent().getStringExtra("vrednost");
        SetUser(s);


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
        ubroj=(TextView) findViewById(R.id.UBrojTelefona);
        wslika=(WebView) findViewById(R.id.WW);
        uslika =(ImageView)findViewById(R.id.USlika);






    }
    private void SetUser(final String user)
    {
        myRef.child("users").child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Map<String, Objects> map =(Map<String, Objects>) dataSnapshot.getValue();
                String ime = dataSnapshot.child("Ime").getValue().toString();
                String prezime = dataSnapshot.child("Prezime").getValue().toString();
                String broj = dataSnapshot.child("Broj Telefona").getValue().toString();
                uime.setText(ime);
                uprezime.setText(prezime);
                ubroj.setText(broj);
                userName.setText(user);
                final String[] url = new String[1];

                mStorageRef.child("gs://myapplication-5bc36.appspot.com/users/ttt").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                         url[0] = uri.toString();
                    }
                });

                //String url= mStorageRef.child("users/ttt").getDownloadUrl().getResult().toString();
                wslika.loadUrl(url[0]);
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
