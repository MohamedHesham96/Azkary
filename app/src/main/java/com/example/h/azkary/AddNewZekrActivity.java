package com.example.h.azkary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddNewZekrActivity extends Activity {

    DBAdapter dbAdapter;
    EditText zekrName_ET;
    ToggleButton fav_CB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);


        setContentView(R.layout.activity_add_new_zekr);


        this.setFinishOnTouchOutside(false);

        zekrName_ET = (EditText) findViewById(R.id.zekrName);
        fav_CB = (ToggleButton) findViewById(R.id.fav_CB);
    }

    public void insertRow(View view) {

        String name = zekrName_ET.getText().toString();
        int fav_bool = 0;

        if (fav_CB.isChecked()) {

            fav_bool = 1;
        }

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        Cursor cursor = dbAdapter.getRow(name);

        if (!cursor.moveToFirst())
            dbAdapter.insertRow(name, fav_bool, 1);

        else {

            Toast ss = Toast.makeText(getBaseContext(), "هذا الذكر موجود بالفعل", Toast.LENGTH_SHORT);
            ss.getView().setBackgroundColor(Color.parseColor("#00ff00"));
          //  ss.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            ss.show();
        }


        finish();
    }


}
