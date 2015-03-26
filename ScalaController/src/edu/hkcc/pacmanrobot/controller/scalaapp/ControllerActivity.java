package edu.hkcc.pacmanrobot.controller.scalaapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import hkccpacmanrobot.utils.Config;
import hkccpacmanrobot.utils.Maths;
import hkccpacmanrobot.utils.message.MovementCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by beenotung on 3/26/15.
 */

public class ControllerActivity extends Activity {
    public ControllerActivity controllerActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        setTitle("Controller");
        addListener();
        controllerActivity = this;
    }

    Sender sender = new Sender(this);

    @Override
    protected void onResume() {
        super.onResume();
        controllerActivity = this;
        sender.start();
    }

    public final double RADIUS = 5d;

    public void addListener() {
        ((Button) findViewById(R.id.button_UP)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = 0;
                distance = RADIUS;
            }
        });
        ((Button) findViewById(R.id.button_UP_RIGHT)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = Math.PI * 0.25;
                distance = RADIUS;
            }
        });
        ((Button) findViewById(R.id.button_RIGHT)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = Math.PI * 0.5;
                distance = RADIUS;
            }
        });
        ((Button) findViewById(R.id.button_DOWN_RIGHT)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = Math.PI * 0.75;
                distance = RADIUS;
            }
        });
        ((Button) findViewById(R.id.button_DOWN)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = Math.PI;
                distance = RADIUS;
            }
        });
        ((Button) findViewById(R.id.button_DOWN_LEFT)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = Math.PI * 1.25;
                distance = RADIUS;
            }
        });
        ((Button) findViewById(R.id.button_LEFT)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = Math.PI * 1.5;
                distance = RADIUS;
            }
        });
        ((Button) findViewById(R.id.button_UP_LEFT)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = Math.PI * 1.75;
                distance = RADIUS;
            }
        });
        ((Button) findViewById(R.id.button_STOP)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance = 0;
            }
        });
    }

    public double direction = 0d;
    public double distance = 0d;

    public class Sender extends Thread {
        private final Context context;

        public Sender(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            super.run();
            try {
                ServerSocket serverSocket = new ServerSocket(Config.PORT_MOVEMENT_COMMAND);
                while (true) {
                    //Toast.makeText(context, "listen connect from port : " + serverSocket.getLocalPort(), Toast.LENGTH_SHORT).show();
                    Log.w("DEBUG", "listen connect from port : " + serverSocket.getLocalPort());
                    Socket socket = serverSocket.accept();
                    try {
                        //socket.connect(new InetSocketAddress(MainActivity.getServerHostName(), MainActivity.getServerPort()));
                        //    Toast.makeText(context, "connected to " + socket.getInetAddress().getHostName() + " (" + socket.getInetAddress().getHostAddress() + ")", Toast.LENGTH_SHORT).show();
                        Log.w("DEBUG", "connected to " + socket.getInetAddress().getHostName() + " (" + socket.getInetAddress().getHostAddress() + ")");
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        while (true) {
                            out.writeObject(new MovementCommand((byte) 0x01, new Maths.Point2D(direction, distance)));
                            Thread.sleep(50);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}