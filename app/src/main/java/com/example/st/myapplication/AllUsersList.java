package com.example.st.myapplication;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.ArrayList;

public class AllUsersList extends AppCompatActivity {




    private ListView lista ;
    private  String value;
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<String> usernamesS = new ArrayList<>();
    private TextView userName;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users_list);


        lista = (ListView)findViewById(R.id.AllUsers);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, usernames);
        lista.setAdapter(arrayAdapter);


        myRef.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                value = dataSnapshot.getKey(); //+ dataSnapshot.getValue().toString()
                usernames.add(value);
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
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                value = parent.getItemAtPosition(position).toString();
                Intent i = new Intent(getApplicationContext(), UserProfile.class);
                i.putExtra("vrednost", value);
                startActivity(i);
            }
        });
        userName=(TextView) findViewById(R.id.UUserName);

    }


}
