package com.example.h.azkary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CountActivity extends Activity {

    DBAdapter dbAdapter;
    Intent intent;
    Button editB;

    TextView zekrName_TV, oldCount_TV, totalCount_TV;
    Button buttonCount, delete_B;
    ToggleButton toggleButton;
    int x = 0;
    int y = 0;
    int z = 0;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       // application = (MyApplication) getApplication();


        setContentView(R.layout.activity_count);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        dbAdapter = new DBAdapter(getBaseContext());
        dbAdapter.open();

        oldCount_TV = (TextView) findViewById(R.id.oldCount);
        totalCount_TV = (TextView) findViewById(R.id.totalCount);
        zekrName_TV = (TextView) findViewById(R.id.zekrName);

        delete_B = (Button) findViewById(R.id.delete_B);
        toggleButton = (ToggleButton) findViewById(R.id.fav_TB);
        editB = (Button) findViewById(R.id.edit_B);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String name = zekrName_TV.getText().toString();

                if (isChecked) {

                    z = 1;

                } else {

                    z = 0;
                }

                dbAdapter.updateRow(name, z);
            }
        });


        intent = getIntent();
        Bundle bundle = intent.getExtras();

        String caller = (String) bundle.get("Caller");

        String name = intent.getStringExtra("zekrName");
        zekrName_TV.setText(name);

        //Toast.makeText(getBaseContext(), intent.getStringExtra("theCaller"), Toast.LENGTH_LONG).show();

        String count = intent.getStringExtra("zekrCount");

        oldCount_TV.setText(count);
        totalCount_TV.setText(count);

        if (caller.equals("DefaultAzkar")) {

            delete_B.setVisibility(View.INVISIBLE);
            editB.setVisibility(View.INVISIBLE);
        }

        Cursor cursor = dbAdapter.getRow(name);
        String favBool = cursor.getString(1);

        if (favBool.equals("1")) {

            toggleButton.setChecked(true);

        } else {

            toggleButton.setChecked(false);
        }



    }

    public void save(View view) {

      /* String caller = intent.getStringExtra("Caller");

        if (caller.equals("MyAzkar")) {

            startActivity(new Intent(getBaseContext(), MyAzkarActivity.class));
        }

       else if (caller.equals("DefaultAzkar")) {

            startActivity(new Intent(getBaseContext(), DefaultAzkarActivity.class));
        }

        else if (caller.equals("MyFav")) {

            startActivity(new Intent(getBaseContext(), MyFavActivity.class));
        }
*/
        dbAdapter.updateRow(zekrName_TV.getText().toString(), totalCount_TV.getText().toString());

        finish();
    }

    public void count(View view) {

        buttonCount = (Button) findViewById(R.id.countButton);

        oldCount_TV = (TextView) findViewById(R.id.oldCount);
        totalCount_TV = (TextView) findViewById(R.id.totalCount);

        String txt = buttonCount.getText().toString();
        x = Integer.parseInt(txt) + 1;
        String txt2 = String.valueOf(x);
        buttonCount.setText(txt2);

        y = Integer.parseInt(oldCount_TV.getText().toString()) + x;

        totalCount_TV.setText(String.valueOf(y));

    }

    public void delete(View view) {

        new AlertDialog.Builder(this)
                .setTitle("تأكيد")
                .setMessage("هل انت متأكد انك تريد حذف هذا الذكر ؟")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        String name = zekrName_TV.getText().toString();
                        dbAdapter.deleteRow(name);

                        Toast ss = Toast.makeText(getBaseContext(), "تم الحذف بنجاح", Toast.LENGTH_SHORT);
                        ss.getView().setBackgroundColor(Color.parseColor("#00ff00"));
                        ss.show();
                        finish();
                    }
                })

                .setNegativeButton("لا", null).show();
    }

    public void edit(View view) {

        Intent intent = new Intent(this, EditZekrActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbAdapter.updateRow(zekrName_TV.getText().toString(), totalCount_TV.getText().toString());
        dbAdapter.close();

        if (oldCount_TV.getText().toString().equals(totalCount_TV.getText().toString()));

        else {
            Toast ss = Toast.makeText(getBaseContext(), "تم الحفظ بنجاح", Toast.LENGTH_SHORT);
            ss.getView().setBackgroundColor(Color.parseColor("#00ff00"));
            ss.show();
        }
    }

}


