package com.nanadeer.eyelash.database;

/**
 * Created by sabrinakuo on 2016/5/30.
 */
public class CustomInfo {
    public String name;
    public String phone;

    public CustomInfo(String name, String phone){
        this.name = name;
        this.phone = phone;
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
}
