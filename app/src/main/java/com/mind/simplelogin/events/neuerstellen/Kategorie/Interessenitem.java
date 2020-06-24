package com.mind.simplelogin.events.neuerstellen.Kategorie;

import java.util.Comparator;

public class Interessenitem {

    private String name;
    private boolean isChecked;
    private int imageresource;

    public Interessenitem(String name, int imageresource) {
        this.name = name;
        this.isChecked = isChecked;
        this.imageresource = imageresource;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked(){
        return isChecked;
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