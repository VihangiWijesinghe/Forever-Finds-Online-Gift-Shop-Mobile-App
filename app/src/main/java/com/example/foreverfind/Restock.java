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
import com.example.foreverfind.model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Restock extends AppCompatActivity {

    EditText itemquan, itemcode, itemprice;
    Button addbtn, show;
    DatabaseReference dbRef;
    Products it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);



        itemquan = findViewById(R.id.pquantity);
        itemcode = findViewById(R.id.pcode);

        addbtn = findViewById(R.id.addbtn);


        it = new Products();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Products");

                try {

                    if (TextUtils.isEmpty(itemquan.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter quantity", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(itemcode.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter product ID", Toast.LENGTH_SHORT).show();


                    else {

                        it.setQuant(itemquan.getText().toString().trim());
                        it.setPid(itemcode.getText().toString().trim());
                        dbRef.child(it.getPid()).child(" quant").setValue(it.getQuant());
                        //dbRef.child("Std1").setValue(std);
                        Toast.makeText(getApplicationContext(), "Restock Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();

                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid ", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

  private void clearControls() {

            itemquan.setText("");
            itemcode.setText("");


        }
    }
