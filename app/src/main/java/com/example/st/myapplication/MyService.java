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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyService extends Service {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    String currentUser;
    Location oldLocation;
    Location newLocation;

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
                myRef.child("users").child("lord").child("Latitude").setValue(location.getLatitude());
                myRef.child("users").child("lord").child("Longitude").setValue(location.getLongitude());
                oldLocation=newLocation;
                newLocation=location;
                NotificationConfig((float)location.getLatitude(), (float) location.getLongitude(), oldLocation.distanceTo(newLocation));
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
    private void NotificationConfig(float lat , float lng, float dist)
    {
        notification.setSmallIcon(R.mipmap.ic_launcher_round);
        notification.setTicker("This is the ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Distance traveled " + dist);
        notification.setContentText("Lat: " + lat + "Lng: " + lng);

        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1,notification.build());


    }
}
