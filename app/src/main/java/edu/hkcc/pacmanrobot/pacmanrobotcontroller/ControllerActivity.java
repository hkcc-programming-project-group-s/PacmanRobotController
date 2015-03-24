package edu.hkcc.pacmanrobot.pacmanrobotcontroller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

import hkccpacmanrobot.utils.Maths;
import hkccpacmanrobot.utils.message.MovementCommand;
import hkccpacmanrobot.utils.message.MovementCommand$;
import hkccpacmanrobot.utils.message.Messenger;
import hkccpacmanrobot.utils.message.Messenger$;


public class ControllerActivity extends ActionBarActivity {
    public void stopGame(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Stop Game")
                .setMessage("Are you sure?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        setTitle("Controller");
        addListener();
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

public Thread sender=new Thread(new Runnable() {
    @Override
    public void run() {

        Socket socket=new Socket();
        try {
            socket.connect(new InetSocketAddress(MainActivity.getServerHostName(),MainActivity.getServerPort()));
            ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
            while (true)
            {
                out.writeObject(new MovementCommand(MovementCommand.MODE_POLAR(),new Maths.Point2D(direction,distance)));
                Thread.sleep(50);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
});
    public void pauseGame(View view) {
        startActivity(new Intent(this, PauseActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
