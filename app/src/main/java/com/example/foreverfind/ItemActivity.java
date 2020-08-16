package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

public class ItemActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        button= (Button) findViewById(R.id.btn_Item_01);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openAddShopActivity();
            }
        });
    }

    public void openAddShopActivity(){
        Intent intent= new Intent(this,AddShop.class);
        startActivity(intent);
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item01:
                startActivity(new Intent(this,ItemActivity.class));
                return true;

            case R.id.item02:
                startActivity(new Intent(this,CakeActivity.class));
                return true;

            case R.id.item03:
                startActivity(new Intent(this,ChocolateActivity.class));
                return true;

            case R.id.item04:
                startActivity(new Intent(this,ToysActivity.class));
                return true;


            default:
                return false;

        }
    }

}