package com.example.linusgram;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;

import com.example.linusgram.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);


    }
}