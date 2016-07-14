package com.nanadeer.eyelash.database;

import java.util.ArrayList;

/**
 * Created by Sabrina Kuo on 2016/5/30.
 */
public class CustomInfo {
    private long id;
    private String name;
    private String phone;
    private String date;
    private String eyesType;
    private String style;
    private String material;
    private String curl;
    private String length;
    private ArrayList<Integer> photos = new ArrayList<>();

    public CustomInfo(){}

    public CustomInfo(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public CustomInfo(String name, String phone, String date, String eyesType, String style, String material, String curl, String length, ArrayList<Integer> photos) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.eyesType = eyesType;
        this.style = style;
        this.material = material;
        this.curl = curl;
        this.length = length;
        this.photos = photos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEyesType() {
        return eyesType;
    }

    public void setEyesType(String eyesType) {
        this.eyesType = eyesType;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCurl() {
        return curl;
    }

    public void setCurl(String curl) {
        this.curl = curl;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public ArrayList<Integer> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Integer> photos) {
        this.photos = photos;
    }
}
