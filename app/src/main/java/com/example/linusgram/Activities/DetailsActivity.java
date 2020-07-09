package com.example.linusgram.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.linusgram.Models.Post;
import com.example.linusgram.R;

public class DetailsActivity extends AppCompatActivity {

    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        post = getIntent().getParcelableExtra("post");



    }
}