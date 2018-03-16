package com.Alatheer.marmy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Elashry on 2/16/2018.
 */

public class Preferense {

    Context context;

    public Preferense(Context context) {
        this.context = context;
    }

    public void CreateSharedPref(String id , String del) {
        SharedPreferences pref = context.getSharedPreferences("id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_id", id);
        editor.putString("isdelegate", del);

        editor.apply();

    }

    public void clear() {
        SharedPreferences pref = context.getSharedPreferences("id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}
