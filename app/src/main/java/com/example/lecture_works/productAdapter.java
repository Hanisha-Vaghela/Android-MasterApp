package com.example.lecture_works;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
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
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;
        TextView originalprice;
        TextView disscountprice;
        TextView description;
        TextView badge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            image = itemView.findViewById(R.id.product_image);
            originalprice = itemView.findViewById(R.id.product_original_price);
            disscountprice = itemView.findViewById(R.id.product_discounted_price);
            description = itemView.findViewById(R.id.product_description);
            badge = itemView.findViewById(R.id.product_badge);
        }
    }

    @NonNull
    @Override
    public productAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new productAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductList item = arrayList.get(position);

        holder.name.setText(item.getProductname());
        holder.image.setImageResource(item.getProductimage());
        holder.disscountprice.setText("₹" + item.getDisscountprice());
        holder.originalprice.setText("₹" + item.getOriginalprice());
//        holder.originalprice.setPaintFlags(holder.originalprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//        if (holder.description != null && item.getDescription() != null) {
//            holder.description.setText(item.getDescription());
//        }
//
//        if (holder.badge != null && item.getProductdiscount() != null && !item.getProductdiscount().isEmpty()) {
//            holder.badge.setText(item.getProductdiscount());
//            holder.badge.setVisibility(View.VISIBLE);
//        }

        holder.itemView.setOnClickListener(view -> {
//            int pos = holder.getAdapterPosition();
//            if (pos == RecyclerView.NO_POSITION) return;
            ProductList selected = arrayList.get(position);

            sp.edit()
                    .putInt(contantSp.PRODUCT_ID, selected.getProductid())
                    .putString(contantSp.PRODUCT_NAME, selected.getProductname())
                    .putInt(contantSp.PRODUCT_IMG, selected.getProductimage())
                    .putInt(contantSp.ORIGINAL_PRICE, selected.getOriginalprice())
                    .putInt(contantSp.DISSCOUNT_PRICE, selected.getDisscountprice())
                    .putString(contantSp.DESCRIPTION, selected.getDescription())
                    .putString(contantSp.PRODUCT_DISSCOUNT, selected.getProductdiscount())
                    .commit();

//            startActivity(new Intent(productAdapter.this, product_details.class));


            Intent intent = new Intent(context, product_details.class);
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
