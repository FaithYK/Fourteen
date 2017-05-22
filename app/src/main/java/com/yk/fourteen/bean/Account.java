package com.yk.fourteen.bean;

import android.widget.TextView;

import cn.bmob.v3.BmobUser;

/**
 * Created by S01 on 2017/5/19.
 */

public class Account extends BmobUser{
    private Boolean sex;
    private String photo;
    private String age;
    private String address;

    public Account() {
    }

    public Account(Boolean sex, String photo, String age, String address) {
        this.sex = sex;
        this.photo = photo;
        this.age = age;
        this.address = address;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
