package com.example.lecture_works;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
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

public class signUp extends AppCompatActivity {


    TextView login;
    EditText name,email,number,password,confirm_pass;
    Button signup;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = openOrCreateDatabase(contantSp.PREF,MODE_PRIVATE,null);
        sp = getSharedPreferences(contantSp.PREF, MODE_PRIVATE);

        String UserTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),email VARCHAR(30),contact VARCHAR(10),password VARCHAR(20))";
        db.execSQL(UserTable);

        name = findViewById(R.id.name_signup);
        email = findViewById(R.id.email_signup);
        number = findViewById(R.id.number_signup);
        password = findViewById(R.id.password_signup);
        confirm_pass = findViewById(R.id.confirm_password_signup);
        signup = findViewById(R.id.signup_button);
        login = findViewById(R.id.login_text);

        signup.setOnClickListener(View -> {
            String name_text = name.getText().toString();
            String email_text = email.getText().toString();
            String number_text = number.getText().toString();
            String password_text = password.getText().toString();
            String confirm_pass_text = confirm_pass.getText().toString();

            if(name_text.isEmpty()){
                name.setError("Enter your name");
                name.requestFocus();
                return;
            }
            if (email_text.isEmpty()){
                email.setError("enter your email");
                email.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email_text).matches()){
                email.setError("Enter valid email");
                email.requestFocus();
                return;
            }
            if(number_text.isEmpty()){
                number.setError("Enter your number");
                number.requestFocus();
                return;
            }
            if(number_text.length() != 10){
                number.setError("Enter atleat 10 number");
                number.requestFocus();
                return;
            }
            if (password_text.isEmpty()){
                password.setError("Enter your password");
                password.requestFocus();
                return;
            }
            if(password_text.length()<6){
                password.setError("Enter valid password");
                password.requestFocus();
                return;
            }
            if (confirm_pass_text.isEmpty()){
                confirm_pass.setError("Enter your password");
                confirm_pass.requestFocus();
                return;
            }
            if (!confirm_pass_text.equals(password_text)){
                confirm_pass.setError("Password does't match");
                confirm_pass.requestFocus();
                return;
            }else {
                String CheckUser = "SELECT * FROM user WHERE email = '"+email_text+"' OR contact ='"+number_text+"' ";
                Cursor cursor = db.rawQuery(CheckUser,null);

                if(cursor.getCount()>0){
                    Toast.makeText(this,"user already exists",Toast.LENGTH_SHORT).show();
                }
                else {
                    String insertUser = "INSERT INTO user VALUES(NULL,'"+name_text+"','"+email_text+"','"+number_text+"','"+password_text+"')";
                    db.execSQL(insertUser);
                    sp.edit()
                            .putString(contantSp.name,name_text)
                            .putString(contantSp.email,email_text)
                            .putString(contantSp.contact,number_text)
                            .putString(contantSp.password,password_text)
                            .apply();

                    Toast.makeText(this,"User Added Successfully!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signUp.this, dashboard.class));
                    finish();
                }
            }
        });

        login.setOnClickListener(view -> {
            startActivity(new Intent(signUp.this, MainActivity.class));
            finish();
        });

    }
}