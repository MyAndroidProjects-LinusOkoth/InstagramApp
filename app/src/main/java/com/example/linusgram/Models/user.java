package com.example.linusgram.Models;


import com.parse.ParseClassName;
import com.parse.ParseFile;

@ParseClassName("Post")
public class user {

    public static final String KEY_DESCRIPTION = "ProfilePic";

    public ParseFile getImage(){
        return getParseFile(KEY_DESCRIPTION);
    }

    private ParseFile getParseFile(String keyDescription) {
    }

}
