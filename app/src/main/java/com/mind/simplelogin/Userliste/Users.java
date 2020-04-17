package com.mind.simplelogin.Userliste;

import com.mind.simplelogin.Profil.UserId;

import java.util.List;

public class Users extends UserId {
    public Users(String Benutername, String Beschreibung, String EMail, String Image, List<String> Interessen, String Ort, String Telefonnummer){
        this.Benutername=Benutername;
        this.Beschreibung=Beschreibung;
        this.EMail=EMail;
        this.Image=Image;
        this.Interessen=Interessen;
        this.Ort=Ort;
        this.Telefonnummer=Telefonnummer;




    }
    public Users(){

    }

    public String getBenutername() {
        return Benutername;
    }

    public void setBenutzername(String benutername) {
        Benutername = benutername;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public List<String> getInteressen() {
        return Interessen;
    }

    public void setInteressen(List<String> interessen) {
        Interessen = interessen;
    }

    public String getOrt() {
        return Ort;
    }

    public void setOrt(String ort) {
        Ort = ort;
    }

    public String getTelefonnummer() {
        return Telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        Telefonnummer = telefonnummer;
    }

    public String getUsId() {
        return this.userId;
    }

    String Benutername;
    String Beschreibung;
    String EMail;
    String Image;
    List<String> Interessen;
    String Ort;
    String Telefonnummer;
}
