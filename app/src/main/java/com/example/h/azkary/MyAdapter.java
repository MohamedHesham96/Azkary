package com.example.h.azkary;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by H on 06/08/2016.
 */

class SingleRow {

    String title;
    String count;

    SingleRow(String t, String i) {

        title = t;
        count = i;
    }

}

public class MyAdapter extends BaseAdapter {


    ArrayList<SingleRow> list;

    DBAdapter dbAdapter;

    Context context;

    MyAdapter(Context c) {

    context = c;

        list = new ArrayList<SingleRow>();


        Cursor cursor = dbAdapter.getAllAzkar();

        List<String> s1 = null;
        List<String> s2 = null;

        cursor.moveToFirst();

        //do {

           String ss1 = (cursor.getString(cursor.getColumnIndex(dbAdapter.NAME)));
           String ss2 = (cursor.getString(cursor.getColumnIndex(dbAdapter.COUNT)));

        list.add(new SingleRow(ss1, ss2));

        //} while (cursor.moveToFirst());



    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row, viewGroup, false);

        TextView title = (TextView) row.findViewById(R.id.txt1);
        TextView desc = (TextView) row.findViewById(R.id.txt2);

        SingleRow temp = list.get(i);

        title.setText(temp.title);
        desc.setText(temp.count);

        return row;
    }
}
