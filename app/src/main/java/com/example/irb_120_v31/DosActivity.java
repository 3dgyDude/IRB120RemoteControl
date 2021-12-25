package com.example.irb_120_v31;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class DosActivity extends AppCompatActivity {
    //Declaración de varibles
    Integer SERVERPORT;
    String SERVER_IP;
    TextView Datos;
    TextView Datos2;
    EditText RotZ,RotX,RotY,MovX,MovY,MovZ;
    private ClientThread clientThread;
    private Thread thread;
    private LinearLayout msgList;
    private Handler handler;
    private int clientTextColor;
    public String mensaje;
    String ip,puerto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Obtener parámetros del layout
        RotZ = (EditText)findViewById(R.id.RotZ);
        RotX = (EditText)findViewById(R.id.RotX);
        RotY = (EditText)findViewById(R.id.RotY);
        MovX = (EditText)findViewById(R.id.MovX);
        MovY = (EditText)findViewById(R.id.MovY);
        MovZ = (EditText)findViewById(R.id.MovZ);
        //Obtener datos de la actividad anterior
        Bundle extras = getIntent().getExtras();
        ip = extras.getString("dato01");
        puerto = extras.getString("dato02");
        //Visualizar ip y puerto en layout
        Datos = (TextView) findViewById(R.id.Datos);
        Datos.setText("Ip: " + ip);
        Datos2 = (TextView) findViewById(R.id.datos2);
        Datos2.setText("Puerto: " + puerto);

        SERVER_IP=ip;
        SERVERPORT = Integer.parseInt(puerto);
        //Generación de atributos
        setTitle("Cliente");
        clientTextColor = ContextCompat.getColor(this, R.color.green);
        handler = new Handler();
        msgList = findViewById(R.id.msgList);
    }

    public TextView textView(String message, int color) {
        if (null == message || message.trim().isEmpty()) {
            message = "<Nah>";
        }
        TextView tv = new TextView(this);
        tv.setTextColor(color);
        tv.setText(message + " [" + getTime() + "]");
        tv.setTextSize(20);
        tv.setPadding(0, 5, 0, 0);
        return tv;
    }

    public void showMessage(final String message, final int color) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                msgList.addView(textView(message, color));
            }
        });
    }

    public void onClick(View view) {

        if (view.getId() == R.id.connect_server) {
            msgList.removeAllViews();
            showMessage("Conectando al robo...", clientTextColor);
            clientThread = new ClientThread();
            thread = new Thread(clientThread);
            thread.start();
            showMessage("Conectado!", clientTextColor);
            return;
        }

        if (view.getId() == R.id.send_data) {
            mensaje=("RotZ:" + RotZ.getText().toString().trim() + "RotY:" + RotY.getText().toString().trim() +
                    "RotX:" + RotX.getText().toString().trim() + "MovZ:" + MovZ.getText().toString().trim() +
                    "MovY:" + MovY.getText().toString().trim() + "MovX:" + MovX.getText().toString().trim());
            String clientMessage = mensaje;
            if (null != clientThread) {
                clientThread.sendMessage(clientMessage);
            }
        }

        if (view.getId() == R.id.mod) {
            Intent cont2 = new Intent(DosActivity.this, TresActivity.class);
            cont2.putExtra("dato03",ip);
            cont2.putExtra("dato04",puerto);
            startActivity(cont2);
        }
    }

    class ClientThread implements Runnable {

        private Socket socket;
        private BufferedReader input;

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);

                while (!Thread.currentThread().isInterrupted()) {

                    this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message = input.readLine();
                    if (null == message || "Adios".contentEquals(message)) {
                        Thread.interrupted();
                        message = "Servidor Adios.";
                        showMessage(message, Color.RED);
                        break;
                    }
                    showMessage("Er robo: " + message, clientTextColor);
                }

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        void sendMessage(final String message) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (null != socket) {
                            PrintWriter out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(socket.getOutputStream())),
                                    true);
                            out.println(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != clientThread) {
            clientThread.sendMessage("Me fui");
            clientThread = null;
        }
    }
}