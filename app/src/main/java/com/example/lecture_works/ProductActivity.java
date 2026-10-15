package com.example.lecture_works;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView product;
    SQLiteDatabase db;
    ArrayList<ProductList> arrayList;
    SharedPreferences sp;
    int[] productidArray = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21};
        int[] SubCatidArray = {6,6,6,2,2,5,6,6,10,10,6,1,3,4,6,8,7,7,3,1,1};
//    int[] productCatidArray = {
//            6, 6, 6,   // Tote, Sling, Mini -> Backpacks
//            2, 2,      // Flat sandals, hiking sandals -> sendal
//            5,         // Buds -> Wireless Headphones
//            0, 0,      // Notebook, PenSet -> no subcategory yet
//            10, 10,    // Foldable Phones, Iphone -> Mobiles
//            0,         // Water Bottle -> no subcategory yet
//            1,         // Sneakers -> shoes
//            3,         // Analog Watch -> Smart Watches
//            4,         // Camera -> DSLR Cameras
//            11,        // Diary -> book subcategory
//            8, 8,      // formal shirt, formal top -> shirts
//            7,         // plain tshirt -> T-Shirts
//            3,         // titan watch -> Smart Watches
//            1, 1       // Oxford shoes, formal shoes -> shoes
//    };
    String[] nameArray = {
            "Tote Bag", //1
            "Sling Bag", //2
            "Mini Bag", //3
            "Flat sandals", //4
            "hiking sandals", //5
            "Buds", //6
            "Notebook", //7
            "PenSet", //8
            "Foldable Phones", //9
            "Iphone", //10
            "Water Bottle", //11
            "Sneakers", //12
            "Analog Watch",  //13
            "Camera", //14
            "Diary" , //15
            "formal shirt", //16
            "formal top", //17
            "plain tshirt", //18
            "titan watch", //19
            "Oxford shoes", //20
            "formal shoes", //21

    };
    int[] imageArray = {
            R.drawable.tote_bag,
            R.drawable.sling_bag,
            R.drawable.mini_bag,
            R.drawable.flatesandal,
            R.drawable.hiking_sandal,
            R.drawable.buds,
            R.drawable.notebook,
            R.drawable.penset,
            R.drawable.foldeble_phone,
            R.drawable.iphone,
            R.drawable.water_bottle,
            R.drawable.sneakers_girls,
            R.drawable.analog_watch,
            R.drawable.camera,
            R.drawable.diary,
            R.drawable.formal_shirt, //for women
            R.drawable.formal_top,
            R.drawable.plain_tshirt,
            R.drawable.watches,
            R.drawable.oxford_shoes,
            R.drawable.formal_shoes,
    };
    int[] originalPricesArray = {
            1499,  // Tote Bag
            1299,  // Sling Bag
            999,   // Mini Bag
            799,   // Flower Vase
            499,   // Candle
            1899,  // Wall Mirror
            349,   // Notebook
            249,   // Pen Set
            599,   // Desk Organizer
            399,   // Coffee Mug
            699,   // Water Bottle
            2499,  // Sneakers
            1999,  // Analog Watch
            3999,  // Camera
            299 ,   // Diary
            // original prices (continue after index 15)
            1799,  // formal shirt   //16
            1499,  // formal top     //17
            699,   // plain tshirt   //18
            4999,  // titan watch    //19
            3499,  // Oxford shoes   //20
            2999   // formal shoes   //21
    };

    int[] discountedPricesArray = {
            1199,  // Tote Bag
            999,   // Sling Bag
            799,   // Mini Bag
            599,   // Flower Vase
            399,   // Candle
            1499,  // Wall Mirror
            249,   // Notebook
            199,   // Pen Set
            449,   // Desk Organizer
            299,   // Coffee Mug
            549,   // Water Bottle
            1999,  // Sneakers
            1599,  // Analog Watch
            3299,  // Camera
            199,    // Diary//
            // discounted prices (continue after index 15)
            1399,  // formal shirt   //16
            1199,  // formal top     //17
            499,   // plain tshirt   //18
            3999,  // titan watch    //19
            2799,  // Oxford shoes   //20
            2299   // formal shoes   //21
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

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



        product = findViewById(R.id.productRV);

        for(int i = 0; i<productidArray.length;i++){
            String checkProduct = "SELECT * FROM product WHERE name = '"+nameArray[i]+"' ";
            Cursor cursor = db.rawQuery(checkProduct,null);
            if(cursor.getCount() == 0){
//                String insertProduct = "INSERT INTO product VALUES(NULL,'"+productCatidArray[i]+"', '"+nameArray[i]+"', '"+imageArray[i]+"', '"+discountedPricesArray+"','"+originalPricesArray+"' )";
                String insertProduct = "INSERT INTO product VALUES(NULL,'"+SubCatidArray[i]+"', '"+nameArray[i]+"', '"+imageArray[i]+"', '"+discountedPricesArray[i]+"','"+originalPricesArray[i]+"')";
                db.execSQL(insertProduct);

            }
        }

        arrayList = new ArrayList<>();
//        int clickedId = sp.getInt(contantSp.SUBCATEGORY_ID, 0);
//        Log.d("PRODUCT_DEBUG", "clicked subcategory id = " + clickedId);
//
//        Cursor all = db.rawQuery("SELECT productid, subcategoryid, name FROM product", null);
//        Log.d("PRODUCT_DEBUG", "total products in db = " + all.getCount());
//        while (all.moveToNext()) {
//            Log.d("PRODUCT_DEBUG", all.getInt(0) + " | subcat=" + all.getInt(1) + " | " + all.getString(2));
//        }

        String fetchProduct = "SELECT * FROM product WHERE subcategoryid = '"+sp.getInt(contantSp.SUBCATEGORY_ID,0) +"' ";
//        String fetchProduct = "SELECT * FROM product";
        Cursor cursor = db.rawQuery(fetchProduct,null);

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                ProductList list = new ProductList();
                list.setProductid(cursor.getInt(0));
//                list.setSubcatid(cursor.getInt(1));
                list.setProductname(cursor.getString(2));
                list.setProductimage(cursor.getInt(3));
                list.setDisscountprice(cursor.getInt(4));
                list.setOriginalprice(cursor.getInt(5));

                arrayList.add(list);
            }
        }

        product.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        productAdapter adapter = new productAdapter(ProductActivity.this,arrayList);
        product.setAdapter(adapter);

    }
}