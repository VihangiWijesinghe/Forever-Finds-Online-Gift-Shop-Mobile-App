package com.example.foreverfindvihangi.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBReference {

    final DatabaseReference RootRef;


    public DBReference() {
        RootRef = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getRootRef() {
        return RootRef;
    }
}
