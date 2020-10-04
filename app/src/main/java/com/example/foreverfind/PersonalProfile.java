package com.example.foreverfind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.foreverfind.sessions.SessionManagement;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class PersonalProfile extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabItem1,tabItem2;
    public PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabItem1=(TabItem)findViewById(R.id.tbpers);
        tabItem2=(TabItem)findViewById(R.id.tbmang);

        viewPager = findViewById(R.id.viewpager);
        pagerAdapter=new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){

                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition()==1){
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    protected void onStart() {
        super.onStart();


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
                intent = new Intent(PersonalProfile.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(PersonalProfile.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(PersonalProfile.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(PersonalProfile.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(PersonalProfile.this,Messages.class);
                startActivity(intent);
                return true;

            case R.id.nav_items:
                intent = new Intent(PersonalProfile.this,SelectItemType.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


}