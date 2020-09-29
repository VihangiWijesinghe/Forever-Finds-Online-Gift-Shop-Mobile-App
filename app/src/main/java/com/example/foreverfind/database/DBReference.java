package com.example.foreverfind.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBReference {

    final DatabaseReference RootRef;
    final String parentDbName = "Users";


    public DBReference() {
        RootRef = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getRootRef() {
        return RootRef;
    }

    public String getParentDbName() {
        return parentDbName;
    }
}
