package com.example.lecture_works;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kotlin.internal.InlineOnly;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {
    Context context;
//    int[] idArray;
//    String[] nameArray;
//    int[] imageArray;
    ArrayList<ProductList> arrayList;

    SharedPreferences sp;
    public productAdapter(Context context, ArrayList<ProductList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sp = context.getSharedPreferences(contantSp.PREF, Context.MODE_PRIVATE);

    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView image;
        TextView originalprice;
        TextView disscountprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            image = itemView.findViewById(R.id.product_image);
            originalprice = itemView.findViewById(R.id.product_original_price);
            disscountprice = itemView.findViewById(R.id.product_discounted_price);
        }
    }

    @NonNull
    @Override
    public productAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new productAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productAdapter.ViewHolder holder, int position) {
//    holder.name.setText(nameArray[position]);
//    holder.image.setImageResource(imageArray[position]);
//    ProductList item = arrayList.get(position);

        holder.name.setText(arrayList.get(position).getProductname());
        holder.image.setImageResource(arrayList.get(position).getProductimage());
        holder.disscountprice.setText(String.valueOf(arrayList.get(position).getProductname()));
        holder.originalprice.setText(String.valueOf(arrayList.get(position).getProductname()));

        holder.itemView.setOnClickListener(view -> {
            sp.edit()
//                    .putInt(contantSp.PREF,arrayList.get(position).getProductid()).apply();
                    .putInt(contantSp.PRODUCT_ID,arrayList.get(position).getProductid())
                    .putString(contantSp.PRODUCT_NAME,arrayList.get(position).getProductname())
                    .putInt(contantSp.PRODUCT_IMG,arrayList.get(position).getProductimage())
                    .putInt(contantSp.ORIGINAL_PRICE,arrayList.get(position).getOriginalprice())
                    .putInt(contantSp.DISSCOUNT_PRICE,arrayList.get(position).getDisscountprice())
                    .putString(contantSp.DESCRIPTION,arrayList.get(position).getDescription())
                    .putString(contantSp.WISHLIST,arrayList.get(position).getWishlist())
                    .putString(contantSp.PRODUCT_DISSCOUNT,arrayList.get(position).getProductdiscount())
                    .apply();
            Intent intent = new Intent(context,product_details.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
//        return 0;
//        return idArray.length;
    return arrayList.size();
    }
}
