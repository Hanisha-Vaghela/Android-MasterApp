package com.example.lecture_works;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class product_details extends AppCompatActivity {

    ImageView detailimage,productwishlist,productcart;
    TextView productdetailname,productdiscountprice,productrealprice,productdiscount;
    Button buynow;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        sp = getSharedPreferences(contantSp.PREF,MODE_PRIVATE);
        db = openOrCreateDatabase(contantSp.PREF,MODE_PRIVATE,null);

        String UserTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),email VARCHAR(30),contact VARCHAR(10),password VARCHAR(20))";
        db.execSQL(UserTable);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),image VARCHAR)";
        db.execSQL(categoryTable);

        String subcategoryTable = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT,categoryid INTEGER,name VARCHAR(50),image VARCHAR)";
        db.execSQL(subcategoryTable);

        String productTable = "CREATE TABLE IF NOT EXISTS product(productid INTEGER PRIMARY KEY AUTOINCREMENT, subcategoryid INTEGER, name VARCHAR(50), image VARCHAR, discountprice VARCHAR(10), originalprice VARCHAR(10))";
        db.execSQL(productTable);


        detailimage = findViewById(R.id.product_detail_image);
        productwishlist = findViewById(R.id.product_detail_wishlist);
        productcart = findViewById(R.id.product_detail_cart);
        productdetailname = findViewById(R.id.product_detail_name);
        productdiscountprice = findViewById(R.id.product_detail_discountprice);
        productrealprice = findViewById(R.id.product_detail_realprice);
        productdiscount = findViewById(R.id.product_detail_discount);
        buynow = findViewById(R.id.buy_now);

        detailimage.setImageResource(sp.getInt(contantSp.PRODUCT_IMG,0));
        productdetailname.setText(sp.getString(contantSp.PRODUCT_NAME,1));
        productdiscountprice.setText(sp.getInt(contantSp.DISSCOUNT_PRICE,2));
        productrealprice.setText(sp.getInt(contantSp.ORIGINAL_PRICE,3));
        productdiscount.setText(sp.getString(contantSp.PRODUCT_DISSCOUNT ,4));

    }
}
