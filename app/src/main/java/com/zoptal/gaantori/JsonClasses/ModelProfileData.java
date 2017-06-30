package com.zoptal.gaantori.JsonClasses;


import java.io.Serializable;

public  class ModelProfileData  implements Serializable{

        private String id;
        private String firstname;
        private String lastname;
        private String username;
        private String gender;
        private String email;
        private String city;
        private String dob;
        private String utype;


    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getFirstname() {

        return firstname;
    }

    public void setFirstname(String firstname) {

        this.firstname=firstname;
    }

    public String getLastname() {

        return lastname;
    }

    public void setLastname(String lastname) {

        this.lastname=lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender=gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city=city;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob=dob;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype=utype;
    }


}