package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class orders02 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders02);
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
                intent = new Intent(orders02.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(orders02.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(orders02.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(orders02.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(orders02.this,Messages.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}