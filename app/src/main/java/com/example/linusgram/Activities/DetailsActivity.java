package com.example.linusgram.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.linusgram.Models.Post;
import com.example.linusgram.R;
import com.example.linusgram.databinding.ActivityDetailsBinding;
import com.example.linusgram.databinding.ActivityMainBinding;

public class DetailsActivity extends AppCompatActivity {

    Post post;
    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       binding = ActivityDetailsBinding.inflate(getLayoutInflater());

        final View view = binding.getRoot();
        setContentView(view);

        post = getIntent().getParcelableExtra("post");

        binding.tvUserNameDescription.setText(post.getUser().getUsername());

        binding.tvDescription.setText(post.getDescription());

        binding.tvTimeStamp.setText(post.getTime());

        binding.NumberofActualLikes.setText(post.getLikes().toString());

        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(binding.ivPostImage);




    }
}