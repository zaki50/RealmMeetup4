package com.example.myapplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

import io.realm.RealmObject;

// for Jackson
@JsonIgnoreProperties({"valid"})

// fot JPP
@JsonModel
public class User extends RealmObject {

    @JsonKey
    private String email;
    @JsonKey
    private String fullName;
    @JsonKey
    private Phone phone;

    public User() {
    }

    public User(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
