package com.barebones.models;

import java.util.List;

public class User {

    private String user;
    private List<String> likedVideos;
    private List<String> subscribedChannel;

    public String getUserName() {
        return user;
    }

    public void setUserName(String user) {
        this.user = user;
    }
}
