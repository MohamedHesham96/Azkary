package com.example.h.azkary;

import android.app.*;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import static com.example.h.azkary.R.id.fav_CB;

public class EditZekrActivity extends Activity {

    DBAdapter dbAdapter;
    EditText newZekrName_ET;
    TextView oldZekrName_TV;
    MyApplication  application;
    String zekrName;
    String zekrCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);

        application = (MyApplication) getApplication();

        setContentView(R.layout.activity_zekr_edit_layout);

        // zekrName = getIntent().getExtras().getString("zekrName");
       // تتحفظ علشان هنستخدمها علشان نبعتها تاني للاكتفيتي الجديدة من الكونت
        // zekrCount = getIntent().getExtras().getString("zekrCount");

        this.setFinishOnTouchOutside(false);

        newZekrName_ET = (EditText) findViewById(R.id.zekrName);

        oldZekrName_TV = (TextView) findViewById(R.id.oldzekrName_TV);

        oldZekrName_TV.setText(zekrName);

    }

    public void editName(View view) {

        String oldName = oldZekrName_TV.getText().toString();

        String newName = newZekrName_ET.getText().toString();


        dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        Cursor cursor = dbAdapter.getRow(newName);

        if (!cursor.moveToFirst()) {

            dbAdapter.editZekrName(oldName, newName);
            // علشان ابعت الاسم الجديد للكونت اكتيفيتي ... علشان العدد الحالي ميروحش
            application.setZekrName(newName);
        }
        else {

            Toast ss = Toast.makeText(getBaseContext(), "هذا الذكر موجود بالفعل", Toast.LENGTH_SHORT);
            ss.getView().setBackgroundColor(Color.parseColor("#00ff00"));
          //  ss.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            ss.show();
        }

        Intent intent = new Intent(getBaseContext(), CountActivity.class);

      /*  intent.putExtra("zekrName", newZekrName_ET.getText().toString());
        intent.putExtra("countName", zekrCount);

        startActivity(intent);
*/
        finish();
    }


}
