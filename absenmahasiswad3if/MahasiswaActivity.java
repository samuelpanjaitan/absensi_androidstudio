package com.dif.bimo.absenmahasiswad3if;


import android.content.ContentValues;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MahasiswaActivity extends AppCompatActivity {

    JSONArray mhs;
    Database_helper dbHelper;
    SharedPreferences sharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        dbHelper = new Database_helper(this);
        sharedpref = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedpref.getInt("first_use",1)==1){
            loadData();
        }

        dbHelper = new Database_helper(this);
        ListView list= (ListView) findViewById(R.id.listView);
        final MainListAdapter adapter = new MainListAdapter(this, R.layout.detail_list, dbHelper.getData(),0);
        list.setAdapter(adapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                HashMap<String,String> myData = adapter.getItem(position);
//                    Intent in = new Intent(getApplicationContext(), CalendarActivity.class);
//                    in.putExtra("nim", myData.get("nim"));
//                    in.putExtra("nama", myData.get("nama"));
//                    startActivity(in);
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        new AsyncTask<Void, Void, String>() {

            boolean succes = false;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    String sURL = "http://dif.indraazimi.com/mhs.php?q=3801";
                    URL url = new URL(sURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(10000);
                    con.setReadTimeout(10000);
                    if (con.getResponseCode() == 200) {
                        BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(bis, "UTF-8"));
                        String line;
                        StringBuilder sb = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                        bis.close();
                        succes = true;
                        return sb.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String aVoid) {
                if (succes) {
                    System.out.println(aVoid);
                    try{
                        mhs = new JSONArray(aVoid);
                        for (int i=0; i<mhs.length(); i++)
                        {
                            JSONObject jobj = mhs.getJSONObject(i);
                            ContentValues v = new ContentValues();
                            v.put("nim",jobj.getString("nim"));
                            v.put("nama",jobj.getString("nama"));
                            dbHelper.insertData(v);
                        }
                        sharedpref.edit().putInt("fist_use",0).apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                super.onPostExecute(aVoid);

            }
        }.execute();
    }
}
