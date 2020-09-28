package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foreverfind.sessions.SessionManagement;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class summary extends AppCompatActivity {

    Button btn,btncancel;
    TextView tvtotal,tvdel,tvsub;
    private String tprice="";
    private String newt ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvtotal = findViewById(R.id.tvIPrice);

        tvdel = findViewById(R.id.delcharge);
        tvsub = findViewById(R.id.subTotal_summry);

        btn = (Button)findViewById(R.id.btn_confirmOrder);

        btncancel=findViewById(R.id.btn_editOrder);

        tprice+=getIntent().getStringExtra("ttot");
        tvtotal.setText(tprice);

        double total = Double.parseDouble(tprice);
        double delivery = 350.0;

        String delcharge="";
        delcharge+=delivery;
        tvdel.setText(delcharge);

        SessionManagement sm = new SessionManagement(summary.this);
        final String phone = sm.getUser();


        double newtot = calDelPrice(total,delivery);

        newt+=newtot;

        tvsub.setText(newt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(summary.this,PaymentSummary.class);
                intent.putExtra("subtot",newt);
                startActivity(intent);
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final DatabaseReference cartlistRef2 = FirebaseDatabase.getInstance().getReference().child("Cart List").child(phone).child("ProductItems");
                cartlistRef2.removeValue();

                Toast.makeText(summary.this, "Order Successfully Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(summary.this,SelectItemType.class);
                startActivity(intent);
            }
        });
    }

    private double calDelPrice(double tot,double del){

        double calDelprice = tot+del;

        return calDelprice;
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
                intent = new Intent(summary.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(summary.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(summary.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(summary.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(summary.this,Messages.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}