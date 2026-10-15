package com.example.lecture_works;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class profile extends AppCompatActivity {

    EditText name,email,contact,password,confirm_password;
    ImageView back;
    Button edit_profile,update_profile;
    SQLiteDatabase db;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = openOrCreateDatabase("lecture_works",MODE_PRIVATE,null);
        sp =getSharedPreferences(contantSp.PREF,MODE_PRIVATE);

        String UserTable = "CREATE TABLE IF NOT EXISTS User(userid integer PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),email VARCHAR(30),contact VARCHAR(10),password VARCHAR(20))";
        db.execSQL(UserTable);


        name = findViewById(R.id.name_profile);
        email = findViewById(R.id.email_profile);
        contact = findViewById(R.id.contact_profile);
        password = findViewById(R.id.password_profile);
        confirm_password = findViewById(R.id.confirm_password_profile);
        edit_profile = findViewById(R.id.profile_btn);
        update_profile = findViewById(R.id.update_profile_btn);
        back = findViewById(R.id.back);

        name.setText(sp.getString(contantSp.name,""));
        email.setText(sp.getString(contantSp.email,""));
        contact.setText(sp.getString(contantSp.contact,""));
        password.setText(sp.getString(contantSp.password,""));
        confirm_password.setText(sp.getString(contantSp.password,""));

        setData(false);

        back.setOnClickListener(view -> {
            startActivity(new Intent(profile.this,dashboard.class));
            finish();
        });

        edit_profile.setOnClickListener(view -> {
            setData(true);
            confirm_password.setVisibility(ImageView.VISIBLE);
            edit_profile.setVisibility(View.GONE);
            update_profile.setVisibility(View.VISIBLE);
        });

        update_profile.setOnClickListener(view -> {

            if (name.getText().toString().isEmpty()){
                name.setError("Enter Name");
                name.requestFocus();
                return;
            }
            if (contact.getText().toString().isEmpty()){
                contact.setError("Enter Contact");
                contact.requestFocus();
                return;
            }
            if (contact.getText().toString().length() != 10){
                contact.setError("Contact number must be 10 digits");
                contact.requestFocus();
                return;
            }
            if (password.getText().toString().isEmpty()){
                password.setError("Enter Password");
                password.requestFocus();
                return;
            }
            if (password.getText().toString().length() < 6){
                password.setError("Password must be 6 characters long");
                password.requestFocus();
                return;
            }
            if (confirm_password.getText().toString().isEmpty()){
                confirm_password.setError("Enter Confirm Password");
                confirm_password.requestFocus();
                return;
            }
            if (!confirm_password.getText().toString().equals(password.getText().toString())){
                confirm_password.setError("Password does not match");
                confirm_password.requestFocus();
                return;
            }

            else{
                db.execSQL("UPDATE user SET name = '"+name.getText().toString()+"',contact = '"+contact.getText().toString()+"', password = '"+password.getText().toString()+"' WHERE email = '"+email.getText().toString()+"' ");
                sp.edit()
                        .putString(contantSp.name,name.getText().toString())
                        .putString(contantSp.contact,contact.getText().toString())
                        .putString(contantSp.password,password.getText().toString())
                        .apply();
                setData(false);

                confirm_password.setVisibility(View.GONE);
                update_profile.setVisibility(View.GONE);
                edit_profile.setVisibility(View.GONE);

                Toast.makeText(this,"Profile update Successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(profile.this,dashboard.class));
                finish();
            }
        });
    }
    void setData(boolean b) {
    name.setEnabled(b);
    email.setEnabled(b);
    contact.setEnabled(b);
    password.setEnabled(b);
    }
}