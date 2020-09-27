package com.example.foreverfindvihangi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.foreverfindvihangi.model.Products;
import com.example.foreverfindvihangi.sessions.SessionManagement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddShop extends AppCompatActivity {

    private Button addToCartBtn;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView proPrice,proDes,proName;
    private String productID = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

         productID = getIntent().getStringExtra("pid");

         if(productID == null){
             Toast.makeText(this, "pid is null", Toast.LENGTH_SHORT).show();
         }

         else{
             getProductDetails(productID);
         }

      addToCartBtn = (Button)findViewById(R.id.btn_addProduct_cart);
      productImage = (ImageView)findViewById(R.id.product_image_adds);
      numberButton = (ElegantNumberButton)findViewById(R.id.number_btn);
      proPrice = (TextView)findViewById(R.id.product_price_adds);
      proDes = (TextView)findViewById(R.id.product_des_adds);
      proName = (TextView)findViewById(R.id.product_name_adds);

      addToCartBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


              addingToCartList();


          }
      });



    }

    private void addingToCartList() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime= currentTime.format(calForDate.getTime());

        SessionManagement sm = new SessionManagement(AddShop.this);
        String phone = sm.getUser();


        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object> cartMap = new HashMap<>();
            cartMap.put("pid",productID);
            cartMap.put("pname",proName.getText().toString());

            cartMap.put("price",proPrice.getText().toString());
            cartMap.put("date",saveCurrentDate);
            cartMap.put("time",saveCurrentTime);
            cartMap.put("quantity",numberButton.getNumber());
            cartMap.put("pid",productID);

         cartListRef.child(phone).child("ProductItems").child(productID).updateChildren(cartMap)
         .addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {

                 if(task.isSuccessful()){
                     Toast.makeText(AddShop.this, "Added To Cart List..", Toast.LENGTH_SHORT).show();
                     Intent intent= new Intent(AddShop.this,shoppingCart.class);
                     startActivity(intent);
                 }
             }
         })
                ;

    }

    private void getProductDetails(String productID) {

        DatabaseReference productsRefer = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRefer.child(productID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);

                    proName.setText(products.getPname());
                    proDes.setText(products.getDescription());
                    proPrice.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch(item.getItemId()){

            case R.id.nav_home:
                intent = new Intent(AddShop.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(AddShop.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(AddShop.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(AddShop.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(AddShop.this,Messages.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}