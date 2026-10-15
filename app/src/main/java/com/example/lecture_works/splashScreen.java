package com.example.lecture_works;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class splashScreen extends AppCompatActivity {

    ImageView image;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            startActivity(new Intent(splashScreen.this,signUp.class));
//            finish();
//        },1000);

        image = findViewById(R.id.imageView);
        Glide.with(splashScreen.this)
                .load("https://search.brave.com/images?q=gif")
                .placeholder(R.mipmap.ic_launcher).into(image);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            startActivity(new Intent(splashScreen.this, MainActivity.class));
            finish();
        }},1000);
    }

}