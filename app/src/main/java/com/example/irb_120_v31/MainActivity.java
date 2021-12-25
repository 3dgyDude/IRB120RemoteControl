package com.example.irb_120_v31;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText ip,puerto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip = (EditText)findViewById(R.id.ip);
        puerto = (EditText)findViewById(R.id.puerto);
    }

    public void control(View v) {
        Intent cont = new Intent(MainActivity.this, DosActivity.class);
        cont.putExtra("dato01",ip.getText().toString());
        cont.putExtra("dato02",puerto.getText().toString());
        startActivity(cont);
    }
}
