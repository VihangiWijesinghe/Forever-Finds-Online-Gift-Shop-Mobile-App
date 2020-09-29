package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.foreverfind.model.Order;
import com.example.foreverfind.model.Payment;
import com.example.foreverfind.sessions.SessionManagement;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Orders extends AppCompatActivity {

    private RecyclerView recyclerView;
    OrderAdapter
            adapter; // Create Object of the Adapter class
    DatabaseReference mbase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        SessionManagement sm = new SessionManagement(Orders.this);
        String p=sm.getUser();

        String date=sm.getOrderPref();


            if (!date.equals("empty")) {

                mbase
                        = FirebaseDatabase.getInstance().getReference().child("Payment").child(p);
                recyclerView = findViewById(R.id.recycler1);

                recyclerView.setLayoutManager(
                        new LinearLayoutManager(this));

                FirebaseRecyclerOptions<Payment> options
                        = new FirebaseRecyclerOptions.Builder<Payment>()
                        .setQuery(mbase.orderByChild("pdate").startAt(date), Payment.class)
                        .build();

                adapter = new OrderAdapter(options);
                recyclerView.setAdapter(adapter);
            } else {
                mbase
                        = FirebaseDatabase.getInstance().getReference().child("Payment").child(p);
                recyclerView = findViewById(R.id.recycler1);

                recyclerView.setLayoutManager(
                        new LinearLayoutManager(this));

                FirebaseRecyclerOptions<Payment> options
                        = new FirebaseRecyclerOptions.Builder<Payment>()
                        .setQuery(mbase, Payment.class)
                        .build();

                adapter = new OrderAdapter(options);

                recyclerView.setAdapter(adapter);

            }
        }


    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch(item.getItemId()){

            case R.id.nav_home:
                intent = new Intent(Orders.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(Orders.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(Orders.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(Orders.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(Orders.this,Messages.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}