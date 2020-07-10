package com.example.linusgram.Models;


import com.parse.ParseClassName;
import com.parse.ParseObject;


@ParseClassName("Comment")
public class Comment extends ParseObject {

    public static final String KEY_POST = "RelatedPost";
    public static final String KEY_USER_COMMENT = "CommentingUser";
    public static final String KEY_BODY = "body";


    public String getBody(){
        return getString(KEY_BODY);
    }
    public void setBody(String description){
        put (KEY_BODY, description);
    }




}
