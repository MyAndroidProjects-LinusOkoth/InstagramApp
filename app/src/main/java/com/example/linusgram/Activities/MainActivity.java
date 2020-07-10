package com.example.linusgram.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.linusgram.Models.Post;
import com.example.linusgram.R;
import com.example.linusgram.databinding.ActivityMainBinding;
import com.example.linusgram.fragments.ComposeFragment;
import com.example.linusgram.fragments.HomeFragment;
import com.example.linusgram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    ActivityMainBinding binding;
    Toolbar toolbar;
    File photoFile;
    public String photoFileName = "photo.jpg";

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


        binding.Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Take your profile picture",
                        Toast.LENGTH_SHORT).show();

            }
        });



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
                        binding.toolbar.setVisibility(View.GONE);
                        fragment = composeFragment;
                        break;
                    case R.id.action_profile:
                    default:
                        Toast.makeText(MainActivity.this, "Profile",
                                Toast.LENGTH_SHORT).show();
                        binding.toolbar.setVisibility(View.GONE);
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

    private void launchCamera() {
        //Create an intent to take pictures and return control to the app
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Create a file reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        //wrap file object into a content provider
        Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.i(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }




}