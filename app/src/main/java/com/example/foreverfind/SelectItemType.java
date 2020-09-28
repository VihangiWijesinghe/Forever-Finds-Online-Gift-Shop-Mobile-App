package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SelectItemType extends AppCompatActivity {

    ImageView FlowersIc,CakeIc,ChocIc,ToyIc;
    private FloatingActionButton cartbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_select_item_type);

        FlowersIc = findViewById(R.id.flow_icon);
        CakeIc = findViewById(R.id.cake_icon);
        ChocIc = findViewById(R.id.choc_icon);
        ToyIc = findViewById(R.id.teddy_icon);
        cartbtn = findViewById(R.id.btn_showShop2);

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectItemType.this,shoppingCart.class);
                startActivity(intent);

            }
        });

        FlowersIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startFlowActivity();
            }
        });

        CakeIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCakeActivity();
            }
        });

        ChocIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChocActivity();
            }
        });

        ToyIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startToyActivity();
            }

        });
    }

    private void startToyActivity() {
        Intent intent = new Intent(SelectItemType.this,mainpage.class);
        intent.putExtra("type","toys");
        startActivity(intent);
    }

    private void startChocActivity() {
        Intent intent = new Intent(SelectItemType.this,mainpage.class);
        intent.putExtra("type","chocolates");
        startActivity(intent);
    }

    private void startCakeActivity() {
        Intent intent = new Intent(SelectItemType.this,mainpage.class);
        intent.putExtra("type","cakes");
        startActivity(intent);
    }

    private void startFlowActivity() {
        Intent intent = new Intent(SelectItemType.this,mainpage.class);
        intent.putExtra("type","flowers");
        startActivity(intent);
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
                intent = new Intent(SelectItemType.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(SelectItemType.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(SelectItemType.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(SelectItemType.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(SelectItemType.this,Messages.class);
                startActivity(intent);
                return true;

            case R.id.nav_items:
                intent = new Intent(SelectItemType.this,SelectItemType.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}