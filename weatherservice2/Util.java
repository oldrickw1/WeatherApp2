package com.example.weatherservice2;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Util {
    public static void log(String message) {
        Log.i("OLLIE", message);
    }
    static public void toast(Context c, String m) {
        Toast.makeText(c, m, Toast.LENGTH_SHORT).show();
    }
}
