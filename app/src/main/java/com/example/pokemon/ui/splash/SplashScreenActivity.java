package com.example.pokemon.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokemon.R;
import com.example.pokemon.ui.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private long splashTime = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(splashTime);
                } catch(Exception e) {}
                finally {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }


}