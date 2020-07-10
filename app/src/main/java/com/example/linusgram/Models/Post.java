package com.example.linusgram.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY__IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_NUMBER_LIKES = "Likes";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put (KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY__IMAGE);
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

    public Number getLikes(){return getNumber(KEY_NUMBER_LIKES);}
    public void setLikes(Number likes){  put(KEY_NUMBER_LIKES, likes);}


    public String getTime(){

        Date date = getCreatedAt();

        SimpleDateFormat  format = new SimpleDateFormat("E MM dd yyyy hh:mm");

        String currDate = format.format(date);

        return currDate;

    }






}
