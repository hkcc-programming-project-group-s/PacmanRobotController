package edu.hkcc.pacmanrobot.controller.scalaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


/**
 * Created by beenotung on 3/26/15.
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void startGame(View view) {
        startActivity(new Intent(this, ControllerActivity.class));
    }
    public static String getServerHostName() {
        return "192.168.43.1";
    }

    public static int getServerPort() {
        return 1234;
    }
}