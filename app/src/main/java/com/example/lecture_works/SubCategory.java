package com.example.lecture_works;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class SubCategory extends AppCompatActivity {

    RecyclerView subcategoryRV;

    int[] subidArray = {1,2,3,4,5,6,7,8,9,10,11};

    int[] catidArray = {2,2,3,4,7,5,6,6,7,7,1};

    String[] subnameArray = {"shoes", "sendal", "Smart Watches", "DSLR Cameras", "Wireless Headphones",
                                "Backpacks", "T-Shirts","shirts", "Bluetooth Speakers", "Mobiles","The power of Nunchi"};
    int[] imageArray = {R.drawable.footwear_man,R.drawable.footwear_man,R.drawable.watches,R.drawable.dsrl_camera,R.drawable.wireless_headphone,
            R.drawable.bags,R.drawable.t_shirt,R.drawable.shirts, R.drawable.bluetooth_spe,R.drawable.mobile,R.drawable.subbook};
    SQLiteDatabase db;
    ArrayList<SubCategoryList> arraylist;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        sp = getSharedPreferences(contantSp.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase(contantSp.PREF,MODE_PRIVATE,null);
        String UserTable = "CREATE TABLE IF NOT EXISTS User(userid integer PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),email VARCHAR(30),contact VARCHAR(10),password VARCHAR(20))";
        db.execSQL(UserTable);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),image VARCHAR)";
        db.execSQL(categoryTable);

        String subcategoryTable = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT,categoryid INTEGER,name VARCHAR(50),image VARCHAR)";
        db.execSQL(subcategoryTable);

        subcategoryRV = findViewById(R.id.subcategoryRV);

        for(int i = 0; i<subidArray.length;i++){
            String checkCategory = "SELECT * FROM subcategory WHERE name = '"+subnameArray[i]+"' ";
            Cursor cursor = db.rawQuery(checkCategory,null);
            if(cursor.getCount() == 0){
                String insertsubCategory = "INSERT INTO subcategory VALUES(NULL, '"+catidArray[i]+"', '"+subnameArray[i]+"', '"+imageArray[i]+"' )";
                db.execSQL(insertsubCategory);
            }
        }

        arraylist = new ArrayList<>();

        String fetchCategory = "SELECT * FROM subcategory WHERE categoryid = '"+sp.getInt(contantSp.CATEGORY_ID,0)+"'  ";
        Cursor subCatcursor = db.rawQuery(fetchCategory,null);

        if(subCatcursor.getCount()>0){
            while(subCatcursor.moveToNext()){
                SubCategoryList list = new SubCategoryList();
                list.setSubid(subCatcursor.getInt(0));
                list.setCatId(subCatcursor.getInt(1));
                list.setSubname(subCatcursor.getString(2));
                list.setSubimage(subCatcursor.getInt(3));


                arraylist.add(list);
            }
        }

        subcategoryRV.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        subcategoryAdapter adapter = new subcategoryAdapter(SubCategory.this,arraylist);
        subcategoryRV.setAdapter(adapter);
    }
}