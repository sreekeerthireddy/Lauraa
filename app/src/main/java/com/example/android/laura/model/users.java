package com.example.android.laura.model;

public class users {
    private String name,password,phone;

    public users()
    {

    }
    public users(String n,String pa,String p)
    {
        name=n;
        password=pa;
        phone=p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.phone= phone;
    }
}
