package com.example.linusgram.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.linusgram.Activities.DetailsActivity;
import com.example.linusgram.Activities.MainActivity;
import com.example.linusgram.Utils.EndlessRecyclerViewScrollListener;
import com.example.linusgram.Models.Post;
import com.example.linusgram.Adapters.PostAdapater;
import com.example.linusgram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final int DISPLAY_LIMIT = 20;
    private RecyclerView rvPost;
    public static final String TAG = "HomeFragment";
    protected PostAdapater adapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;
    protected ParseUser specifiedUser;






    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPost = view.findViewById(R.id.rvPost);
        MainActivity.setToolBarvisible();


        allPosts = new ArrayList<>();



        PostAdapater.onClickListener onClickListener = new PostAdapater.onClickListener() {
            @Override
            public void onItemClicked(int position, int replyCode) {

                if (replyCode == PostAdapater.DETAILS_CODE) {

                    Intent intent = new Intent(getContext(), DetailsActivity.class);

                    intent.putExtra("post", allPosts.get(position));

                    startActivity(intent);
                }
                if (replyCode == PostAdapater.PROFILE_CODE){
                    Fragment fragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", allPosts.get(position).getUser());
                    fragment.setArguments(bundle);

                    //Go from this fragment to profile fragment
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flContainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();


                }
                if (replyCode == PostAdapater.LIKE_CODE){

                    Number k = allPosts.get(position).getLikes().intValue() + 1;

                    allPosts.get(position).setLikes(k);


                }


            }
        };

        adapter = new PostAdapater(getContext(), allPosts, onClickListener, PostAdapater.HOME_FRAGMENT_CODE);

        rvPost.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPost.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromBackend(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvPost.addOnScrollListener(scrollListener);

        swipeContainer = view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                queryPost(0);
                swipeContainer.setRefreshing(false);
            }
        });
        //configure refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);




        queryPost(0);


    }

    public void loadNextDataFromBackend(int offset) {

        queryPost(1);
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

    protected void queryPost(final int page) {
        Post.query(page, DISPLAY_LIMIT, specifiedUser, new FindCallback<Post>() {
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
                    adapter.clear();
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }


}