package com.mind.simplelogin.events.Freundeeinladen;

import com.mind.simplelogin.events.Freundeeinladen.Eventid;


public class Event extends Eventid {
    public Event(String Eventname, String Ort, String Teilnehmer, String Zeit, String Datum, String Kategorie,String Id){
            this.Eventname=Eventname;
            this.Ort=Ort;
            this.Teilnehmer=Teilnehmer;
            this.Zeit=Zeit;
            this.Datum=Datum;
            this.Kategorie= Kategorie;
            this.Id=Id;
    }
    public Event(){
    }

    public String getKategorie() {
        return Kategorie;
    }

    public void setKategorie(String kategorie) {
        Kategorie = kategorie;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEventname() {
        return Eventname;
    }

    public void setEventname(String eventname) {
        Eventname = eventname;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getTeilnehmer() {
        return Teilnehmer;
    }

    public void setTeilnehmer(String teilnehmer) {
        Teilnehmer = teilnehmer;
    }

    public String getZeit() {
        return Zeit;
    }

    public void setZeit(String zeit) {
        Zeit = zeit;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    String Eventname;
    String Ort;
    String Teilnehmer;
    String Zeit;
    String Datum;
    String Kategorie;
    String Id;
}
