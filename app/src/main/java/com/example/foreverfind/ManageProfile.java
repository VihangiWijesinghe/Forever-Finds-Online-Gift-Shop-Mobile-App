package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ManageProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        Button btnfrg = findViewById(R.id.btnClearh);
        Button btnfrg2 = findViewById(R.id.btnDeleteA);

        btnfrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();
            }
        });

        btnfrg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ManageProfile.this, ConfirmDelete.class);
                startActivity(intent);
            }
        });

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
                intent = new Intent(ManageProfile.this,getStarted.class);
                return true;

            case R.id.nav_profile:
                intent = new Intent(ManageProfile.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(ManageProfile.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(ManageProfile.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(ManageProfile.this,Messages.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void openDialog(){

        DialogExamples example = new DialogExamples();
        example.show(getSupportFragmentManager(),"example");

    }
}