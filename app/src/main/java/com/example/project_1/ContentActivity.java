package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1.contentProvider.UserContentProvider;
import com.example.project_1.db.UserDataBase;

public class ContentActivity extends AppCompatActivity {

    private EditText etName, etPhone;
    private TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        tvDisplay = findViewById(R.id.tvDisplay);
        onShow();
    }

    public void onInsert(View view){
        ContentValues values = new ContentValues();
        values.put(UserDataBase.NAME, etName.getText().toString());
        values.put(UserDataBase.PHONE, etPhone.getText().toString());
        getApplicationContext().getContentResolver().insert(UserContentProvider.CONTENT_URI, values);
        Toast.makeText(this, "Entry created", Toast.LENGTH_SHORT).show();
        onShow();
    }

    public void onReset(View view){
        int delCount = getContentResolver().delete(UserContentProvider.CONTENT_URI, null, null);
        Toast.makeText(this, delCount +" Entries deleted", Toast.LENGTH_SHORT).show();
        onShow();
    }

    public void onShow(){
        Uri uri = UserContentProvider.CONTENT_URI;
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()){
            builder.append(cursor.getString(0)).append(", ")
                    .append(cursor.getString(1)).append(", ")
                    .append(cursor.getString(2))
                    .append("\n");
        }
        tvDisplay.setText(builder.toString());
    }
}