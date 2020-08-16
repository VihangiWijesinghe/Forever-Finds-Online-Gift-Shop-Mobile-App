package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class shoppingCart extends AppCompatActivity {
    Button button,button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        button= (Button) findViewById(R.id.btn_cont_shopping);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openItemActivity();
            }
        });

        button1=(Button)findViewById(R.id.btn_placeOr_shopc);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shoppingCart.this,DeliveryForm.class);
                startActivity(intent);
            }
        });


        }



    public void openItemActivity(){
        Intent intent= new Intent(this,ItemActivity.class);
        startActivity(intent);
    }
}