package com.example.lecture_works;

import static androidx.core.content.ContextCompat.startActivity;

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
import java.util.TimeZone;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {

    Context context;
    int[] idArray;
    String[] nameArray;
    int[] imageArray;
    ArrayList<CategoryList> arrayList;
    SharedPreferences sp;

//    public categoryAdapter(Context context, int[] idArray, String[] nameArray, int[] imageArray) {
//        this.context = context;
//        this.idArray = idArray;
//        this.nameArray = nameArray;
//        this.imageArray = imageArray;
//    }

    public categoryAdapter(Context context, ArrayList<CategoryList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sp =context.getSharedPreferences(contantSp.PREF,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public categoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new categoryAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.category_name);
            image = itemView.findViewById(R.id.category_image);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull categoryAdapter.ViewHolder holder, int position) {
//        holder.name.setText(nameArray[position]);
//        holder.image.setImageResource(imageArray[position]);
//        CategoryList item = arrayList.get(position);

        holder.name.setText(arrayList.get(position).getName());
        holder.image.setImageResource(arrayList.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;
                int catId = arrayList.get(pos).getId();
                sp.edit().putInt(contantSp.CATEGORY_ID, catId).commit();
                Intent intent = new Intent(context, SubCategory.class);
                intent.putExtra(contantSp.CATEGORY_ID, catId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
//        return idArray.length;
    }
}