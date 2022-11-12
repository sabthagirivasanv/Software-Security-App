package com.example.project_1.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.project_1.db.UserDataBase;

public class UserContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.project_1.user";
    public static final String BASE_PATH = "user";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+BASE_PATH);
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int USERS = 1;
    public static final int USER = 2;

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, USERS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH+"/#", USER);
    }

    SQLiteDatabase db;


    public UserContentProvider() {
    }

    @Override
    public boolean onCreate() {
        db = new UserDataBase(getContext()).getWritableDatabase();
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delCount;
        switch (uriMatcher.match(uri)){
            case USERS:
            case USER:
                delCount = db.delete(UserDataBase.TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Invalid URI"+uri);
        }
        return delCount;
    }

    @Override
    public String getType(Uri uri) {
        if (uriMatcher.match(uri) == 1) {
            return "vnd.android.cursor.dir/contacts";
        }
        throw new IllegalArgumentException("Invalid URI" + uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i("insert uri", uri.toString());
        long id = db.insert(UserDataBase.TABLE, null, values);
        if (id > 0){
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(uri, null);
            return newUri;
        }
        throw new SQLException("Insertion failed for uri: "+uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.i("query uri", uri.toString());
        Cursor cursor;
        if (uriMatcher.match(uri) == USERS) {
            cursor = db.query(UserDataBase.TABLE, UserDataBase.ALL_COLUMNS, selection,
                    null, null, null, UserDataBase.NAME + " ASC");
        } else {
            throw new IllegalArgumentException("Invalid URI" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updateCount;
        if(uriMatcher.match(uri) == USERS){
            updateCount = db.update(UserDataBase.TABLE, values, selection, selectionArgs);
        }else{
            throw new IllegalArgumentException("Invalid URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }
}