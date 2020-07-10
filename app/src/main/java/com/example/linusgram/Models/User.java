package com.example.linusgram.Models;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseObject {

    public static final String KEY_PROFILE_PICTURE = "ProfilePic";

    public ParseFile getImage(){
        return getParseFile(KEY_PROFILE_PICTURE);
    }

    public void setImage(ParseFile parseFile){
        put (KEY_PROFILE_PICTURE, parseFile);
    }

}
