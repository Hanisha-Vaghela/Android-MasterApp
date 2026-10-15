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
    int[] productidArray = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
    int[] SubCatidArray  = {6,6,6,2,2,5,11,11,10,10,6,1,3,4,11,8,7,7,3,1,1,9};

    String[] nameArray = {
            "Tote Bag",           // 1  -> Backpacks (6)
            "Sling Bag",          // 2  -> Backpacks (6)
            "Mini Bag",           // 3  -> Backpacks (6)
            "Flat sandals",       // 4  -> sendal (2)
            "hiking sandals",     // 5  -> sendal (2)
            "Buds",               // 6  -> Wireless Headphones (5)
            "Notebook",           // 7  -> Books (11)
            "PenSet",             // 8  -> Books (11)
            "Foldable Phones",    // 9  -> Mobiles (10)
            "Iphone",             // 10 -> Mobiles (10)
            "Water Bottle",       // 11 -> Backpacks (6)
            "Sneakers",           // 12 -> shoes (1)
            "Analog Watch",       // 13 -> Smart Watches (3)
            "Camera",             // 14 -> DSLR Cameras (4)
            "Diary",              // 15 -> Books (11)
            "formal shirt",       // 16 -> shirts (8)
            "formal top",         // 17 -> T-Shirts (7)
            "plain tshirt",       // 18 -> T-Shirts (7)
            "titan watch",        // 19 -> Smart Watches (3)
            "Oxford shoes",       // 20 -> shoes (1)
            "formal shoes",       // 21 -> shoes (1)
            "Bluetooth Speaker"   // 22 -> Bluetooth Speakers (9)
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
            R.drawable.formal_shirt,
            R.drawable.formal_top,
            R.drawable.plain_tshirt,
            R.drawable.watches,
            R.drawable.oxford_shoes,
            R.drawable.formal_shoes,
            R.drawable.bluetooth_spe
    };

    int[] originalPricesArray = {
            1499,  // Tote Bag
            1299,  // Sling Bag
            999,   // Mini Bag
            799,   // Flat sandals
            499,   // hiking sandals
            1899,  // Buds
            349,   // Notebook
            249,   // PenSet
            59999, // Foldable Phones
            79999, // Iphone
            699,   // Water Bottle
            2499,  // Sneakers
            1999,  // Analog Watch
            39999, // Camera
            299,   // Diary
            1799,  // formal shirt
            1499,  // formal top
            699,   // plain tshirt
            4999,  // titan watch
            3499,  // Oxford shoes
            2999,  // formal shoes
            2499   // Bluetooth Speaker
    };

    int[] discountedPricesArray = {
            1199,  // Tote Bag
            999,   // Sling Bag
            799,   // Mini Bag
            599,   // Flat sandals
            399,   // hiking sandals
            1499,  // Buds
            249,   // Notebook
            199,   // PenSet
            49999, // Foldable Phones
            69999, // Iphone
            549,   // Water Bottle
            1999,  // Sneakers
            1599,  // Analog Watch
            32999, // Camera
            199,   // Diary
            1399,  // formal shirt
            1199,  // formal top
            499,   // plain tshirt
            3999,  // titan watch
            2799,  // Oxford shoes
            2299,  // formal shoes
            1799   // Bluetooth Speaker
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

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

        product = findViewById(R.id.productRV);

        for (int i = 0; i < productidArray.length; i++) {
            String checkProduct = "SELECT * FROM product WHERE productid = '" + productidArray[i] + "' OR name = '" + nameArray[i] + "' ";
            Cursor cursor = db.rawQuery(checkProduct, null);
            if (cursor.getCount() == 0) {
                String insertProduct = "INSERT INTO product VALUES('" + productidArray[i] + "','" + SubCatidArray[i] + "', '" + nameArray[i] + "', '" + imageArray[i] + "', '" + discountedPricesArray[i] + "','" + originalPricesArray[i] + "')";
                db.execSQL(insertProduct);
            } else {
                String updateProduct = "UPDATE product SET subcategoryid = '" + SubCatidArray[i] + "', name = '" + nameArray[i] + "', image = '" + imageArray[i] + "', discountprice = '" + discountedPricesArray[i] + "', originalprice = '" + originalPricesArray[i] + "' WHERE productid = '" + productidArray[i] + "' OR name = '" + nameArray[i] + "'";
                db.execSQL(updateProduct);
            }
            cursor.close();
        }

        arrayList = new ArrayList<>();

        int subcatId = getIntent().getIntExtra(contantSp.SUBCATEGORY_ID, sp.getInt(contantSp.SUBCATEGORY_ID, 0));
        if (subcatId != 0) {
            sp.edit().putInt(contantSp.SUBCATEGORY_ID, subcatId).apply();
        }

        String fetchProduct;
        if (subcatId == 0) {
            fetchProduct = "SELECT * FROM product";
        } else {
            fetchProduct = "SELECT * FROM product WHERE subcategoryid = '" + subcatId + "' ";
        }
        Cursor cursor = db.rawQuery(fetchProduct, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ProductList list = new ProductList();
                list.setProductid(cursor.getInt(0));
                list.setSubcatid(cursor.getInt(1));
                list.setProductname(cursor.getString(2));
                list.setProductimage(cursor.getInt(3));
                list.setDisscountprice(cursor.getInt(4));
                list.setOriginalprice(cursor.getInt(5));
                list.setDescription("High quality " + cursor.getString(2) + " with premium style and durability.");

                int orig = cursor.getInt(5);
                int disc = cursor.getInt(4);
                if (orig > disc && orig > 0) {
                    int percent = ((orig - disc) * 100) / orig;
                    list.setProductdiscount(percent + "% OFF");
                } else {
                    list.setProductdiscount("Special Offer");
                }

                arrayList.add(list);
            }
        }
        cursor.close();

        product.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        productAdapter adapter = new productAdapter(ProductActivity.this, arrayList);
        product.setAdapter(adapter);

    }
}