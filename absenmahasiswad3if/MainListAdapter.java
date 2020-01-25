package com.dif.bimo.absenmahasiswad3if;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.HashMap;


public class MainListAdapter extends ResourceCursorAdapter {

    public MainListAdapter(Context context, int layout, Cursor c, int flags){
       super(context,layout,c,flags);
    }

    public HashMap<String, String> getItem(int pos)
    {
        HashMap<String, String> data= new HashMap<>();
        Cursor c=getCursor();
        if (c.moveToPosition(pos)){
            data.put("nim", c.getString(c.getColumnIndex("_id")));
            data.put("nama", c.getString(c.getColumnIndex("nama")));
        }
        return  data;
    }

    @Override
    public void bindView(View view, final Context context, Cursor c) {
        HashMap<String, String> data= new HashMap<>();
            TextView t = (TextView) view.findViewById(R.id.nim);
            t.setText(c.getString(c.getColumnIndex("_id")));
            t = (TextView) view.findViewById(R.id.nama);
            t.setText(c.getString(c.getColumnIndex("nama")));
    }






}
