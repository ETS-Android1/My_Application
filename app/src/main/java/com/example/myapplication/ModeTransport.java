package com.example.myapplication;


public class ModeTransport {
    String name;
    int image;

    public ModeTransport(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage()
    {

        return image;
    }

    public String getName()
    {
        return name;
    }

    public void setImage(int image)
    {
        this.image = image;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
