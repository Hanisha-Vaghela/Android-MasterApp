package com.example.lecture_works;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class wishlistAdapter extends RecyclerView.Adapter<wishlistAdapter.MyHolder> {
    Context context;

    ArrayList<array_wishlist> arrayList;
    SQLiteDatabase db;
    SharedPreferences sp;
    public wishlistAdapter(Context context,ArrayList<array_wishlist>arrayList,SQLiteDatabase db) {
        this.context = context;
        this.arrayList = arrayList;
        this.db = db;

        sp = context.getSharedPreferences(contantSp.PREF, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public wishlistAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_wishlist_item,parent,false);
        return new MyHolder(view);
    }
    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView image, wishlist;
        TextView text, price;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.wishlist_Product_image);
            text = itemView.findViewById(R.id.wishlist_text);
            price = itemView.findViewById(R.id.wishlist_price);
            wishlist = itemView.findViewById(R.id.wishlist_icon);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull wishlistAdapter.MyHolder holder, int position) {
    holder.image.setImageResource(arrayList.get(position).getImage());
    holder.text.setText(arrayList.get(position).getName());
    holder.price.setText(arrayList.get(position).getPrice());

    if (arrayList.get(position).isWishlist){
        holder.wishlist.setImageResource(R.drawable.wishlist_fill);
    }
    else {
        holder.wishlist.setImageResource(R.drawable.wishlist_empty);
    }

    holder.itemView.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View view){
            sp.edit().putString(contantSp.PRODUCT_ID,String.valueOf(arrayList.get(position).getProductid())).commit();
            sp.edit().putString(contantSp.PRODUCT_NAME,String.valueOf(arrayList.get(position).getName())).commit();
            sp.edit().putString(contantSp.PRODUCT_IMG,String.valueOf(arrayList.get(position).getImage())).commit();
            sp.edit().putString(contantSp.ORIGINAL_PRICE,String.valueOf(arrayList.get(position).getPrice())).commit();
//            sp.edit().putString(contantSp.DESCRIPTION,String.valueOf(arrayList.get(position).getDescription())).commit();

            Intent intent = new Intent(context,ProductActivity.class);
            context.startActivity(intent);

        }
    });

    holder.wishlist.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View view){
            if (arrayList.get(position).isWishlist){
                arrayList.get(position).setWishlist(false);
                String deleteWishlist = "DELETE FROM wishlist WHERE wishlistid = '"+arrayList.get(position).getWishlistid()+"' ";
                db.execSQL(deleteWishlist);
                holder.wishlist.setImageResource(R.drawable.wishlist_empty);
                Toast.makeText(context, "Removed From Wishlist", Toast.LENGTH_SHORT).show();
                arrayList.remove(position);
                notifyDataSetChanged();
            }
            else{
                arrayList.get(position).setWishlist(true);
                String insertWishlist = "INSERT INTO wishlist(productid, userid) VALUES('"+arrayList.get(position).getProductid()+"', '"+sp.getString(contantSp.userid,"")+"')";
                db.execSQL(insertWishlist);
                holder.wishlist.setImageResource(R.drawable.wishlist_fill);
                Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show();
            }
        }
    });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
