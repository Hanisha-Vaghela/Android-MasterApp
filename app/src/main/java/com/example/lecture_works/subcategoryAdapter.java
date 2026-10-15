package com.example.lecture_works;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class subcategoryAdapter extends RecyclerView.Adapter<subcategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<SubCategoryList> arrayList;
    SharedPreferences sp;
public subcategoryAdapter(Context context, ArrayList<SubCategoryList> arrayList) {
    this.context = context;
    this.arrayList = arrayList;
    sp = context.getSharedPreferences(contantSp.PREF, Context.MODE_PRIVATE);
}
    @NonNull
    @Override
    public subcategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_category_items,parent,false);
        return new subcategoryAdapter.ViewHolder(view);

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subname;
        ImageView subimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subname = itemView.findViewById(R.id.subcategory_name);
            subimage = itemView.findViewById(R.id.subcategory_image);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull subcategoryAdapter.ViewHolder holder, int position) {
//        SubCategoryList item = arrayList.get(position);

        holder.subname.setText(arrayList.get(position).getSubname());
        holder.subimage.setImageResource(arrayList.get(position).getSubimage());

        holder.itemView.setOnClickListener(view ->{

            sp.edit().putInt(contantSp.SUBCATEGORY_ID,arrayList.get(position).getSubid()).apply();
//            context.startActivity(new Intent(context,Product.class));
            Intent intent = new Intent(context, Product.class);
            context.startActivity(intent);
        });

//
//@Override
//public void onBindViewHolder(@NonNull subcategoryAdapter.ViewHolder holder, int position) {
//    holder.subname.setText(arrayList.get(position).getSubname());
//    holder.subimage.setImageResource(arrayList.get(position).getSubimage());
//
//    holder.itemView.setOnClickListener(view -> {
//        int pos = holder.getAdapterPosition();
//        Log.d("SUB_CLICK", "clicked id = " + arrayList.get(pos).getSubid());
//        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
//
//        sp.edit().putInt(contantSp.SUBCATEGORY_ID, arrayList.get(pos).getSubid()).apply();
//        context.startActivity(new Intent(context, Product.class));
//    });
}

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
