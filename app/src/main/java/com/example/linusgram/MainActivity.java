package com.example.linusgram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.linusgram.databinding.ActivityMainBinding;
import com.example.linusgram.fragments.ComposeFragment;
import com.example.linusgram.fragments.HomeFragment;
import com.example.linusgram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.net.URI;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    ActivityMainBinding binding;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        final FragmentManager fragmentManager = getSupportFragmentManager();

        //define your fragments here
        final Fragment composeFragment = new ComposeFragment();
        final Fragment homeFragment = new HomeFragment();
        final Fragment profileFragment = new ProfileFragment();

        // layout of activity is stored in a special property called root
        final View view = binding.getRoot();
        setContentView(view);



        // Find the toolbar view inside the activity layout
         //toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(binding.toolbar);



        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        binding.toolbar.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Home",
                                Toast.LENGTH_SHORT).show();
                        fragment = homeFragment;
                        break;
                    case R.id.action_compose:
                        Toast.makeText(MainActivity.this, "Compose",
                                Toast.LENGTH_SHORT).show();
                        fragment = composeFragment;
                        break;
                    case R.id.action_profile:
                    default:
                        Toast.makeText(MainActivity.this, "Profile",
                                Toast.LENGTH_SHORT).show();
                        fragment = profileFragment;
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        binding.bottomNavigation.setSelectedItemId(R.id.action_home);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}