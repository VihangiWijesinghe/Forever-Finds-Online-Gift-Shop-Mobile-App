package com.example.foreverfind.sessions;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foreverfind.model.User;

public class SessionManagement {

    SharedPreferences sp;
    String Session_key= "session_user";
    String phone;

    SharedPreferences.Editor ed;

    String Shared_Pref_name = "session";

    public SessionManagement(Context context) {
        sp = context.getSharedPreferences(Shared_Pref_name ,Context.MODE_PRIVATE);
        ed = sp.edit();
    }

    public void saveSession(User user){
       phone = user.getPhone();
        ed.putString(Session_key,phone);
    }

    public int getSession(){
        return sp.getInt(Session_key,-1);
    }

    public String getUser(){
        return phone;
    }

}
