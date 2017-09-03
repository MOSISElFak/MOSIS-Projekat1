package com.example.st.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.location.Criteria;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static android.R.attr.name;

public class AddMyPlace extends FragmentActivity {


    private GoogleMap mMap;
    //private Button getLocation;
    private Button dodajPitanje;
    private Button slikaj;
    private DatabaseReference mDatabase;
    private Button dodajMesto;
    private ImageView slika;
    private EditText imeMesta;
    private EditText opisMesta;
    private StorageReference mStorageRef;
    private Bitmap photo;
    public Uri downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_place);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        // mapFragment.getMapAsync(this);
        //getLocation = (Button)findViewById(R.id.dugmeZaSlikanje);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

      /*  getLocation.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                //getLocation();
            }
        });*/
        imeMesta = (EditText) findViewById(R.id.imemesta);
        opisMesta = (EditText) findViewById(R.id.opis);
        slika = (ImageView) findViewById(R.id.rSlika);
        dodajPitanje = (Button) findViewById(R.id.kviz);
        slikaj = (Button) findViewById(R.id.dugmeZaSlikanje);
        dodajMesto = (Button) findViewById(R.id.dodajMesto);
        dodajMesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imeMesta.length() != 0 && opisMesta.length() != 0) {


                    if (slika.getDrawable() != null) {

                        mDatabase.child("places").child(imeMesta.getText().toString()).child("opis").setValue(opisMesta.getText().toString());

                        StorageReference referenca= mStorageRef.child("places").child(imeMesta.getText().toString());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = referenca.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(AddMyPlace.this,"NEuspesan upload slike",Toast.LENGTH_LONG ).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                downloadUrl = taskSnapshot.getDownloadUrl();
                                Toast.makeText(AddMyPlace.this,"Uspesan upload slike",Toast.LENGTH_LONG ).show();

                            }
                        });
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "Morate dodati fotografiju!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Ime i opis ne moze biti prazno";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        slikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }


        });
        dodajPitanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent poziv = new Intent(getApplicationContext(), AddQuestion.class); //MapsActivity.class
                imeMesta.getTextSize();
                if (imeMesta.length() > 0) {
                    String ime = imeMesta.getText().toString();
                    poziv.putExtra("mesto", ime);
                    startActivity(poziv);
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Ime  ne moze biti prazno";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                //Toast.makeText(getApplicationContext(), "ssdsd", Toast.LENGTH_SHORT).show();

            }

        });
    }

    //za kameru
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            slika.setImageBitmap(photo);
        }
    }


    /*public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng arg0)
            {
                android.util.Log.i("onMapClick", "Horray!");
            }
        });*/


    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating an empty criteria object

        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria


        String provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        double c = location.getLatitude();

        double d = location.getLongitude();
    }
}







