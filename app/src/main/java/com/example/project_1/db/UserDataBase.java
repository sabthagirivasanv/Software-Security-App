package com.example.project_1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDataBase extends SQLiteOpenHelper {
    public static final String DB_NAME = "user_data";
    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONE = "phone";

    public static final String CREATE_TABLE_SCRIPT = "create table "+TABLE+ " ("+ID+" integer primary key autoincrement, "+
            NAME + " text, "+PHONE+" text);";

    public static final String[] ALL_COLUMNS = {ID, NAME, PHONE};

    public UserDataBase(Context context){
        super(context,DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("user db: ", "upgrading database old contents will be lost." +
                " ["+oldVersion+"] to " + "["+newVersion+"]");
        //sqLiteDatabase.execSQL();
    }
}
