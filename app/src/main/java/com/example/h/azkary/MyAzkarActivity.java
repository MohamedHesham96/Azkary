package com.example.h.azkary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MyAzkarActivity extends Activity {

    DBAdapter dbAdapter;

    @Override
    protected void onResume() {
        super.onResume();

        openDB();
        poplateListView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_azkar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        openDB();
        poplateListView();
    }

    public void openDB() {

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
    }

    public void poplateListView() {

        ListView listView = (ListView) findViewById(R.id.myAzkarList);

        String[] fromFieldNames = new String[]{DBAdapter.NAME, DBAdapter.COUNT};
        int[] toViewIDs = new int[]{R.id.txt1, R.id.txt2};

        final Cursor cursor = dbAdapter.getAllMyAzkar();

        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.second, cursor, fromFieldNames, toViewIDs, 0);

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
                intent.putExtra("Caller", "MyAzkar");
                startActivity(intent);

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



