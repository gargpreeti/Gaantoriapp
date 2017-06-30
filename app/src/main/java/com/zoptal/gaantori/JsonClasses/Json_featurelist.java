package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.zoptal.gaantori.Adapter.Adapter_FeaturedPlaylist;
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


public class Json_featurelist extends AsyncTask<String, String, String> {

    ProgressDialog loading;
   public static ArrayList<NewRelease> al_newrelease ;


    GridView grid;
    Context context;
    String res = "";

    public Json_featurelist(Context context, GridView grid) {
        // TODO Auto-generated constructor stub
        this.grid = grid;
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
                RegisterUrl.featurelist);

        try {
            // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("user_id","0"));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);



                al_newrelease= new ArrayList<NewRelease>();

                JSONArray ary_products =new JSONArray(result);

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String image = obj.getString("albumimage").replace(" ", "%20");
                    String name = obj.getString("albumname");
                    String songs = obj.getString("totalsongs");


               NewRelease f = new NewRelease();

                    f.setId(id);
                    f.setImage(image);
                    f.setName(name);
                    f.setArtists(songs);

                    al_newrelease.add(f);

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

        grid.setAdapter(new Adapter_FeaturedPlaylist(context, al_newrelease));

    }

}
