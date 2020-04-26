package com.mind.simplelogin.events.Freundeeinladen;

import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.Freundeeinladen.Eventid;

import java.util.Comparator;
import java.util.List;


public class Event extends Eventid {
    public Event(String Datum, String Eventname, String Id, String Kategorie, String Ort, List<String> Teilnehmer,String Zeit ){
            this.Datum=Datum;
            this.Eventname=Eventname;
            this.Id=Id;
            this.Ort=Ort;
            this.Teilnehmer=Teilnehmer;
            this.Zeit=Zeit;
            this.Datum=Datum;
            this.Kategorie= Kategorie;
    }
    public Event(){
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public String getEventname() {
        return Eventname;
    }

    public void setEventname(String eventname) {
        Eventname = eventname;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getKategorie() {
        return Kategorie;
    }

    public void setKategorie(String kategorie) {
        Kategorie = kategorie;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public List<String> getTeilnehmer() {
        return Teilnehmer;
    }

    public void setTeilnehmer(List<String> teilnehmer) {
        Teilnehmer = teilnehmer;
    }

    public String getZeit() {
        return Zeit;
    }

    public void setZeit(String zeit) {
        Zeit = zeit;
    }


    public static Comparator<Event> myname= new Comparator<Event>(){
        @Override
        public int compare(Event u1, Event u2){

            return u1.getEventname().compareTo(u2.getEventname());

        }
    };
    String Eventname;
    String Ort;
    List<String> Teilnehmer;
    String Zeit;
    String Datum;
    String Kategorie;
    String Id;
}
