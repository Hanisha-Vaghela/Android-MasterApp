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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class wishlist extends AppCompatActivity {

        RecyclerView wishlist_recycler;
        SharedPreferences sp;
        SQLiteDatabase db;
        ArrayList<array_wishlist> arraylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);


        sp = getSharedPreferences(contantSp.PREF, MODE_PRIVATE);
        db = openOrCreateDatabase(contantSp.PREF, MODE_PRIVATE, null);

        String UserTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),email VARCHAR(30),contact VARCHAR(10),password VARCHAR(20))";
        db.execSQL(UserTable);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),image VARCHAR)";
        db.execSQL(categoryTable);

        String subcategoryTable = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT,categoryid INTEGER,name VARCHAR(50),image VARCHAR)";
        db.execSQL(subcategoryTable);

        String productTable = "CREATE TABLE IF NOT EXISTS product(productid INTEGER PRIMARY KEY AUTOINCREMENT, subcategoryid INTEGER, name VARCHAR(50), image VARCHAR, discountprice VARCHAR(10), originalprice VARCHAR(10))";
        db.execSQL(productTable);

        String wishlistTable = "CREATE TABLE IF NOT EXISTS wishlist(wishlistid INTEGER PRIMARY KEY AUTOINCREMENT, productid INTEGER ,userid INTEGER)";
        db.execSQL(wishlistTable);

        wishlist_recycler= findViewById(R.id.wishlist_item_xml);

        wishlist_recycler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        wishlist_recycler.setItemAnimator(new DefaultItemAnimator());

        arraylist = new ArrayList<>();

        String fetchWishlist = "SELECT * FROM wishlist WHERE userid = '"+sp.getInt(contantSp.userid,0)+"' ";
        Cursor cursor = db.rawQuery(fetchWishlist,null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                array_wishlist list = new array_wishlist();
                //sir ne ahiya int chhe mare error ave chhe jo hu int lau to
                list.setWishlistid(String.valueOf(cursor.getInt(0)));

                String getProducts = "SELECT * FROM product WHERE productid = '" + cursor.getString(1) + "' ";
                Cursor productCursor = db.rawQuery(getProducts, null);

                if (productCursor.getCount() > 0) {
                    while (productCursor.moveToNext()) {
                        list.setProductid(String.valueOf(productCursor.getInt(0)));
                        list.setSubcategoryid(productCursor.getString(1));
                        list.setName(productCursor.getString(2));
                        list.setImage(Integer.parseInt(productCursor.getString(3)));
                        list.setPrice(productCursor.getString(4));
                        list.setWishlist(true);
//                        list.setDescription(productCursor.getString(5));

                        if (productCursor.getCount() > 0) {
                            list.setWishlist(true);
                        } else {
                            list.setWishlist(false);
                        }

                    }
//                    array_wishlist List = new array_wishlist();

                }
//                cursor.close();
                productCursor.close();
                arraylist.add(list);

//            String fetchProductFromWishlist = "SELECT * FROM product WHERE productid = '"+productId+"' ";
//            Cursor productCursor = db.rawQuery(fetchProductFromWishlist,null);

            }
        }

        wishlistAdapter adapter = new wishlistAdapter(wishlist.this,arraylist,db);
        wishlist_recycler.setAdapter(adapter);
    }


}