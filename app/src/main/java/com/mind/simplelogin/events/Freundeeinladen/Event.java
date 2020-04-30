package com.mind.simplelogin.events.Freundeeinladen;

import com.mind.simplelogin.Userliste.Users;
import com.mind.simplelogin.events.Freundeeinladen.Eventid;

import java.util.Comparator;
import java.util.List;


public class Event extends Eventid {
    public Event(String ort, List<String> teilnehmer, String zeit, String datum, String kategorie, String id, String kosten, String Private, int Max) {
        Ort = ort;
        Teilnehmer = teilnehmer;
        Zeit = zeit;
        Datum = datum;
        Kategorie = kategorie;
        Id = id;
        Kosten = kosten;
        Private = Private;
        Max = Max;
    }

    public Event(){
    }

    public void setPrivate(String aPrivate) {
        Private = aPrivate;
    }

    public int getMax() {
        return Max;
    }

    public void setMax(int max) {
        Max = max;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
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

    public String getKosten() {
        return Kosten;
    }

    public void setKosten(String kosten) {
        Kosten = kosten;
    }

    public String getPrivate() {
        return Private;
    }

    public void setPrivat(String privat) {
        this.Private = Private;
    }

    String Ort;
    List<String> Teilnehmer;
    String Zeit;
    String Datum;
    String Kategorie;
    String Id;
    String Kosten;
    String Private;
    int Max;
}
