package com.example.foreverfind;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foreverfind.database.DBReference;
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

public class DeliveryStatusAdapter extends FirebaseRecyclerAdapter<
        Order,DeliveryStatusAdapter.deliveryViewholder> {

    String key;
    String k = "";
    Order ord = new Order();

      //  Order or=new Order();
        public DeliveryStatusAdapter(@NonNull FirebaseRecyclerOptions<Order> options){
        super(options);
        }

@Override
protected void onBindViewHolder(@NonNull final DeliveryStatusAdapter.deliveryViewholder holder,int position,@NonNull Order model){

            key = this.getRef(position).getKey();

            DatabaseReference orref = FirebaseDatabase.getInstance().getReference().child("Order").child(key);

            Query q = orref.orderByKey();

           q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   k += dataSnapshot.getKey();
                   ord.setOrderNo(k);
                   ord.setDaddress(dataSnapshot.child("daddress").toString());
                   ord.setRecName(dataSnapshot.child("recName").toString());
                   ord.setResPhone(dataSnapshot.child("resPhone").toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

      holder.odnum.setText(ord.getOrderNo());
      holder.rename.setText(ord.getRecName());
      holder.address.setText(ord.getDaddress());
      holder.remobile.setText(ord.getResPhone());
      holder.sender.setText(key);
      holder.status.setText("In Process");

   /* holder.delOrder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            final DBReference db = new DBReference();

            db.getRootRef().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    dataSnapshot.child(key).child(holder.odnum.getText().toString()).child("status").child(String.valueOf(true));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }); */

        }


    @NonNull
    @Override
    public deliveryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order02_view,parent,false);
        return new DeliveryStatusAdapter.deliveryViewholder(view);
    }


    class deliveryViewholder
        extends RecyclerView.ViewHolder {
    public TextView odnum, rename, address, status, remobile, sender;
    public Button delOrder;

    public deliveryViewholder(@NonNull final View itemView) {
        super(itemView);

        odnum = itemView.findViewById(R.id.odNo);
        rename = itemView.findViewById(R.id.Rname);
        sender = itemView.findViewById(R.id.Sname);
        address = itemView.findViewById(R.id.Addr);
        status = itemView.findViewById(R.id.Stat);
        remobile = itemView.findViewById(R.id.Rmobile);
        delOrder = (Button) itemView.findViewById(R.id.btnDelOrder02);



    }
}
}

