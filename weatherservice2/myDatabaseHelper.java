package com.example.weatherservice2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.utils.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class myDatabaseHelper extends SQLiteOpenHelper {
    public myDatabaseHelper(@Nullable Context context) {
        super(context, "cities.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Util.log("DB NOT FOUND, A NEW ONE WAS CREATED INSTEAD");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void copyDatabaseFromAssets(Context context) {
        try {
            File dbFile = context.getDatabasePath("cities.db");

            // Check if the database already exists
            if (dbFile.exists()) {
                Util.log("Database already exists");
                return;
            }

            // Create the database directory if it doesn't exist
            File dbDir = new File(dbFile.getParent());
            if (!dbDir.exists()) {
                dbDir.mkdirs();
            }

            // Copy the database from assets to the app's data directory
            InputStream inputStream = context.getAssets().open("cities.db");
            OutputStream outputStream = new FileOutputStream(dbFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            Util.log("Database copied from assets to data directory");
        } catch (IOException e) {
            Util.log("Error copying database: " + e.getMessage());
        }
    }
}

