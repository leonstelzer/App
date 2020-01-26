package com.mind.simplelogin;

import com.google.firebase.database.annotations.NotNull;

public class UserId {
    public String userId;

    public <T extends UserId> T withId(@NotNull final String id){
        this.userId = id;
        return (T) this;
    }




}
