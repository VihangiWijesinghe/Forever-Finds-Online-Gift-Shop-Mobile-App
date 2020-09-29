package com.example.foreverfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foreverfind.database.DBReference;
import com.example.foreverfind.model.Payment;
import com.example.foreverfind.sessions.SessionManagement;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentConfirm extends AppCompatActivity{

    Button button2;
    ProgressDialog loadingBar;
    EditText etcard,etcv,ethname,etmonth,ety;
    ImageView img;
    TextView tvtotal;
    int keyDel;
    String a;
    int n;

    String odnum="";
    String key="";
    String amount;
    String ctype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm);

        button2=findViewById(R.id.btncon);
        img = findViewById(R.id.imgvInfo);
        etcard=findViewById(R.id.etCard);
        etcv=findViewById(R.id.etcvv);
        ethname = findViewById(R.id.etHName);
        etmonth=findViewById(R.id.etMonth);
        ety = findViewById(R.id.etYY);
        tvtotal = findViewById(R.id.tvTotalPay);

        key="";

        loadingBar = new ProgressDialog(this);

        amount=getIntent().getStringExtra("iamount");
        ctype=getIntent().getStringExtra("card");
        tvtotal.setText("Rs. "+amount);

        SessionManagement sm = new SessionManagement(PaymentConfirm.this);
        String curUser=sm.getUser();
        final DBReference db = new DBReference();
        final DatabaseReference dbref = db.getRootRef().child("Payment").child(curUser);
        Query query = dbref.limitToLast(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    n=Integer.parseInt(childSnapshot.getKey());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference dbref2 = db.getRootRef().child("Order").child(curUser);
        Query query2 = dbref.limitToLast(1);

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    odnum+=childSnapshot.getKey();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar mySnackbar = Snackbar.make(view, "CVV is the 3 digit number behind your card", 5000);
                mySnackbar.show();

            }
        });

        etcv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(etcv.getText().toString().length()==3){

                    etmonth.requestFocus();

                  }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingBar.setTitle("Payment Confirmation");
                loadingBar.setMessage("Please wait while we are checking the details...");

                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                validateForm();

            }
        });


        etcard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                boolean flag = true;
                String eachBlock[] = etcard.getText().toString().split(" ");
                for (int j = 0; j < eachBlock.length; j++) {
                    if (eachBlock[j].length() > 4) {
                        flag = false;
                    }
                }
                if (flag) {

                    etcard.setOnKeyListener(new View.OnKeyListener() {

                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                keyDel = 1;
                            return false;
                        }
                    });

                    if (keyDel == 0) {

                        if (((etcard.getText().length() + 1) % 5) == 0) {

                            if (etcard.getText().toString().split(" ").length <= 3) {
                                etcard.setText(etcard.getText() + " ");
                                etcard.setSelection(etcard.getText().length());
                            }
                        }
                        a = etcard.getText().toString();
                    } else {
                        a = etcard.getText().toString();
                        keyDel = 0;
                    }

                } else {
                    etcard.setText(a);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etmonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(etmonth.length()==2){

                    if (Integer.parseInt(etmonth.getText().toString()) > 12) {

                        Toast.makeText(PaymentConfirm.this, "Invalid month", Toast.LENGTH_SHORT).show();

                    }

                    else
                        ety.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ety.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(ety.getText().length()==4){

                    String year = "";
                    year += ety.getText().toString().trim();
                    SimpleDateFormat sd3 = new SimpleDateFormat("yyyy");
                    String cdate = sd3.format(Calendar.getInstance().getTime());

                    Date d1 = null;
                    Date d2 = null;
                    try {
                        d1 = sd3.parse(cdate);
                        d2 = sd3.parse(year);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                    if(d1.compareTo(d2) > 0){

                        Toast.makeText(PaymentConfirm.this, "Invalid year!", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                intent = new Intent(PaymentConfirm.this,getStarted.class);
                startActivity(intent);
                return true;

            case R.id.nav_profile:
                intent = new Intent(PaymentConfirm.this,PersonalProfile.class);
                startActivity(intent);
                return true;

            case R.id.nav_shopcart:
                intent = new Intent(PaymentConfirm.this,shoppingCart.class);
                startActivity(intent);
                return true;

            case R.id.nav_orders:
                intent = new Intent(PaymentConfirm.this,Orders.class);
                startActivity(intent);
                return true;

            case R.id.nav_message:
                intent = new Intent(PaymentConfirm.this,Messages.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void validateForm(){

        String monthday = "";
        monthday += etmonth.getText().toString().trim() + "/" + ety.getText().toString().trim();
        SimpleDateFormat sd3 = new SimpleDateFormat("MM/yyyy");
        String cdate = sd3.format(Calendar.getInstance().getTime());

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sd3.parse(cdate);
            d2 = sd3.parse(monthday);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
            if (TextUtils.isEmpty(ethname.getText().toString())) {

                Toast.makeText(PaymentConfirm.this, "Please enter your name in card", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            } else if (TextUtils.isEmpty(etcard.getText().toString())) {
                Toast.makeText(PaymentConfirm.this, "Please enter the card number", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            } else if (TextUtils.isEmpty(etcv.getText().toString())) {

                Toast.makeText(PaymentConfirm.this, "Please enter the cvv number", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            } else if (TextUtils.isEmpty(etmonth.getText().toString())) {

                Toast.makeText(PaymentConfirm.this, "Please enter the expiry month", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            } else if (TextUtils.isEmpty(ety.getText().toString())) {

                Toast.makeText(PaymentConfirm.this, "Please enter the expiry year", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            } else if (etcard.length() < 19) {

                Toast.makeText(PaymentConfirm.this, "Invalid card number", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
            else if(d1.compareTo(d2) > 0){

                Toast.makeText(PaymentConfirm.this, "Invalid expiry details", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
            else {

                SessionManagement sm = new SessionManagement(PaymentConfirm.this);
                String curUser=sm.getUser();
                DBReference dbb = new DBReference();
                final DatabaseReference dbref2=dbb.getRootRef().child("Payment").child(curUser);

                Payment p = new Payment();
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sd2 = new SimpleDateFormat("HH:MM:ss");

                String nowdate = sd.format(Calendar.getInstance().getTime());
                String nowt = sd2.format(Calendar.getInstance().getTime());

                p.setCtype(ctype);
                p.setAmount(amount);
                p.setPdate(nowdate);
                p.setPtime(nowt);
                p.setOrdNo(odnum);
                n++;
                String m="";
                m+=n;

                dbref2.child(m).setValue(p);

                Toast.makeText(this, "Payment Success! ", Toast.LENGTH_SHORT).show();
                setNotification();
                Intent intent = new Intent(PaymentConfirm.this, OrderConfirm.class);
                startActivity(intent);

            }

    }


    private void setNotification(){

        String CHANNEL_ID = "1888";

        Intent intent = new Intent(PaymentConfirm.this, Orders.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(PaymentConfirm.this, 0, intent, 0);
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(PaymentConfirm.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.gift_ff)
                .setContentTitle("Order Successful. Delivery on process")
                .setContentText("Order No: "+odnum)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager
                = NotificationManagerCompat.from(PaymentConfirm.this);

        notificationManager.notify(0, builder.build());

    }
}