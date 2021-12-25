package com.example.irb_120_v31;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;


public class TresActivity extends AppCompatActivity {

    TextView Datos3;
    TextView Datos4;
    String ip,puerto;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tres);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Obtener datos de la actividad anterior
        Bundle extras = getIntent().getExtras();
        ip = extras.getString("dato03");
        puerto = extras.getString("dato04");
        //Visualizar ip y puerto en layout
        Datos3 = (TextView) findViewById(R.id.datos3);
        Datos3.setText("Ip: " + ip);
        Datos4 = (TextView) findViewById(R.id.datos4);
        Datos4.setText("Puerto: " + puerto);
    }
}
