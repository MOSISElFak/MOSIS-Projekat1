package com.example.st.myapplication;

import android.content.Intent;

/**
 * Created by st on 01-Sep-17.
 */

public  class UserClass {
    String email;
    String ime;
    String prezime;
    double latitude;
    double longitude;
    String id;
    int points;


    public UserClass()
    {
        ime = null;
        prezime=null;
        email=null;
        latitude=0;
        longitude=0;
        points=0;
    }
    public int getPoints()
    {
        return points;
    }
    public String getId()
    {
        return id;
    }
    public String getIme()
    {
        return ime;
    }
    public void setIme(String ime)
    {
        this.ime = ime;
    }
    public String getPrezime()
    {
        return prezime;
    }
    public void setPrezime(String prezime)
    {
        this.prezime= prezime;
    }

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email= email;
    }
    public double getLatitude()
    {
        return latitude;
    }
    public void setLatitude(double latitude)
    {
        this.latitude= latitude;
    }
    public double getLongitude()
    {
        return longitude;
    }
    public void setLongitude(double longitude)
    {
        this.longitude= longitude;
    }

}
