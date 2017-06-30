package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.gaantori.Adapter.Adapter_AlbumDetail;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
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


public class Json_Saveplaylist_song extends AsyncTask<String, String, String> {

    String res="",sn,st,uname,uemail;
    Context context;
   ProgressDialog loading;
String playlistid;
       public Json_Saveplaylist_song(Context context) {
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
                RegisterUrl.saveplaylist);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("name",params[0]));
            nameValuePairs.add(new BasicNameValuePair("user_id",params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result1);
               playlistid=main_obj.getString("id");



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

        new Json_saveplaylistsong(context).execute(Fragment_Home1.user_id,playlistid, Adapter_AlbumDetail.songid,"1");

    }

}
