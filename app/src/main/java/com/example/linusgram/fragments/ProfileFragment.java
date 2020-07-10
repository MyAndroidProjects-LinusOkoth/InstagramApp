package com.example.linusgram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.linusgram.Models.Post;
import com.example.linusgram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.Nullable;

import java.util.List;


public class ProfileFragment extends HomeFragment {

    private ImageView ivProfilePic;
    private TextView tvBio;
    private TextView tvUsername;
    private Button btnEditProfile;

    public ProfileFragment() {
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvBio = view.findViewById(R.id.tvUsername);
        tvUsername = view.findViewById(R.id.tvUsername);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        //Get the bundle to determine user
        Bundle bundle = this.getArguments();
        if (bundle == null){
            user = ParseUser.getCurrentUser();
        } else {
            user = bundle.getParcelable("user");
        }

        tvUsername.setText(user.getUsername());
        ParseFile image = user.getParseFile("profilePic");
        if (image == null){
            Glide.with(getContext())
                    .load(getResources().getString(R.string.DEFAULT_PROFILE_PIC))
                    .circleCrop()
                    .into(ivProfilePic);
        } else {
            Glide.with(getContext())
                    .load(image.getUrl())
                    .circleCrop()
                    .into(ivProfilePic);
        }

        if (user == ParseUser.getCurrentUser()) {
            btnEditProfile.setVisibility(View.VISIBLE);
            //some EditProfile functionality
            btnEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //When clicked, it launches the EditProfile Activity
                    Intent intent = new Intent(getContext(), EditProfileActivity.class);
                    //pass info from that post into Details Activity
                    intent.putExtra("user", user);
                    getContext().startActivity(intent);
                }
            });
        } else {
            btnEditProfile.setVisibility(View.GONE);
        }


        rvUserPosts = view.findViewById(R.id.rvPosts);

        userPosts = new ArrayList<>();
        //instantiate the adapter
        userAdapter = new PostAdapter(getContext(), userPosts, new PostAdapter.onClickListener() {
            @Override
            public void onProfilePicAction(int position) {
                //do nothing
            }

            @Override
            public void onUsernameAction(int position) {
                //do nothing
            }
        }, ProfileFragment.class.getSimpleName());

        //set the adapter on the recycler view
        rvUserPosts.setAdapter(userAdapter);

        //set the layout manager on the recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvUserPosts.setLayoutManager(gridLayoutManager);
        queryPosts(0);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //get latest 20 Instagram items
                queryPosts(0);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Stop animation (This will be after 2 seconds)
                        swipeContainer.setRefreshing(false);
                    }
                }, 2000); //Delay in millis

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //get the next 20 posts
                queryPosts(page);
            }
        };
        rvUserPosts.addOnScrollListener(scrollListener);
    }


    @Override
    protected void queryPost() {
        super.queryPost();

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addAscendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error getting Post", e);
                    return;
                }
                for (Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + " username: " + post.getUser().getUsername());
                }

                allPosts.clear();
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();


            }
        });

    }
}