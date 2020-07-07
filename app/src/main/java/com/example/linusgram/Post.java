package com.example.linusgram;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY__IMAGE = "image";
    public static final String KEY_USER = "user";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put (KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_DESCRIPTION);
    }
    public void setImage(ParseFile parseFile){
        put (KEY__IMAGE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put (KEY_USER, user);
    }




}
