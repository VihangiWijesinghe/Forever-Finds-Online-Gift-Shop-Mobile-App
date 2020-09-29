package com.example.foreverfind;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foreverfind.database.DBReference;
import com.example.foreverfind.model.User;
import com.example.foreverfind.sessions.SessionManagement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Fragmentpersonal extends Fragment {


    EditText etName,etEmail,etAddr;
    EditText etName2,etEmail2,etAddr2;
    ImageView img;
    String phone2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragmentpersonal,container,false);
        Button btnlgout = rootView.findViewById(R.id.btnLogOut);
        TextView tvChange;
        tvChange = rootView.findViewById(R.id.tvUpdate);

        etName2 = rootView.findViewById(R.id.etGcode);
        etEmail2 = rootView.findViewById(R.id.etEmailProf);
        etAddr2 = rootView.findViewById(R.id.etAddrss);

        etName2.setInputType(InputType.TYPE_NULL);
        etEmail2.setInputType(InputType.TYPE_NULL);
        etAddr2.setInputType(InputType.TYPE_NULL);


        showDetails();

        btnlgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManagement sm = new SessionManagement(getActivity());
                sm.removeSession();

                Toast.makeText(getActivity(), "Logging out...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = getActivity().getLayoutInflater();

                View view2 = inflater1.inflate(R.layout.layout_updatedpersonal,null);


                etName = view2.findViewById(R.id.etNameProfup);
                etEmail = view2.findViewById(R.id.etEmailUp);
                etAddr = view2.findViewById(R.id.etAddressUp);

                etName.setText(etName2.getText());
                etEmail.setText(etEmail2.getText());
                etAddr.setText(etAddr2.getText());


                img = view2.findViewById(R.id.pen);

                SessionManagement sm = new SessionManagement(getActivity());
                final String phone = sm.getUser();

             mydialog.setView(view2).setTitle("Update Details")
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
                        String expression = "^(.+)@(.+)$";
                        Pattern pattern = Pattern.compile(expression);
                        final Matcher matcher = pattern.matcher(etEmail.getText().toString());

                        if (etName.getText().toString().equals("") || etEmail.getText().toString().equals("")) {

                            Toast.makeText(mydialog.getContext(), "Please enter a Name and Email", Toast.LENGTH_SHORT).show();

                        } else if (matcher.matches() == false) {

                            Toast.makeText(mydialog.getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();

                        } else {

                            final DBReference db = new DBReference();
                            db.getRootRef().addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    User u = new User();
                                    u.setName(etName.getText().toString());
                                    u.setEmail(etEmail.getText().toString());
                                    u.setAddress(etAddr.getText().toString());

                                    if (dataSnapshot.child(db.getParentDbName()).hasChild(phone)) {
                                        db.getRootRef().child(db.getParentDbName()).child(phone).child("name").setValue(u.getName());
                                        db.getRootRef().child(db.getParentDbName()).child(phone).child("email").setValue(u.getEmail());
                                        db.getRootRef().child(db.getParentDbName()).child(phone).child("address").setValue(u.getAddress());
                                        Toast.makeText(getActivity(), "Details Updated Successfully", Toast.LENGTH_SHORT).show();
                                        showDetails();
                                        dialog.dismiss();


                                    }

                                    else{

                                        Toast.makeText(getContext(), "Details not updated", Toast.LENGTH_SHORT).show();

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });

            }
        });

        return rootView;
    }

    private void showDetails(){

        SessionManagement sm = new SessionManagement(getContext());
        phone2 = sm.getUser();

        final DBReference db = new DBReference();
        db.getRootRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone2).hasChildren()){
                    etName2.setText(dataSnapshot.child(db.getParentDbName()).child(phone2).child("name").getValue().toString());
                    etEmail2.setText(dataSnapshot.child(db.getParentDbName()).child(phone2).child("email").getValue().toString());
                    etAddr2.setText(dataSnapshot.child(db.getParentDbName()).child(phone2).child("address").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}