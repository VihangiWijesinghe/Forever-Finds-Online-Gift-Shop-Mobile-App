package com.example.foreverfindvihangi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class add extends AppCompatActivity {
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        button=findViewById(R.id.btnaddnew);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast =Toast.makeText(getApplicationContext(),"added new item",Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

}