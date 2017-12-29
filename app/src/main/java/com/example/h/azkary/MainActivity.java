package com.example.h.azkary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button defaultButton;
    Button myAzkarButton;
    Button myFavButton;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //   dbAdapter = new DBAdapter(this);

        defaultButton = (Button) findViewById(R.id.defaultAzkarButton);
        myAzkarButton = (Button) findViewById(R.id.myAzkarButton);
        myFavButton   = (Button) findViewById(R.id.myFavButton);
    }

    public void Click(View view) {

        int id = view.getId();

        if (id == defaultButton.getId()) {

            startActivity(new Intent(this, DefaultAzkarActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        else if (id == myAzkarButton.getId()) {

            startActivity(new Intent(this, MyAzkarActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        else if (id == myFavButton.getId()) {

            startActivity(new Intent(this, MyFavActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

    }
}
