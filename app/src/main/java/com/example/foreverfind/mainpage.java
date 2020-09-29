package com.example.foreverfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.foreverfind.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class mainpage extends AppCompatActivity {

    //private DatabaseReference ProductRef;
    private RecyclerView recyclerView;

    private RecyclerView mrecyclerView;
    private FloatingActionButton showcartbtn;
    DatabaseReference productRef;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        productRef = FirebaseDatabase.getInstance().getReference().child("Products");


        recyclerView = findViewById(R.id.recyMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mrecyclerView = findViewById(R.id.recyMenu);
        mrecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(layoutManager);

        showcartbtn = findViewById(R.id.btn_showShop2);

        showcartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainpage.this,shoppingCart.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {

       /*super.onStart();
        adapter.startListening();
*/
        super.onStart();

        String productType = getIntent().getStringExtra("type");
        Query data = productRef.orderByChild("type").equalTo(productType);

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(data, Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);

                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final Products product) {

                        holder.pnamev.setText(product.getPname());
                        holder.pPricev.setText(product.getPrice());
                        holder.pdesv.setText(product.getDescription());
                        Picasso.get().load(product.getImage()).into(holder.iv);

                        holder.iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(mainpage.this,AddShop.class);
                                intent.putExtra("pid",product.getPid());
                                startActivity(intent);
                            }
                        });



                    }
                };

        mrecyclerView.setAdapter(adapter);
        adapter.startListening();

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
                intent = new Intent(mainpage.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(mainpage.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(mainpage.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(mainpage.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(mainpage.this,Messages.class);
                startActivity(intent);
                return true;

            case R.id.nav_items:
                intent = new Intent(mainpage.this,SelectItemType.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}