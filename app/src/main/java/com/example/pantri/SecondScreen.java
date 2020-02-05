package com.example.pantri;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SecondScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }
    public void sendMessage (View view){
        Intent firstScreen = new Intent(this, ThirdScreen.class);
        startActivity(firstScreen);
    }
    public void message (View view){
        Intent firstScreen = new Intent(this, FourthScreen.class);
        startActivity(firstScreen);
    }

    public void send (View view){
        Intent firstScreen = new Intent(this, MainActivity.class);
        startActivity(firstScreen);
    }

}
