package com.example.st.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.R.attr.value;


public class OdgovoriteNaPitanja extends AppCompatActivity {
    private TextView  pitanje;
    private Button odg1,odg2,odg3,odg4;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("questions");
    private DatabaseReference mDatabase;
    private ArrayList<Pitanja> mPitanja = new ArrayList<Pitanja>();
    int brojPitanja = 0;
    int brojPoena = 0;
    String currentUser;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<UserClass> usernames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odgovorite_na_pitanja);
        pitanje = (TextView)findViewById(R.id.pitanje) ;
        odg1 = (Button)findViewById(R.id.tacanOdgovor);
        odg2 = (Button)findViewById(R.id.pogresan1);
        odg3 = (Button)findViewById(R.id.pogresan2);
        odg4 = (Button)findViewById(R.id.pogresan3);


        if(user==null)
        {
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
        }
        else
        {
            currentUser=user.getEmail().toString();
        }
        FindUser();
        brojPoena=UserPoints(user.getEmail());


        odg2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if( mPitanja.get(brojPitanja-1).tacanOdgovor == odg2.getText())
                                        {
                                            brojPoena++;
                                            odg2.setBackgroundColor(Color.GREEN);


                                        }
                                        Context context = getApplicationContext();
                                        CharSequence text = ("Broj poena "+brojPoena+"");
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();

                                        novoPitanje(brojPitanja);
                                    }
                                }
        );
        odg3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (mPitanja.get(brojPitanja-1).tacanOdgovor == odg4.getText())
                                        {
                                            brojPoena++;
                                            odg3.setBackgroundColor(Color.GREEN);


                                        }
                                        Context context = getApplicationContext();
                                        CharSequence text = ("Broj poena "+brojPoena+"");
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();

                                        novoPitanje(brojPitanja);
                                    }
                                }
        );
        odg4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Pitanja p = mPitanja.get(0);
                                        if (mPitanja.get(brojPitanja - 1).tacanOdgovor == odg4.getText()) {
                                            brojPoena++;
                                            odg4.setBackgroundColor(Color.GREEN);


                                        }
                                        Context context = getApplicationContext();
                                        CharSequence text = ("Broj poena "+brojPoena+"");
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();

                                    }
                                }
        );

        odg1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (mPitanja.get(brojPitanja-1).tacanOdgovor == odg1.getText())
                                        {
                                            brojPoena++;
                                            odg1.setBackgroundColor(Color.GREEN);

                                        }
                                        Context context = getApplicationContext();
                                        CharSequence text = ("Broj poena "+brojPoena+"");
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();

                                        novoPitanje(brojPitanja);
                                    }
                                }

        );
        myRef.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                                            Pitanja p = new Pitanja();
                                            long g = dataSnapshot.getChildrenCount();
                                            p.pitanje = dataSnapshot.child("pitanje").getValue().toString();
                                            p.tacanOdgovor = dataSnapshot.child("correct").getValue().toString();
                                            p.netacan1 = dataSnapshot.child("answer1").getValue().toString();
                                            p.netacan2 = dataSnapshot.child("answer2").getValue().toString();
                                            p.netacan3 = dataSnapshot.child("answer3").getValue().toString();
                                            mPitanja.add(p);
                                            if (brojPitanja == 0) {
                                                pitanje.setText(p.pitanje);
                                                odg1.setText(p.tacanOdgovor);
                                                odg2.setText(p.netacan1);
                                                odg3.setText(p.netacan2);
                                                odg4.setText(p.netacan3);
                                                brojPitanja++;
                                            }

                                        }
                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                            Pitanja p = new Pitanja();
                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                                            Pitanja p = new Pitanja();
                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                            Pitanja p = new Pitanja();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Pitanja p = new Pitanja();
                                        }
                                    }

        );


    }

    void novoPitanje(int index)
    {
        odg1.setBackgroundColor(Color.BLUE);
        odg2.setBackgroundColor(Color.BLUE);
        odg3.setBackgroundColor(Color.BLUE);
        odg4.setBackgroundColor(Color.BLUE);
        if(index < mPitanja.size())
        {
            pitanje.setText(mPitanja.get(index).pitanje);
            odg1.setText(mPitanja.get(index).tacanOdgovor);
            odg2.setText(mPitanja.get(index).netacan1);
            odg3.setText(mPitanja.get(index).netacan2);
            odg4.setText(mPitanja.get(index).netacan3);
            brojPitanja++;
        }
        else
        {
            Context context = getApplicationContext();
            CharSequence text = "Zavrsiliste kviz";
            int duration = Toast.LENGTH_SHORT;
            myRef.child("users").child(ReturnID(currentUser)).child("points").setValue(brojPoena);

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

    public int UserPoints(String email)
    {
        for(UserClass uc : usernames)
        {
            if(uc.email.equals(email))
                return uc.points;
        }
        return 0;
    }

    public void FindUser() {
        myRef.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserClass userClass = dataSnapshot.getValue(UserClass.class);
                userClass.id=dataSnapshot.getKey();
                usernames.add(userClass);
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
    public String ReturnID(String email)
    {
        for(UserClass uc : usernames)
        {
            if(uc.email.equals(email))
                return uc.id;
        }
        return null;
    }

}
