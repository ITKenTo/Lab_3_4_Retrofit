package com.example.ph26746_lab_3_4.Model;

import android.os.Parcelable;

import java.io.Serializable;

public class LoverModel  {
    private String _id;
    private String name;
    private String phone;
    private String date;
    private TypeModel id_type;
    private String des;
    private String image;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public LoverModel(String _id, String name, String phone, String date, TypeModel id_type, String des, String image) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.id_type = id_type;
        this.des = des;
        this.image = image;
    }

    public LoverModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TypeModel getId_type() {
        return id_type;
    }

    public void setId_type(TypeModel id_type) {
        this.id_type = id_type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
