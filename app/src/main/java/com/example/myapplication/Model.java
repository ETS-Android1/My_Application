package com.example.myapplication;

public class Model {

    String ag_name, ag_image, ag_address;
    String ag_phone;

    public Model() {
    }

    public String getAg_phone() {
        return ag_phone;
    }

    public void setAg_phone(String ag_phone) {
        this.ag_phone = ag_phone;
    }


    public String getAg_name() {
        return ag_name;
    }

    public void setAg_name(String ag_name) {
        this.ag_name = ag_name;
    }

    public String getAg_address() {
        return ag_address;
    }

    public void setAg_address(String ag_address) {
        this.ag_address = ag_address;
    }

    public String getAg_image() {
        return ag_image;
    }

    public void setAg_image(String ag_image) {
        this.ag_image = ag_image;
    }
}