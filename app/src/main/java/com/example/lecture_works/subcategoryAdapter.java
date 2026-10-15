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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;
                int subcatId = arrayList.get(pos).getSubid();
                sp.edit().putInt(contantSp.SUBCATEGORY_ID, subcatId).commit();
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra(contantSp.SUBCATEGORY_ID, subcatId);
                context.startActivity(intent);
            }
        });
    }

//        public void onBindViewHolder (@NonNull subcategoryAdapter.ViewHolder holder,int position){
////        SubCategoryList item = arrayList.get(position);
//
//            holder.subname.setText(arrayList.get(position).getSubname());
//            holder.subimage.setImageResource(arrayList.get(position).getSubimage());
//
//            holder.itemView.setOnClickListener(view -> {
//                int pos = holder.getAdapterPosition();
//
//                if (pos == RecyclerView.NO_POSITION) {
//                    return;
//                }
//
//                SubCategoryList item = arrayList.get(pos);
//
//                int subcategoryId = item.getSubid();
//
//                sp.edit()
//                        .putInt(contantSp.SUBCATEGORY_ID, subcategoryId)
//                        .apply();
//
//                Log.d("SUB_CLICK", "Subcategory ID: " + subcategoryId);
//
//                Intent intent = new Intent(context, ProductActivity.class);
//                context.startActivity(intent);
//            });
//        }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
