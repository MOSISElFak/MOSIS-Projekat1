package com.example.st.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyService extends Service {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    private UserClass userClass;
    private ArrayList<UserClass> usernames = new ArrayList<>();
    public int NotificationId=1;
    String currentUser;
    Location oldLocation;
    Location newLocation;
    String radious;


    NotificationCompat.Builder notification;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate() {
        super.onCreate();

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


        oldLocation = new Location("Tacka A");
        oldLocation.setLongitude(0);
        oldLocation.setLatitude(0);

        newLocation = new Location("Tacka B");
        newLocation.setLongitude(0);
        newLocation.setLatitude(0);


        notification= new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myRef.child("users").child(ReturnID(currentUser)).child("latitude").setValue(location.getLatitude());
                myRef.child("users").child(ReturnID(currentUser)).child("longitude").setValue(location.getLongitude());
                CheckRadious(Float.parseFloat(radious),location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, locationListener);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager!= null){
            locationManager.removeUpdates(locationListener);
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private void NotificationConfig(double lat , double lng, float dist, String email, String ime, int hashcode)
    {
        notification.setSmallIcon(R.mipmap.ic_launcher_round);
        notification.setTicker("This is the ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(ime + "   " + dist);
        notification.setContentText(email+"  Lat: " + lat + "Lng: " + lng);

        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(hashcode,notification.build());


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

    public void CheckRadious(float radious, Location locator)
    {
        for(UserClass uc : usernames)
        {
            Location userLocation = new Location("Korisnikova Tacka");
            userLocation.setLatitude(uc.latitude);
            userLocation.setLongitude(uc.longitude);
            if(locator.distanceTo(userLocation)<radious && !uc.email.equals(user.getEmail()))
            {
                NotificationConfig(userLocation.getLatitude(),  userLocation.getLongitude(), locator.distanceTo(userLocation), uc.email,uc.ime, uc.email.hashCode());
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
         radious=(String) intent.getExtras().get("radius");
        Log.e("radius: " , radious);
        return super.onStartCommand(intent, flags, startId);
    }
}
