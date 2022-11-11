package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class BroadCastActivity extends AppCompatActivity {

    EditText etBroadCastMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etBroadCastMessage = findViewById(R.id.etBroadCastMessage);
        setContentView(R.layout.activity_broad_cast);
    }

    public void onBroadcast(View view) {
        etBroadCastMessage = findViewById(R.id.etBroadCastMessage);
        sendBroadcast(new Intent(this, ToastIntentReceiver.class).setAction("MyAction").putExtra("message", etBroadCastMessage.getText().toString()));
        finish();
        //sendBroadcast(intent);
        //Toast.makeText(this, "BroadCast. WIP", Toast.LENGTH_SHORT).show();
    }
}