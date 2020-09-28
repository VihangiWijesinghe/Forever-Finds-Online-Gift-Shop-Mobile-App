package com.example.foreverfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foreverfind.model.ShoppingCart;
import com.example.foreverfind.sessions.SessionManagement;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class shoppingCart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessbtn;
    private TextView txt_totalPrice;

    private int overallPrice = 0;


    private int onep;
    private int quan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView = findViewById(R.id.cartList);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        NextProcessbtn = findViewById(R.id.next_process_btn);
        txt_totalPrice = findViewById(R.id.total_price);


        overallPrice=0;
        NextProcessbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_totalPrice.setText("Total Price : " + String.valueOf(overallPrice) + "Rs");
                Intent intent= new Intent(shoppingCart.this,DeliveryForm.class); //savani
                intent.putExtra("Total price",String.valueOf(overallPrice));
                startActivity(intent);
                finish();
            }
        });



        }

    @Override
    protected void onStart() {

        super.onStart();

        SessionManagement sm = new SessionManagement(shoppingCart.this);
        final String phone = sm.getUser();

        final DatabaseReference cartlistRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<ShoppingCart> options = new FirebaseRecyclerOptions.Builder<ShoppingCart>()
                .setQuery(cartlistRef.child(phone)
                        .child("ProductItems"),ShoppingCart.class).build();

        FirebaseRecyclerAdapter<ShoppingCart,CartViewHolder> adapter
                =new FirebaseRecyclerAdapter<ShoppingCart, CartViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final ShoppingCart shoppingCart) {
                cartViewHolder.txtProName.setText(shoppingCart.getPname());
                cartViewHolder.txtProPrice.setText("Price :  "+shoppingCart.getPrice()+ " Rs");
                cartViewHolder.txtProQuantity.setText("Quantity :"+shoppingCart.getQuantity() );




                onep = (Integer.valueOf(shoppingCart.getPrice()));
                quan = Integer.valueOf(shoppingCart.getQuantity());

               int onetprice =  calOnePrice(onep,quan);
                calOverallTotalPrice(onetprice);
              //  int TotOne =((Integer.valueOf(shoppingCart.getPrice() ))) * Integer.valueOf(shoppingCart.getQuantity());




                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{

                                "Edit",
                                "Remove"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(shoppingCart.this);
                        builder.setTitle("Cart Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    if(i==0){
                                        Intent intent= new Intent(shoppingCart.this,AddShop.class);
                                        intent.putExtra("pid",shoppingCart.getPid());
                                        startActivity(intent);
                                    }

                                    if(i==1){

                                        /*int remtot=Integer.parseInt(cartlistRef.child(phone).child("ProductItems").child(shoppingCart.getPrice()).toString());
                                        int remqty=Integer.parseInt(cartlistRef.child(phone).child("ProductItems").child(shoppingCart.getQuantity()).toString());

                                            int remtotal = remtot * remqty;//method
                                            overallPrice -= remtotal;*/


                                        int price = Integer.parseInt(shoppingCart.getPrice());
                                        int qty = Integer.parseInt(shoppingCart.getQuantity());

                                        int sum = price * qty;
                                        overallPrice-=sum;

                                        cartlistRef.child(phone).child("ProductItems").child(shoppingCart.getPid())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful()){
                                                            Toast.makeText(shoppingCart.this, "Item Removed Successfully", Toast.LENGTH_SHORT).show();

                                                            //Intent intent= new Intent(shoppingCart.this,mainpage.class);
                                                           // startActivity(intent);
                                                        }
                                                    }
                                                });
                                    }
                            }
                        });

                        builder.show();
                    }
                });


            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public int calOnePrice(int iprice,int quantity){
        int oneTypePrice = iprice*quantity;
        return oneTypePrice;
    }

    public int calOverallTotalPrice(int oneTyPrice) {
        overallPrice = overallPrice + oneTyPrice;


        return overallPrice;
    }



    public void openItemActivity(){
        Intent intent= new Intent(this,ItemActivity.class);
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
                intent = new Intent(shoppingCart.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(shoppingCart.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(shoppingCart.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(shoppingCart.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(shoppingCart.this,Messages.class);
                startActivity(intent);
                return true;

            case R.id.nav_items:
                intent = new Intent(shoppingCart.this,SelectItemType.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}