package com.mind.simplelogin.events.neuerstellen.Kategorie;

import java.util.Comparator;

public class InteressenProfilItem {

    private String name;
    boolean isSelected = false;
    private int imageresource;

    public InteressenProfilItem(String name, int imageresource, boolean isSelected) {
        this.name = name;
        this.imageresource = imageresource;
        this.isSelected = isSelected;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(boolean selected){
        isSelected = selected;
    }

    public boolean isSelected() { return isSelected; }

    public int getImageresource() {
        return imageresource;
    }

    public void setImageresource(int imageresource) {
        this.imageresource = imageresource;
    }
    public static Comparator<InteressenProfilItem> myname= new Comparator<InteressenProfilItem>(){
        @Override
        public int compare(InteressenProfilItem u1, InteressenProfilItem u2){

            return u1.getName().compareTo(u2.getName());

        }
    };
}