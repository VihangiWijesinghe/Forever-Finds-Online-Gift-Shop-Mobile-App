package com.example.foreverfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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


public class stockdetails extends AppCompatActivity {
    Button delete,update;
    EditText quantity, code, price, weight;
    DatabaseReference dbref;
    Items it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockdetails);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(stockdetails.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.items));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      mySpinner.setAdapter(myAdapter);

        quantity = findViewById(R.id.pdes);
        code = findViewById(R.id.pdes);
        price = findViewById(R.id.price);
        weight = findViewById(R.id.weight);

        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);

        it = new Items();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Items");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild("Std1")) {

                            try {
                                it.setItemquan(quantity.getText().toString().trim());
                                it.setItemprice(code.getText().toString().trim());
                                it.setItemcode(price.getText().toString().trim());
                                it.setItemcode(weight.getText().toString().trim());

                                dbref = FirebaseDatabase.getInstance().getReference().child("Items").child("Items");
                                dbref.setValue(it);
                               // clearControls();

                                Toast.makeText(getApplicationContext(), " Updated Successfully", Toast.LENGTH_SHORT).show();

                            } catch (NumberFormatException e) {

                                Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{

                            Toast.makeText(getApplicationContext(), "No source to Update", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }
    }
