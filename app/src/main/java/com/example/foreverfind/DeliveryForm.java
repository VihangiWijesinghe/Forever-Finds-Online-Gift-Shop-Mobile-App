package com.example.foreverfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foreverfind.database.DBReference;
import com.example.foreverfind.sessions.SessionManagement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeliveryForm extends AppCompatActivity {

    Button btnsubmitDeliveryDet;
    EditText resFullName,DelAddress,PhNumber;
    private int n = 0;
    private String orderno = "";
    private String tprice="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_form);

        String curUser;
        tprice+=getIntent().getStringExtra("Total price");

        SessionManagement sm = new SessionManagement(DeliveryForm.this);
        curUser = sm.getUser();

        final DBReference db = new DBReference();
        final   DatabaseReference  dbRef  = db.getRootRef().child("Order").child(curUser);
        Query query = dbRef.limitToLast(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                     n = Integer.parseInt(childSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btnsubmitDeliveryDet = findViewById(R.id.btn_confirmOrder);
        resFullName = findViewById(R.id.res_name);
        DelAddress = findViewById(R.id.delPhone);
        PhNumber = findViewById(R.id.resmobile);

        btnsubmitDeliveryDet.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                    insertDeilveryDetails();
            }
        });

    }

    private void insertDeilveryDetails() {



            if(TextUtils.isEmpty(resFullName.getText().toString())){
                Toast.makeText(DeliveryForm.this, " Enter Receiver Full Name..", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(DelAddress.getText().toString())){
                Toast.makeText(DeliveryForm.this, " Enter Delivery Address..", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(PhNumber.getText().toString())){
                Toast.makeText(DeliveryForm.this, " Enter Receiver Phone Number..", Toast.LENGTH_SHORT).show();
            }
            else {
                // String id = deliveryRef.push().getKey();
                DatabaseReference deliveryRef;
                String curUser;

                SessionManagement sm = new SessionManagement(DeliveryForm.this);
                curUser = sm.getUser();

                com.example.foreverfind.model.Order or1 = new com.example.foreverfind.model.Order();

                n++;
                orderno += n;
                deliveryRef = FirebaseDatabase.getInstance().getReference().child("Order").child(curUser);


                or1.setRecName(resFullName.getText().toString().trim());
                or1.setDaddress(DelAddress.getText().toString().trim());
                or1.setResPhone(PhNumber.getText().toString().trim());


                deliveryRef.child(orderno).setValue(or1);

                Toast.makeText(DeliveryForm.this, "Delivery Details Inserted Successfully", Toast.LENGTH_SHORT).show();
                clearControls();


                Intent intent = new Intent(DeliveryForm.this, summary.class);
                intent.putExtra("ttot",tprice);
                startActivity(intent);
            }
    }

    public void clearControls(){
        resFullName.setText("");
        DelAddress.setText("");
        PhNumber.setText("");
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
                intent = new Intent(DeliveryForm.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(DeliveryForm.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(DeliveryForm.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(DeliveryForm.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(DeliveryForm.this,Messages.class);
                startActivity(intent);
                return true;

            case R.id.nav_items:
                intent = new Intent(DeliveryForm.this,mainpage.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
