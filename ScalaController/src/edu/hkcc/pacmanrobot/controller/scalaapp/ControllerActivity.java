package edu.hkcc.pacmanrobot.controller.scalaapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import studentrobot.code.Config;
import studentrobot.code.MovementCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static studentrobot.code.Maths.Point2D;


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

    @Override
    protected void onPause() {
        try {
            sender.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onPause();
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

        public ServerSocket serverSocket;

        @Override
        public void run() {
            super.run();
            try {
                serverSocket = new ServerSocket(Config.PORT_MOVEMENT_COMMAND);
                while (true) {
                    try {
                        Log.w("DEBUG", "listen connect from port : " + serverSocket.getLocalPort());
                        Socket socket = serverSocket.accept();
                        Log.w("DEBUG", "connected to " + socket.getInetAddress().getHostName() + " (" + socket.getInetAddress().getHostAddress() + ")");
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        while (true) {
                            if (socket.isClosed()) throw new SocketException();
                            MovementCommand message = new MovementCommand(new Point2D(direction, distance));
                            out.writeObject(message);
                            //distance= direction = 0d;
                            //Log.w("DEBUG", "sent " + message.toString());
                            Thread.sleep(Config.MOVEMENT_COMMAND_INTERVAL);
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}