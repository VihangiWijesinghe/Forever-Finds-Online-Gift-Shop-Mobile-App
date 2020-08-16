package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddShop extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        button = (Button) findViewById(R.id.btn_addcart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShopcartActivity();
            }

        });

        button = (Button) findViewById(R.id.btn_backItem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openItems();
            }

        });
    }

    public void openShopcartActivity() {
        Intent intent = new Intent(this, shoppingCart.class);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Item is added to the cart!" , Toast.LENGTH_SHORT ).show();
    }

    public void openItems() {
        Intent intent = new Intent(this, ItemActivity.class);
        startActivity(intent);
    }
}