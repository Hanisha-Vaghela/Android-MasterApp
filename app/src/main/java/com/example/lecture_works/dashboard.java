package com.example.lecture_works;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class dashboard extends AppCompatActivity {

    Button delete,logout,profile,category;

    TextView welcome;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sp = getSharedPreferences(contantSp.PREF, MODE_PRIVATE);
        db = openOrCreateDatabase(contantSp.PREF,MODE_PRIVATE,null);
        String UserTable = "CREATE TABLE IF NOT EXISTS user(userid integer PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),email VARCHAR(30),contact VARCHAR(10),password VARCHAR(20))";
        db.execSQL(UserTable);

        delete = findViewById(R.id.dashboard_delete);
        logout = findViewById(R.id.dashboard_logout);
        profile = findViewById(R.id.dashboard_profile);
        welcome = findViewById(R.id.dashboard_welcome);
        category = findViewById(R.id.dashboard_category);

        welcome.setText("Welcome" +sp.getString(contantSp.name,""));

        delete.setOnClickListener(view -> {
            db.execSQL("DELETE FROM user WHERE userid = "+sp.getInt(contantSp.userid,0));
            sp.edit().clear().apply();
            Snackbar.make(view,"Sure to delete your account",Snackbar.LENGTH_SHORT).setAction("yes",v -> {
               startActivity(new Intent(dashboard.this,MainActivity.class));
               finish();
            }).show();
        });

            logout.setOnClickListener(view -> {
                sp.edit().clear().apply();
                startActivity(new Intent(dashboard.this,MainActivity.class));
                finish();
            });

            profile.setOnClickListener(View -> {
                startActivity(new Intent(dashboard.this,profile.class));
            });
            category.setOnClickListener(View -> {
                startActivity(new Intent(dashboard.this,category.class));
            });

    }
}