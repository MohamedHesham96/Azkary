package com.example.h.azkary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DefaultAzkarActivity extends Activity {

    DBAdapter dbAdapter;

    Button myAzkarButton;
    Button myFavButton;
    TextView modeTV;

    @Override
    protected void onResume() {
        super.onResume();

        openDB();
        poplateListView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_list_layout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        myAzkarButton = (Button) findViewById(R.id.azkary_B);
        myFavButton = (Button) findViewById(R.id.fav_B);

     //   modeTV = (TextView) findViewById(R.id.mode_TV);

        openDB();


       dbAdapter.insertRow("سبحانك", 0, 0);
        dbAdapter.insertRow("تباركت وتعاليت", 0, 0);
        dbAdapter.insertRow("ربنا اغفر لنا", 0, 0);
        dbAdapter.insertRow("اللهم احمنا", 0, 0);
        dbAdapter.insertRow("الحمد لله", 0, 0);
        dbAdapter.insertRow("الله اكبر", 0, 0);
        dbAdapter.insertRow("سبحان الله", 0, 0);
        dbAdapter.insertRow("اللهم اصلح الحال", 0, 0);


        poplateListView();


    }

    public void Click(View view) {

        int id = view.getId();

        ListView listView = (ListView) findViewById(R.id.defaultList);

        String[] fromFieldNames = new String[]{DBAdapter.NAME, DBAdapter.COUNT};
        int[] toViewIDs = new int[]{R.id.txt1, R.id.txt2};

        if (id == myAzkarButton.getId()) {

            findViewById(R.id.addButton).setVisibility(View.VISIBLE);
            final Cursor cursor = dbAdapter.getAllMyAzkar();

            SimpleCursorAdapter myCursorAdapter;
            myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.second, cursor, fromFieldNames, toViewIDs, 0);

            listView.setAdapter(myCursorAdapter);

            modeTV.setText("الأذكار المضافة");

        } else if (id == myFavButton.getId()) {

            findViewById(R.id.addButton).setVisibility(View.INVISIBLE);

            final Cursor cursor = dbAdapter.getAllFavAzkar();

            SimpleCursorAdapter myCursorAdapter;
            myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.second, cursor, fromFieldNames, toViewIDs, 0);

            listView.setAdapter(myCursorAdapter);

            modeTV.setText("الأذكار المفضلة");

        } else {
            findViewById(R.id.addButton).setVisibility(View.INVISIBLE);

            final Cursor cursor = dbAdapter.getAllAzkar();

            SimpleCursorAdapter myCursorAdapter;
            myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.single_row, cursor, fromFieldNames, toViewIDs, 0);

            listView.setAdapter(myCursorAdapter);

            modeTV.setText("الأذكار الأساسية");
        }


   /*     if (id == myAzkarButton.getId()) {

            startActivity(new Intent(this, MyAzkarActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else if (id == myFavButton.getId()) {

            startActivity(new Intent(this, MyFavActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        } */
    }

    public void openDB() {

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
    }

    public void poplateListView() {

        ListView listView = (ListView) findViewById(R.id.defaultList);

        String[] fromFieldNames = new String[]{DBAdapter.NAME, DBAdapter.COUNT};
        int[] toViewIDs = new int[]{R.id.txt1, R.id.txt2};

        final Cursor cursor = dbAdapter.getAllAzkar();

        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.single_row, cursor, fromFieldNames, toViewIDs, 0);

        listView.setAdapter(myCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getBaseContext(), CountActivity.class);

                cursor.moveToFirst();

                for (int i = 0; i < position; i++) {

                    cursor.moveToNext();
                }

                intent.putExtra("zekrName", cursor.getString(1));
                intent.putExtra("zekrCount", cursor.getString(2));
                intent.putExtra("Caller", "DefaultAzkar");

                startActivity(intent);
                //  overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
    }

    public void addZekr(View view) {

        startActivity(new Intent(getBaseContext(), AddNewZekrActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbAdapter.close();

    }
}
