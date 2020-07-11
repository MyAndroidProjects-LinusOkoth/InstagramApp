package com.example.linusgram.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.linusgram.Adapters.CommentAdapter;
import com.example.linusgram.Models.Comment;
import com.example.linusgram.Models.Post;
import com.example.linusgram.R;
import com.example.linusgram.databinding.ActivityDetailsBinding;
import com.example.linusgram.databinding.ActivityMainBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DETAILSACTIVITY";
    Post post;
    ActivityDetailsBinding binding;
    List<Comment> comments;
    CommentAdapter commentAdapter;

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


        binding.sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String body = binding.editComment.getText().toString();

                if (body.isEmpty()){
                    Toast.makeText(DetailsActivity.this, "Body cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                Comment comment = new Comment();
                comment.setBody(body);
                comment.setUser(ParseUser.getCurrentUser());
                comment.setPost(post);

                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(DetailsActivity.this, "Comment posted", Toast.LENGTH_SHORT).show();
                        queryComments(post);
                    }
                });
            }
        });

        String profilepic = post.getUser().getParseFile("ProfilePic").getUrl();


        Glide.with(this)
                .load(profilepic)
                .into(binding.ivProfileImage);


        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(binding.ivPostImage);

        comments = new ArrayList<>();


        commentAdapter = new CommentAdapter(this, comments, new CommentAdapter.onClickListener() {
            @Override
            public void onItemClicked(int position, int replyCode) {

            }
        });

        //set the adapter on the recycler view
        binding.rvComment.setAdapter(commentAdapter);

        //set the layout manager on the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvComment.setLayoutManager(linearLayoutManager);
        queryComments(post);


    }

    protected void queryComments(final Post post) {
        Comment.query(post, new FindCallback<Comment>(){
            @Override
            public void done(List<Comment> coments, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;

                }
                for(Comment comment: coments){
                    Log.i(TAG, "Comment: " + comment.getBody() + " Username: " + comment.getUser().getUsername());
                }
                comments.clear();
                comments.addAll(coments);
                commentAdapter.notifyDataSetChanged();
            }
        });
    }




}