package com.example.linusgram.Models;


import com.parse.ParseClassName;
import com.parse.ParseFile;

@ParseClassName("Post")
public class user {

    public static final String KEY_PROFILE_PICTURE = "ProfilePic";

    public ParseFile getImage(){
        return getParseFile(KEY_PROFILE_PICTURE);
    }

    public void setImage(ParseFile parseFile){
        put (KEY_PROFILE_PICTURE, parseFile);
    }

}
