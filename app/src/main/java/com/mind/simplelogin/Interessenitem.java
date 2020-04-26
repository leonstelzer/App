package com.mind.simplelogin;

import com.mind.simplelogin.Userliste.Users;

import java.util.Comparator;

public class Interessenitem {

    private String name;

    private int imageresource;


    public Interessenitem(String name, int imageresource) {
        this.name = name;
        this.imageresource = imageresource;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageresource() {
        return imageresource;
    }

    public void setImageresource(int imageresource) {
        this.imageresource = imageresource;
    }
    public static Comparator<Interessenitem> myname= new Comparator<Interessenitem>(){
        @Override
        public int compare(Interessenitem u1, Interessenitem u2){

            return u1.getName().compareTo(u2.getName());

        }
    };
}
