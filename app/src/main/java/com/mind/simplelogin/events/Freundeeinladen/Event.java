package com.mind.simplelogin.events.Freundeeinladen;



public class Event extends Eventid {
    public Event(String Eventname, String Ort, String Teilnehmer, String Zeit, String Datum){
            this.Eventname=Eventname;
            this.Ort=Ort;
            this.Teilnehmer=Teilnehmer;
            this.Zeit=Zeit;
            this.Datum=Datum;
    }
    public Event(){
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
}
