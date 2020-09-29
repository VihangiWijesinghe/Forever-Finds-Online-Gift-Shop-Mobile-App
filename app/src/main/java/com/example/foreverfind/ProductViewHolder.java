package com.example.foreverfind;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foreverfind.interfaces.ItemClickListner;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView iv;
    public TextView pnamev,pPricev,pdesv;
    public ItemClickListner listner;
    public EditText etname;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        iv = (ImageView) itemView.findViewById(R.id.rImageView);
        pnamev = (TextView) itemView.findViewById(R.id.itemname);
        pPricev = (TextView)  itemView.findViewById(R.id.itemprice);
        pdesv = (TextView)  itemView.findViewById(R.id.itemdes);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }
    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }
}
