package com.example.lecture_works;

import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView forgotpage,create_new_accout;

    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = openOrCreateDatabase("lecture_works",MODE_PRIVATE,null);
        sp = getSharedPreferences(contantSp.PREF, MODE_PRIVATE);

        String UserTable = "CREATE TABLE IF NOT EXISTS User(userid integer PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),email VARCHAR(30),contact VARCHAR(10),password VARCHAR(20))";
        db.execSQL(UserTable);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.login_button);
        forgotpage = findViewById(R.id.forgot_pass);
        create_new_accout = findViewById(R.id.create_new_account);

        login.setOnClickListener(View -> {
            String email_text = email.getText().toString();
            String pass_text = password.getText().toString();

            if (email_text.isEmpty()){
                email.setError("Enter your email");
                email.requestFocus();
            }
            else if (pass_text.isEmpty()){
                password.setError("Enter your password");
                password.requestFocus();
            }
            else {

                String existUser = "SELECT * FROM user WHERE email = '"+email_text+"' AND password='"+pass_text+"' ";
                Cursor cursor = db.rawQuery(existUser,null);
                if(cursor.moveToFirst()){
                    sp.edit()
                            .putInt(contantSp.userid,cursor.getInt(0))
                            .putString(contantSp.name,cursor.getString(1))
                            .putString(contantSp.email,cursor.getString(2))
                            .putString(contantSp.contact,cursor.getString(3))
                            .putString(contantSp.password,cursor.getString(4))

                            .apply();
                    Toast.makeText(this, "welcome again", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, dashboard.class));
                    finish();

                }
                else{

                    Toast.makeText(this, "user not exits", Toast.LENGTH_SHORT).show();
                }
                 }
        });

        forgotpage.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, forgetPassword.class));
        });
        create_new_accout.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, signUp.class));
        });
    }
}