package com.example.foreverfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foreverfind.database.DBReference;
import com.example.foreverfind.model.User;
import com.example.foreverfind.sessions.SessionManagement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button createAc;
    Button login;
    ProgressDialog loadingBar ;

    private EditText phone;
    private EditText password;
    private String parentDBName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createAc =findViewById(R.id.btnSignup);
       login=findViewById(R.id.btnLog);
       phone = findViewById(R.id.etPhone);
       password = findViewById(R.id.etPassword);
        loadingBar = new ProgressDialog(this);
        createAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement sm = new SessionManagement(Login.this);
        int phone = sm.getSession();

        if(phone != -1){
            Intent intent= new Intent(Login.this,getStarted.class);
            startActivity(intent);
        }

    }

    private void LoginUser() {

        User ui = new User();

        ui.setPhone((phone.getText().toString()));
        ui.setPassword(password.getText().toString());


          if(TextUtils.isEmpty(ui.getPassword())){
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(ui.getPhone().toString())){
            Toast.makeText(this, "Please Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
        }

         else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please Wait, We Are Checking The Credentials");

            loadingBar.setCanceledOnTouchOutside(false);

            loadingBar.show();

            AllowAccessToAccount(ui.getPhone().toString(),ui.getPassword());
        }
    }

    private void AllowAccessToAccount(final String  phone, final String password) {

        DBReference dbref = new DBReference();

        dbref.getRootRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(parentDBName).child(phone).exists()){

                    User u = dataSnapshot.child(parentDBName).child(phone).getValue(User.class);


                        if(u.getPassword().equals(password)){
                            Toast.makeText(Login.this, "Logged In Successfully..", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            SessionManagement sm = new SessionManagement(Login.this);
                            sm.saveSession(u);

                            Intent intent= new Intent(Login.this,getStarted.class);
                            startActivity(intent);
                        }

                        else {
                            Toast.makeText(Login.this, "Your Password Is Incorrent ", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }



                else{
                    Toast.makeText(Login.this, "This " + phone +" Does Not Exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}