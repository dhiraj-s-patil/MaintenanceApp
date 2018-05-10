package com.dhiraj.admin.project_maintenance.classes;

/**
 * Created by admin on 5/9/18.
 */

public class User {
    private String fname, lname, email,password, apartment;
    private String accessType;

    public User(String fname, String lname, String email, String password, String accessType, String apartment){
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.accessType = accessType;
        this.apartment = apartment;
    }

    public User(){

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
}
