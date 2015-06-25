package com.example.myapplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

import io.realm.RealmObject;

// for Jackson
@JsonIgnoreProperties({"valid"})

// fot JPP
@JsonModel
public class Phone extends RealmObject {

    @JsonKey
    private String os;
    @JsonKey
    private String type;

    public Phone() {
    }

    public Phone(String os, String type) {
        this.os = os;
        this.type = type;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
