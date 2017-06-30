package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.zoptal.gaantori.Adapter.Adapter_MyPlaylist_Music;
import com.zoptal.gaantori.url.RegisterUrl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_MyPlaylist extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    public static  ArrayList<MyPlaylist> al_myplaylist;
    ListView list;
    Context context;


    public Json_MyPlaylist(Context context, ListView list) {
        // TODO Auto-generated constructor stub
        this.list=list;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        loading = new ProgressDialog(context);
        loading.setMessage("loading");
        loading.show();
    }

    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(
                RegisterUrl.userplaylist);


        try {
            // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("user_id",params[0]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

          JSONObject main_obj = new JSONObject(result);

                al_myplaylist = new ArrayList<MyPlaylist>();

                JSONArray ary_songs= main_obj.getJSONArray("records");

                for (int i = 0; i < ary_songs.length(); i++) {

                    JSONObject obj = ary_songs.getJSONObject(i);
                    String id = obj.getString("id");
                    String name= obj.getString("name");
                    String nofs= obj.getString("nofs");

                    MyPlaylist f = new MyPlaylist();

                    f.setId(id);
                    f.setName(name);
                    f.setNofs(nofs);


                    al_myplaylist.add(f);

                    Log.e("mylist====",""+result);
                }




            }

        } catch (Exception e) {
            Log.e("==+Error===", "Error===" + e);

        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();


       list.setAdapter(new Adapter_MyPlaylist_Music(context,al_myplaylist));


    }

}
