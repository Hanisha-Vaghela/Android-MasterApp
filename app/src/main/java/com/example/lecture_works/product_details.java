package com.example.lecture_works;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class product_details extends AppCompatActivity {

    ImageView detailimage,productwishlist,productcart;
    TextView productdetailname,productdiscountprice,productrealprice,productdiscount;

    //    for cart -,+
    TextView cart_qty;
    ImageView cart_icon, cart_minus,cart_add;
    LinearLayout cart_layout;
    Button buynow;
    Boolean togglewishlist;
    int cartTotal = 0;
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

        String wishlistTable = "CREATE TABLE IF NOT EXISTS wishlist (wishlistid INTEGER PRIMARY KEY AUTOINCREMENT, productid INTEGER, userid INTEGER)";
        db.execSQL(wishlistTable);

        String cartTable = "CREATE TABLE IF NOT EXISTS cart (cartid INTEGER PRIMARY KEY AUTOINCREMENT, cart INTEGER, userid INTEGER, productid INTEGER )";
        db.execSQL(cartTable);

        detailimage = findViewById(R.id.product_detail_image);
        productwishlist = findViewById(R.id.product_detail_wishlist);
        productcart = findViewById(R.id.product_detail_cart);
        productdetailname = findViewById(R.id.product_detail_name);
        productdiscountprice = findViewById(R.id.product_detail_discountprice);
        productrealprice = findViewById(R.id.product_detail_realprice);
        productdiscount = findViewById(R.id.product_detail_discount);
        buynow = findViewById(R.id.buy_now);

//        cart

        cart_icon = findViewById(R.id.product_detail_cart);
        cart_layout = findViewById(R.id.product_detail_cart_layout);
        cart_minus = findViewById(R.id.product_detail_cart_minus);
        cart_add = findViewById(R.id.product_detail_cart_add);
        cart_qty = findViewById(R.id.product_detail_cart_qty);

        // ahiyan aapde jaate value set akriye chhiye , naa ke arrayList mathi laaviye chhiye ,
        // to jate set karvani hoy tyare default value int mate 0 aapvani hoy and String mate "".
//        int imgRes = sp.getInt(contantSp.PRODUCT_IMG, 0);

        detailimage.setImageResource(sp.getInt(contantSp.PRODUCT_IMG, 0));
        productdetailname.setText(sp.getString(contantSp.PRODUCT_NAME, ""));
        productdiscountprice.setText(String.valueOf(sp.getInt(contantSp.DISSCOUNT_PRICE, 0)));
        productrealprice.setText(String.valueOf(sp.getInt(contantSp.ORIGINAL_PRICE, 0)));
        productdiscount.setText(sp.getString(contantSp.PRODUCT_DISSCOUNT, ""));
//        productwishlist.setText(String.valueOf(sp.getInt(contantSp.PRODUCT_DISCOUNT_PRICE, 0)));



// for discount
//
//        if (imgRes != 0) {
//            detailimage.setImageResource(imgRes);
//        }
//        productdetailname.setText(sp.getString(contantSp.PRODUCT_NAME, ""));
//        int discountPrice = sp.getInt(contantSp.DISSCOUNT_PRICE, 0);
//        int realPrice = sp.getInt(contantSp.ORIGINAL_PRICE, 0);
//        productdiscountprice.setText("₹" + discountPrice);
//        productrealprice.setText("₹" + realPrice);
//        productrealprice.setPaintFlags(productrealprice.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
//
//        String discountStr = sp.getString(contantSp.PRODUCT_DISSCOUNT, "");
//        if (discountStr != null && !discountStr.isEmpty()) {
//            productdiscount.setText(discountStr);
//        } else if (realPrice > discountPrice && realPrice > 0) {
//            int percent = ((realPrice - discountPrice) * 100) / realPrice;
//            productdiscount.setText(percent + "% OFF");
//        } else {
//            productdiscount.setText("Special Offer");
//        }
//
//        try {
//            togglewishlist = sp.getBoolean(contantSp.WISHLIST, false);
//        } catch (ClassCastException e) {
//            togglewishlist = false;
//            sp.edit().putBoolean(contantSp.WISHLIST, false).apply();
//        }
        /// end discount
        String checkWishlist = "SELECT * FROM wishlist WHERE productid = '"+sp.getInt(contantSp.PRODUCT_ID, 0)+"' AND userid = '"+sp.getString(contantSp.userid, "")+"'";
        Cursor cursor = db.rawQuery(checkWishlist, null);

        if (cursor.getCount()>0) {
            togglewishlist = true;
            productwishlist.setImageResource(R.drawable.wishlist_fill);
        } else {
            togglewishlist = false;
            productwishlist.setImageResource(R.drawable.wishlist_empty);
        }

        productwishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (togglewishlist) {
                    togglewishlist = false;
//                sp.edit().putBoolean(contantSp.WISHLIST, false).apply();
                    Toast.makeText(product_details.this, "Removed From WishList", Toast.LENGTH_SHORT).show();

                    String deleteFromWishlist = "DELETE FROM wishlist WHERE productid = '" + sp.getInt(contantSp.PRODUCT_ID, 0) + "' AND userid = '" + sp.getString(contantSp.userid, "") + "' ";
                    db.execSQL(deleteFromWishlist);

                    productwishlist.setImageResource(R.drawable.wishlist_empty);
                } else {
                    togglewishlist = true;
//                sp.edit().putBoolean(contantSp.WISHLIST, true).apply();
                    Toast.makeText(product_details.this, "Product added to wishlist", Toast.LENGTH_SHORT).show();

                    String insertInWishlist = "INSERT INTO wishlist VALUES(NULL, '" + sp.getInt(contantSp.PRODUCT_ID, 0) + "', '" + sp.getString(contantSp.userid, "") + "'  )";
                    db.execSQL(insertInWishlist);

                    productwishlist.setImageResource(R.drawable.wishlist_fill);

                }
            }
        });
            cart_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartTotal++;
                    cart_qty.setText(String.valueOf(cartTotal));
                    cart_layout.setVisibility(View.VISIBLE);
                    cart_icon.setVisibility(View.GONE);

                    String cartTable = "INSERT INTO cart VALUES(NULL, '" + sp.getInt(contantSp.CART, 0) + "', '" + sp.getString(contantSp.userid, "") + "' ,'" + sp.getString(contantSp.PRODUCT_ID, "") + "'  )";
                    db.execSQL(cartTable);

                    Toast.makeText(product_details.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                }
            });

        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(cartTotal > 1){
                cartTotal--;
                cart_qty.setText(String.valueOf(cartTotal));
                Toast.makeText(product_details.this, "Decrease To Cart", Toast.LENGTH_SHORT).show();
                }else {
                cartTotal = 0;
                cart_qty.setText("0");
                cart_layout.setVisibility(View.GONE);
                cart_icon.setVisibility(View.VISIBLE);
                String cartTable = "DELETE FROM wishlist WHERE productid = '" + sp.getInt(contantSp.PRODUCT_ID, 0) + "' AND userid = '" + sp.getString(contantSp.userid, "") + "' ";
                db.execSQL(cartTable);
                Toast.makeText(product_details.this, "Product Removed from cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
