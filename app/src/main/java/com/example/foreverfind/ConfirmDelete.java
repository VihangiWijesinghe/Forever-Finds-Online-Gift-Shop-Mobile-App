package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ConfirmDelete extends AppCompatActivity{

    Button btn;
    Button btn2;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_delete);


        btn = (Button)findViewById(R.id.btncondel);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();
            }
        });

        btn2 = (Button)findViewById(R.id.btncanceldel);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ConfirmDelete.this,ManageProfile.class);
                startActivity(intent);
            }
        });

    }


    public void openDialog(){

        DeleteDialog confdel = new DeleteDialog();
        confdel.show(getSupportFragmentManager(),"deleted");
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
                intent = new Intent(ConfirmDelete.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(ConfirmDelete.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(ConfirmDelete.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(ConfirmDelete.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(ConfirmDelete.this,Messages.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}