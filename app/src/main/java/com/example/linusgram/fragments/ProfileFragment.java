package com.example.linusgram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.linusgram.Adapters.PostAdapater;
import com.example.linusgram.HelperClasses.EndlessRecyclerViewScrollListener;
import com.example.linusgram.Models.Post;
import com.example.linusgram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;


public class ProfileFragment extends HomeFragment {

    private static final int DISPLAY_LIMIT = 20 ;
    private ImageView ivProfilePic;
    private TextView tvBio;
    private TextView tvUsername;
    private Button btnEditProfile;
    private ParseUser user;
    private RecyclerView rvUserPosts;
    private List<Post> userPosts;
    private PostAdapater userAdapter;

    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;

    public ProfileFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfilePic = view.findViewById(R.id.ivProfileImage);
        tvBio = view.findViewById(R.id.tvBio);
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
        if (image != null){

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
                }
            });
        } else {
            btnEditProfile.setVisibility(View.GONE);
        }


        rvUserPosts = view.findViewById(R.id.rvPosts);

        userPosts = new ArrayList<>();
        //instantiate the adapter
        userAdapter = new PostAdapater(getContext(), userPosts, new PostAdapater.onClickListener() {
            @Override
            public void onItemClicked(int position, int replyCode) {

            }
        },PostAdapater.PROFILE_FRAGMENT_CODE);

        //set the adapter on the recycler view
        rvUserPosts.setAdapter(userAdapter);

        //set the layout manager on the recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvUserPosts.setLayoutManager(gridLayoutManager);
        queryPost(0);

        swipeContainer =  view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //get latest 20 Instagram items
                queryPost(0);
                swipeContainer.setRefreshing(false);


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
                queryPost(page);
            }
        };
        rvUserPosts.addOnScrollListener(scrollListener);
    }



    protected void queryPosts(final int page) {
        Post.query(page, DISPLAY_LIMIT, user, new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;

                }
                for(Post post: posts){
                    Log.i(TAG, "Post: " + post.getDescription() + " Username: " + post.getUser().getUsername());
                }
                if(page == 0) {
                    userAdapter.clear();
                }
                userPosts.addAll(posts);
                userAdapter.notifyDataSetChanged();
            }
        });
    }

}