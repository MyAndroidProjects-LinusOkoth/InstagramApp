package com.example.linusgram.Models;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;


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

    public ParseUser getUser(){
        return getParseUser(KEY_USER_COMMENT);
    }
    public void setUser(ParseUser user){
        put (KEY_USER_COMMENT, user);
    }

    public Post getPost(){return (Post) getParseObject(KEY_POST);}
    public void setPost(Post post){ put(KEY_POST, post);}



}
