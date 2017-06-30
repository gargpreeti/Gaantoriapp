package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_saveplaylistsong extends AsyncTask<String, String, String> {

     String res="",sn,st,uname,uemail;
     Context context;
     ProgressDialog loading;
      String msg;
       public Json_saveplaylistsong(Context context) {
        // TODO Auto-generated constructor stub

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
                RegisterUrl.saveplaylistsong);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("user_id",params[0]));
            nameValuePairs.add(new BasicNameValuePair("p_id",params[1]));
            nameValuePairs.add(new BasicNameValuePair("s_id",params[2]));
            nameValuePairs.add(new BasicNameValuePair("flag",params[3]));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result1);
                // JSONObject  obj=main_obj.getString("message");

                res=main_obj.getString("status");
                msg= main_obj.getString("message");

               }

        } catch (Exception e) {
            Log.e("==+result save playlist===", "======" + e);

        }

        return res;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();


        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


    }

}
