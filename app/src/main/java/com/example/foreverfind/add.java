package com.example.foreverfind;

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
import com.example.foreverfind.model.Products;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class add extends AppCompatActivity {
    Button btnaddnew;
    EditText description, code, price, weight;
    DatabaseReference databaseStock;
    Products itm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);

        final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(add.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.items));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


        btnaddnew = findViewById(R.id.btnaddnew);
        description = findViewById(R.id.pdes);
        code = findViewById(R.id.code);
        price = findViewById(R.id.price);
        weight = findViewById(R.id.weight);

        itm = new Products();


        btnaddnew.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {

                                             databaseStock = FirebaseDatabase.getInstance().getReference().child("Products");
                                             try {
                                                 if (TextUtils.isEmpty(description.getText().toString()))
                                                     Toast.makeText(getApplicationContext(), "Please enter description", Toast.LENGTH_SHORT).show();

                                                 else if (TextUtils.isEmpty(code.getText().toString()))
                                                     Toast.makeText(getApplicationContext(), "Please enter the code", Toast.LENGTH_SHORT).show();

                                                 else if (TextUtils.isEmpty(price.getText().toString()))
                                                     Toast.makeText(getApplicationContext(), "Please enter price", Toast.LENGTH_SHORT).show();

                                                 else if (TextUtils.isEmpty(weight.getText().toString()))
                                                     Toast.makeText(getApplicationContext(), "Please enter weight", Toast.LENGTH_SHORT).show();
                                                 else {

                                                     itm.setDescription(description.getText().toString().trim());
                                                     itm.setPid(code.getText().toString().trim());
                                                     itm.setPrice(price.getText().toString().trim());
                                                     itm.setWeight(weight.getText().toString().trim());
                                                    itm.setType( mySpinner.getSelectedItem().toString());


                                                     databaseStock.child(itm.getPid()).setValue(itm);
                                                     //dbRef.child("Std1").setValue(std);
                                                     Toast.makeText(getApplicationContext(), "items added Successfully", Toast.LENGTH_SHORT).show();
                                                     clearControls();
                                                 }
                                             } catch (NumberFormatException e) {
                                                 Toast.makeText(getApplicationContext(), "Invalid ", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     });
        /*btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");

                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChildren()) {

                            txtID.setText(dataSnapshot.child("id").getValue().toString());
                            txtName.setText(dataSnapshot.child("name").getValue().toString());
                            txtAdd.setText(dataSnapshot.child("address").getValue().toString());
                            txtConNo.setText(dataSnapshot.child("conNo").getValue().toString());

                        } else
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }*/


    }

    private void clearControls() {

        description.setText("");
        code.setText("");
        price.setText("");
        weight.setText("");


    }
}





