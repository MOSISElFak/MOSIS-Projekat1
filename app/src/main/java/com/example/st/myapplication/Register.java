package com.example.st.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Register extends AppCompatActivity {

    private Button Register;
    private EditText Ime;
    private EditText Prezime;
    private EditText Username;
    private EditText Password;
    private EditText BrTel;
    private ImageView rSlika;
    private Button rSlikaj;
    private Bitmap photo;
    public  Uri downloadUrl;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private StorageReference mStorageRef;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Register = (Button) findViewById(R.id.rRegister);
        Ime = (EditText) findViewById(R.id.eIme);
        Prezime = (EditText) findViewById(R.id.ePrezime);
        Username = (EditText) findViewById(R.id.eUsername);
        Password = (EditText) findViewById(R.id.ePassword);
        BrTel = (EditText) findViewById(R.id.eBrojTelefona);
        rSlika = (ImageView) findViewById(R.id.rSlika);
        rSlikaj = (Button) findViewById(R.id.rSlikaj);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("users").child(Username.getText().toString()).child("Ime").setValue(Ime.getText().toString());
                myRef.child("users").child(Username.getText().toString()).child("Prezime").setValue(Prezime.getText().toString());
                myRef.child("users").child(Username.getText().toString()).child("Password").setValue(Password.getText().toString());
                myRef.child("users").child(Username.getText().toString()).child("Broj Telefona").setValue(BrTel.getText().toString());


                mStorageRef = FirebaseStorage.getInstance().getReference();
                StorageReference referenca= mStorageRef.child("users").child(Username.getText().toString());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = referenca.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(Register.this,"NEuspesan upload slike",Toast.LENGTH_LONG ).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                         downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(Register.this,"uspesan upload slike",Toast.LENGTH_LONG ).show();
                    }
                });
            }
        });

        if(!hasCamera())
        {
            rSlika.setEnabled(false);
        }

        rSlika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;*/
                android.view.ViewGroup.LayoutParams layoutParams = rSlika.getLayoutParams();
                layoutParams.width = 300;
                layoutParams.height = 300;
                rSlika.setLayoutParams(layoutParams);
            }
        });




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
                Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            rSlika.setImageBitmap(photo);

        }
    }

    public void Slikaj(View view){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);

    }
    public boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


}
