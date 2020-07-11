package com.example.linusgram.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.linusgram.Adapters.CommentAdapter;
import com.example.linusgram.Models.Comment;
import com.example.linusgram.Models.Post;
import com.example.linusgram.R;
import com.example.linusgram.databinding.ActivityDetailsBinding;
import com.example.linusgram.databinding.ActivityMainBinding;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    Post post;
    ActivityDetailsBinding binding;
    List<Comment> comments;

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

        String profilepic = post.getUser().getParseFile("ProfilePic").getUrl();


        Glide.with(this)
                .load(profilepic)
                .into(binding.ivProfileImage);


        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(binding.ivPostImage);


        binding.rvComment = new CommentAdapter(this, )




    }
}