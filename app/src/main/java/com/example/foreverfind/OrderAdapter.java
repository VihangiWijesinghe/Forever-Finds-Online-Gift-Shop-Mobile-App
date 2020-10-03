package com.example.foreverfind;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foreverfind.model.Order;

import com.example.foreverfind.model.Payment;
import com.example.foreverfind.sessions.SessionManagement;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class OrderAdapter extends FirebaseRecyclerAdapter<
        Payment, OrderAdapter.ordersViewholder> {

    Payment py = new Payment();
    public OrderAdapter(@NonNull FirebaseRecyclerOptions<Payment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ordersViewholder holder, int position, @NonNull Payment model) {

        String key=model.getOrdNo();
        py = new Payment();

        SessionManagement sm = new SessionManagement(holder.itemView.getContext());
        String phn = sm.getUser();

        DatabaseReference dbf
                = FirebaseDatabase.getInstance().getReference().child("Order").child(phn);
        Query q = dbf.orderByKey().equalTo(key);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    String val = childSnapshot.child("status").getValue().toString();

                    if(val.equals("true"))
                        holder.delstatus.setText("Delivered");


                    else
                        holder.delstatus.setText("In Process");

                    holder.rname.setText(childSnapshot.child("recName").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        holder.odnum.setText(key);
        holder.date.setText(model.getPdate());

        // holder.amount.setText("Rs. "+childSnapshot.child("amount").getValue().toString());
        holder.amount.setText(model.getAmount());


    }

    @NonNull
    @Override
    public ordersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_view, parent, false);
        return new OrderAdapter.ordersViewholder(view);
    }


    class ordersViewholder
            extends RecyclerView.ViewHolder {
       public TextView odnum,date,amount,delstatus,rname;
        public ordersViewholder(@NonNull View itemView)
        {
            super(itemView);

            odnum = itemView.findViewById(R.id.ordno);
            date = itemView.findViewById(R.id.dat);
            amount = itemView.findViewById(R.id.am);
            delstatus = itemView.findViewById(R.id.status);
            rname = itemView.findViewById(R.id.rname);
        }
    }
}
