package com.example.linusgram.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.linusgram.Activities.DetailsActivity;
import com.example.linusgram.Models.Post;
import com.example.linusgram.Adapters.PostAdapater;
import com.example.linusgram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView rvPost;
    public static final String TAG = "HomeFragment";
    protected PostAdapater adapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;





    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPost = view.findViewById(R.id.rvPost);

        allPosts = new ArrayList<>();



        PostAdapater.onClickListener onClickListener = new PostAdapater.onClickListener() {
            @Override
            public void onItemClicked(int position, int replyCode) {

                if (replyCode == PostAdapater.DETAILS_CODE) {

                    Intent intent = new Intent(getContext(), DetailsActivity.class);

                    intent.putExtra("post", allPosts.get(position));

                    startActivity(intent);
                }
            }
        };

        adapter = new PostAdapater(getContext(), allPosts, onClickListener);

        rvPost.setAdapter(adapter);


        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeContainer = view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                queryPost();
                swipeContainer.setRefreshing(false);
            }
        });
        //configure refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);




        queryPost();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    protected void queryPost(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
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