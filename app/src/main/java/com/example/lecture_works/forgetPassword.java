package com.example.lecture_works;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class forgetPassword extends AppCompatActivity {

    EditText email,newpass, confirmpass;
    Button changepass;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        db = openOrCreateDatabase("lecture_works",MODE_PRIVATE,null);
        String UserTable = "CREATE TABLE IF NOT EXISTS User(userid integer PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),email VARCHAR(30),contact VARCHAR(10),password VARCHAR(20))";
        db.execSQL(UserTable);

        email =findViewById(R.id.email_forget);
        newpass = findViewById(R.id.new_password_forget);
        confirmpass = findViewById(R.id.confirm_new_password_forget);
        changepass = findViewById(R.id.change_password_btn);

        changepass.setOnClickListener(View -> {
            String email_text = email.getText().toString().trim();
            String newpass_text = newpass.getText().toString().trim();
            String confirmpass_text = confirmpass.getText().toString().trim();

            if (email_text.isEmpty()){
                email.setError("enter your email");
            }
            else if (newpass_text.isEmpty()){
                newpass.setError("enter your password");
                newpass.requestFocus();

            } else if (newpass_text.length()<6) {
                newpass.setError("Enter valid password");
                newpass.requestFocus();
            }
            else if(confirmpass_text.isEmpty()){
                newpass.setError("Enter your password");
                newpass.requestFocus();
            } else if (!newpass_text.equals(confirmpass_text)) {
                confirmpass.setError("password do not match");
                confirmpass.requestFocus();
            }else {

                String checkUser = "SELECT * FROM user WHERE email = '"+email+"' ";
                Cursor cursor = db.rawQuery(checkUser,null);
                if (cursor.getCount() > 0){
                    String updatePassword = "UPDATE user SET password = '"+newpass_text+"' WHERE email ='"+email_text+"' ";
                    db.execSQL(updatePassword);
                    Toast.makeText(this,"Pasword Changed Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(forgetPassword.this,MainActivity.class));
                    finish();
                }
                else {
                    email.setError("User does not exist");
                    email.requestFocus();
                }
            }
        });
    }
}