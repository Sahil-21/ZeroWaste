package com.example.zerowaste.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    public String bio;
    public String loc;
    public String prof_uri;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getLoc() {
        return loc;
    }

    public void setProf_uri(String prof_uri) {
        this.prof_uri = prof_uri;
    }

    public String getProf_uri() {return prof_uri;    }
}
