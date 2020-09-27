package com.example.foreverfindvihangi;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foreverfindvihangi.interfaces.ItemClickListner;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProName,txtProPrice,txtProQuantity;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProName = itemView.findViewById(R.id.product_name_cartI);
        txtProPrice = itemView.findViewById(R.id.prodctPrice_cartI);
        txtProQuantity = itemView.findViewById(R.id.productQun_cartI);
    }

    @Override
    public void onClick(View view) {

        itemClickListner.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
