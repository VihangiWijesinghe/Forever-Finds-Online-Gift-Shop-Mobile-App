package com.example.foreverfind.sessions;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {

    SharedPreferences sp;
    String Session_key= "session_user";
    String Switch_key= "switch";
    String phone;

    SharedPreferences.Editor ed;

    String Shared_Pref_name = "session";

    public SessionManagement(Context context) {
        sp = context.getSharedPreferences(Shared_Pref_name ,Context.MODE_PRIVATE);
        ed = sp.edit();
        phone+="";
    }

    public void saveSession(String user){

        ed.putString(Session_key,user).commit();
    }

    public void save(String s){

        ed.putString(Switch_key,s).commit();
    }

    public String getSwitch(){

        return sp.getString(Switch_key,"switch_on");
    }

    public String getSession(){
        return sp.getString(Session_key,"empty");
    }

    public String getUser(){
        return sp.getString(Session_key,"empty");
    }

    public void removeSession(){
        ed.putString(Session_key,"empty").commit();

    }
}
