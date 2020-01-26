package com.mind.simplelogin;

public class Users extends UserId{
    public Users(String Benutername,String Beschreibung,String EMail,String Image,String Interessen,String Ort,String Telefonnummer){
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

    public String getInteressen() {
        return Interessen;
    }

    public void setInteressen(String interessen) {
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

    String Benutername, Beschreibung, EMail, Image, Interessen, Ort, Telefonnummer;
}
