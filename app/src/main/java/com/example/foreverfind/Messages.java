package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.foreverfind.model.Promotion;
import com.example.foreverfind.sessions.SessionManagement;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Messages extends AppCompatActivity {

    private RecyclerView recyclerView;
    MessagesAdapter
            adapter; // Create Object of the Adapter class
    DatabaseReference mbase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        String now = sd.format(Calendar.getInstance().getTime());

        SessionManagement sm = new SessionManagement(Messages.this);
        String sw = sm.getSwitch();

        if(sw.equals("switch_on")) {

            mbase
                    = FirebaseDatabase.getInstance().getReference().child("Promotions").child("discounts");

            recyclerView = findViewById(R.id.recycler2);

            recyclerView.setLayoutManager(
                    new LinearLayoutManager(this));

            FirebaseRecyclerOptions<Promotion> options
                    = new FirebaseRecyclerOptions.Builder<Promotion>()
                    .setQuery(mbase.orderByChild("expdate").startAt(now), Promotion.class)
                    .build();


            adapter = new MessagesAdapter(options);

            recyclerView.setAdapter(adapter);
        }

     else{
            Toast.makeText(this, "No promos. Enable send promotions to display", Toast.LENGTH_LONG).show();
     }
    }

    @Override protected void onStart()
    {
        super.onStart();


        SessionManagement sm = new SessionManagement(Messages.this);
        String sw = sm.getSwitch();

        if(sw.equals("switch_on")) {
            adapter.startListening();
        }
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();

        SessionManagement sm = new SessionManagement(Messages.this);
        String sw = sm.getSwitch();

        if(sw.equals("switch_on")) {
            adapter.stopListening();
        }
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
                intent = new Intent(Messages.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(Messages.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(Messages.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(Messages.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(Messages.this,Messages.class);
                startActivity(intent);
                return true;

            case R.id.nav_items:
                intent = new Intent(Messages.this,SelectItemType.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}