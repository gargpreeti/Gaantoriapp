package com.zoptal.gaantori.JsonClasses;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.List;


public class Json_SongPlayCount extends AsyncTask<String, String, String> {

    String res="",sn,uname,uemail;
    Context context;


       public Json_SongPlayCount(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    @Override
    protected void onPreExecute() {

        super.onPreExecute();


    }
    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.songplaycount);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("sid", params[0]));
            nameValuePairs.add(new BasicNameValuePair("uid", params[1]));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
                Log.e("result song count---",""+result1);


            }

        } catch (Exception e) {
            Log.e("==+result songplaycount===", "======" + e);

        }


        return res;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

    }

}
