package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1.contentProvider.UserContentProvider;
import com.example.project_1.db.UserDataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentActivity extends AppCompatActivity {

    private EditText etName, etPhone;
    private ListView llDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        llDisplay = findViewById(R.id.llDisplay);
        registerForContextMenu(llDisplay);
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
        List<Map<String, String>> list = new ArrayList<>();
        while (cursor.moveToNext()){
//            list.add(new StringBuilder().append("Id: ").append().append("\n")
//                    .append("Name: ").append().append("\n")
//                    .append("Ph No: ").append().toString());
            HashMap<String, String> item = new HashMap<>();
            item.put( "line1", cursor.getString(1));
            item.put( "line2", "ID: "+cursor.getString(0));
            item.put( "line3", "PHONE: "+cursor.getString(2));
            list.add(item);
        }
        SimpleAdapter sa = new SimpleAdapter(this, list,
                R.layout.multiline_list_view,
                new String[]{"line1", "line2", "line3"},
                new int[]{R.id.line_name, R.id.line_id, R.id.line_ph});
        llDisplay.setAdapter(sa);
    }
}