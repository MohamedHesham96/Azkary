package com.example.h.azkary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MyFavActivity extends Activity {

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
        setContentView(R.layout.activity_my_fav);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setTitle("الأذكار المفضلة");

        openDB();
        poplateListView();

    }

    public void openDB() {

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
    }

    public void poplateListView() {

        ListView listView = (ListView) findViewById(R.id.myFavList);

        String[] fromFieldNames = new String[]{DBAdapter.NAME, DBAdapter.COUNT};
        int[] toViewIDs = new int[]{R.id.txt1, R.id.txt2};

        final Cursor cursor = dbAdapter.getAllFavAzkar();

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
                intent.putExtra("Caller", "MyFav");

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbAdapter.close();

    }

}
