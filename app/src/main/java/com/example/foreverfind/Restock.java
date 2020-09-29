package com.example.foreverfind;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foreverfind.model.Items;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Restock extends AppCompatActivity {

    EditText itemquan, itemcode, itemprice;
    Button addbtn, show;
    DatabaseReference dbRef;
    Items it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Restock.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.items));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        itemquan = findViewById(R.id.quantity);
        itemcode = findViewById(R.id.code);
        itemprice = findViewById(R.id.price);
        addbtn = findViewById(R.id.addbtn);
        show = findViewById(R.id.show);

        it = new Items();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Items");

                try {

                    if (TextUtils.isEmpty(itemquan.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter quantity", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(itemcode.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter code", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(itemprice.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter item price", Toast.LENGTH_SHORT).show();

                    else {

                        it.setItemquan(itemquan.getText().toString().trim());
                        it.setItemcode(itemcode.getText().toString().trim());
                        it.setItemprice(itemprice.getText().toString().trim());

                        dbRef.push().setValue(it);
                        //dbRef.child("Std1").setValue(std);
                        Toast.makeText(getApplicationContext(), "Restock Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();

                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Items").child("Items");

                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChildren()) {

                            itemquan.setText(dataSnapshot.child("quantity").getValue().toString());
                            itemcode.setText(dataSnapshot.child("code").getValue().toString());
                            itemprice.setText(dataSnapshot.child("price").getValue().toString());

                        } else
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
});

    }

  private void clearControls() {

            itemquan.setText("");
            itemcode.setText("");
            itemprice.setText("");

        }
    }
