package com.example.linusgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Bindable;

import android.os.Bundle;
import android.renderscript.ScriptGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityDetailsBinding.inflate(getLayoutInflater());

    }
}