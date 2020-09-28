package com.example.foreverfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foreverfind.database.DBReference;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class PaymentSummary extends AppCompatActivity{

    TextView tvTot,tvDis,tvFTot;
    Button btn;
    Button btn2;
    EditText etCode;
    String tot;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_summary);

        tvTot=findViewById(R.id.tvTot);
        tvDis=findViewById(R.id.tvDis);
        tvFTot=findViewById(R.id.tvTotalSum);
        etCode=findViewById(R.id.etGcode);

        tot=getIntent().getStringExtra("subtot");

        tvTot.setText(tot);
        tvDis.setText("0");
        tvFTot.setText(tot);

        btn = findViewById(R.id.btnGpay);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PaymentSummary.this, PaymentMethod.class);
                intent.putExtra("ftot",tvFTot.getText().toString());
                startActivity(intent);
            }
        });

        btn2 = (Button)findViewById(R.id.btnGetCode);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
                CharSequence message = "Redeeming Code....";//Display string
                int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

                final DBReference db = new DBReference();
                db.getRootRef().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child("Promotions").child("discounts").child(etCode.getText().toString()).exists()){
                            tvDis.setText(dataSnapshot.child("Promotions").child("discounts").child(etCode.getText().toString()).child("value").getValue().toString());
                            float maxtot = Float.parseFloat(dataSnapshot.child("Promotions").child("discounts").child(etCode.getText().toString()).child("maxtot").getValue().toString());
                            double total = calculateDisTotal(Double.parseDouble(tot),maxtot,Double.parseDouble(tvDis.getText().toString()));

                            if(total == 0 ){

                                Toast toast = Toast.makeText(PaymentSummary.this,"Sorry, this code is only valid for orders above "+maxtot, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }

                            else {
                                String t = "";
                                t += total;
                                tvFTot.setText(t);

                                Toast.makeText(PaymentSummary.this, "Code Redeemed Successfully", Toast.LENGTH_SHORT).show();
                            }

                        }

                        else{

                            Toast.makeText(PaymentSummary.this, "Sorry this code is invalid!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }

    public double calculateDisTotal(double tamount,double maxamount,double discount){


        if(tamount>=maxamount || maxamount == 0) {
            double total = tamount - discount;

            return total;
        }

        else{
            return 0;
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
                intent = new Intent(PaymentSummary.this, getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(PaymentSummary.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(PaymentSummary.this,shoppingCart.class);
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