package com.example.st.myapplication;

/**
 * Created by st on 01-Sep-17.
 */

public  class UserClass {
    String email;
    String ime;
    String prezime;
    String latitude;
    String longitude;
    String id;
    String points;


    public UserClass()
    {
        ime = null;
        prezime=null;
        email=null;
        latitude=null;
        longitude=null;
    }
    public String getPoints()
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
    public String getLatitude()
    {
        return latitude;
    }
    public void setLatitude(String latitude)
    {
        this.latitude= latitude;
    }
    public String getLongitude()
    {
        return longitude;
    }
    public void setLongitude(String longitude)
    {
        this.longitude= longitude;
    }

}
