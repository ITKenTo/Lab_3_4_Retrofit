package com.example.ph26746_lab_3_4.Model;

import java.io.Serializable;

public class TypeModel implements Serializable {
    private String _id;
    private String name;
    private String des;

    public TypeModel(String _id, String name, String des) {
        this._id = _id;
        this.name = name;
        this.des = des;
    }

    public TypeModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
