package com.example.lecture_works;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.bumptech.glide.load.Option;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Locale;

public class category extends AppCompatActivity {
    RecyclerView categoryRV;
    SQLiteDatabase db;
    int[] idArray = {1,2,3,4,5,6,7};

    String[] nameArray = { "Books", "Footwear", "Watches", "Cameras",
                            "Bags", "Clothing", "Electronics"};

    int[] imageArray = {R.drawable.subbook,R.drawable.footwear_man,R.drawable.watches,
            R.drawable.dsrl_camera,R.drawable.bag,R.drawable.clothes,
            R.drawable.gadgets};

    ArrayList<CategoryList> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        db = openOrCreateDatabase(contantSp.PREF,MODE_PRIVATE,null);
        String UserTable = "CREATE TABLE IF NOT EXISTS user(userid integer PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),email VARCHAR(30),contact VARCHAR(10),password VARCHAR(20))";
        db.execSQL(UserTable);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),image VARCHAR)";
        db.execSQL(categoryTable);

        categoryRV = findViewById(R.id.category);

        for(int i = 0; i<idArray.length;i++){
            String checkCategory = "SELECT * FROM category WHERE name = '"+nameArray[i]+"' ";
            Cursor cursor = db.rawQuery(checkCategory,null);
            if(cursor.getCount() == 0){
                String insertCategory = "INSERT INTO category VALUES(NULL, '"+nameArray[i]+"', '"+imageArray[i]+"' )";
                db.execSQL(insertCategory);
            }
        }
        arraylist = new ArrayList<>();

        String fetchCategory = "SELECT * FROM category";
        Cursor cursor = db.rawQuery(fetchCategory,null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                CategoryList list = new CategoryList();
                list.setId(cursor.getInt(0));
                list.setName(cursor.getString(1));
                list.setImage(cursor.getInt(2));
                arraylist.add(list);
            }
        }


//        categoryRV.setLayoutManager(new LinearLayoutManager(category.this));
        categoryRV.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

//        categoryAdapter adapter = new categoryAdapter(category.this,idArray,nameArray,imageArray);
        categoryAdapter adapter = new categoryAdapter(category.this,arraylist);
        categoryRV.setAdapter(adapter);


    }
}