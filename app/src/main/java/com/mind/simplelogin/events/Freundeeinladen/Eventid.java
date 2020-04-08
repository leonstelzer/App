package com.mind.simplelogin.events.Freundeeinladen;

import com.google.firebase.database.annotations.NotNull;


public class Eventid {
    public String eventid;
    public <T extends Eventid> T withId(@NotNull final String id){
        this.eventid = id;
        return (T) this;
    }

}
