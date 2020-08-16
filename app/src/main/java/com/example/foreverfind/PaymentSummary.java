package com.example.foreverfind;


import androidx.appcompat.app.AppCompatActivity;

import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class PaymentSummary extends AppCompatActivity{

    Button btn;
    Button btn2;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_summary);

        btn = (Button)findViewById(R.id.btnGpay);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PaymentSummary.this, PaymentMethod.class);
                startActivity(intent);
            }
        });

        btn2 = (Button)findViewById(R.id.btnGetCode);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PaymentSummary.this, PaymentSummary.class);
                startActivity(intent);

                Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
                CharSequence message = "Redeeming Code....";//Display string
                int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

            }
        });


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
                intent = new Intent(PaymentSummary.this, HomeActivity.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(PaymentSummary.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(PaymentSummary.this,Shoppingcart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(PaymentSummary.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(PaymentSummary.this,Messages.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}