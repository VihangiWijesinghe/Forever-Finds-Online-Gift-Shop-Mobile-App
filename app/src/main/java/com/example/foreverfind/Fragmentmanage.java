package com.example.foreverfind;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.UserHandle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.foreverfind.database.DBReference;
import com.example.foreverfind.model.User;
import com.example.foreverfind.sessions.SessionManagement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Fragmentmanage extends Fragment {

    private String myText;
    private String phone;
    private EditText etP;
    private EditText etPass;
    private EditText etUpCurPass,etUpNewPass,etUpConNew;
    Switch sw;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_fragmentmanage,container,false);

        etP = rootView.findViewById(R.id.editTextPhone);
        etPass = rootView.findViewById(R.id.etPassword); //edittext Phone
        etP.setInputType(InputType.TYPE_NULL);
        etPass.setInputType(InputType.TYPE_NULL);   //edittext Password
        etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        Button btnfrg = rootView.findViewById(R.id.btnClearh);
        Button btnfrg2 = rootView.findViewById(R.id.btnDeleteA);

        //ImageView imgpen = rootView.findViewById(R.id.pen); //edit phone
        ImageView imgpen2 = rootView.findViewById(R.id.pen2);   //edit password

        showPhonePass();


        imgpen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
               LayoutInflater inflater1 = getActivity().getLayoutInflater();
               View view2 = inflater1.inflate(R.layout.activity_change_password,null);


                etUpCurPass = view2.findViewById(R.id.etCurPass);
                etUpNewPass = view2.findViewById(R.id.etNewPass);
                etUpConNew = view2.findViewById(R.id.etConNewPass);

               mydialog.setView(view2).setTitle("Update Password")
                       .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               }).setPositiveButton("Confirm", null);

                final AlertDialog dialog = mydialog.create();

                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!etUpCurPass.getText().toString().equals("") || !etUpNewPass.getText().toString().equals("") || !etUpConNew.getText().toString().equals("")) {
                            if (!etUpCurPass.getText().toString().equals(etPass.getText().toString())) {
                                Toast.makeText(getActivity(), "Current password you entered is incorrect", Toast.LENGTH_SHORT).show();

                            } else if (!etUpNewPass.getText().toString().equals(etUpConNew.getText().toString())) {
                                Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();

                            } else if (etUpNewPass.getText().toString().length() < 8) {
                                Toast.makeText(getActivity(), "Please enter a password of at least 8 characters", Toast.LENGTH_SHORT).show();
                            } else {

                                final DBReference db = new DBReference();
                                db.getRootRef().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        User u = new User();
                                        u.setPassword(etUpNewPass.getText().toString());
                                        if (dataSnapshot.child(db.getParentDbName()).hasChild(etP.getText().toString())) {
                                            db.getRootRef().child(db.getParentDbName()).child(etP.getText().toString()).child("password").setValue(u.getPassword());

                                            Toast.makeText(getActivity(), "Password updated", Toast.LENGTH_SHORT).show();
                                            showPhonePass();
                                            dialog.dismiss();

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        btnfrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();

            }
        });

        btnfrg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ConfirmDelete.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment

        sw=rootView.findViewById(R.id.switchnotify);

        SessionManagement ss = new SessionManagement(getContext());

        String sw2 = ss.getSwitch();

        if(sw2.equals("switch_off"))
            sw.setChecked(false);

        else
            sw.setChecked(true);

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw.isChecked()==true){

                    SessionManagement sm = new SessionManagement(getContext());
                    sm.save("switch_on");

                }

                else{

                    SessionManagement sm = new SessionManagement(getContext());
                    sm.save("switch_off");
                }

            }
        });

        return rootView;
    }

    public void openDialog(){

        DialogExamples example = new DialogExamples();
        example.show(getFragmentManager(),"example");
    }

    public void showPhonePass(){

        SessionManagement sm = new SessionManagement(getContext());
        String phone2 = sm.getSession();
        phone = sm.getUser();
        final DBReference db = new DBReference();
        db.getRootRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).hasChildren()){
                    etP.setText(dataSnapshot.child(db.getParentDbName()).child(phone).child("phone number").getValue().toString());

                    etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPass.setText(dataSnapshot.child(db.getParentDbName()).child(phone).child("password").getValue().toString());
                    etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}