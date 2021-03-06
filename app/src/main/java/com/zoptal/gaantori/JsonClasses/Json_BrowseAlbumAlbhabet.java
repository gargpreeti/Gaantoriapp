package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.zoptal.gaantori.Adapter.Adapter_NewRelease;
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


public class Json_BrowseAlbumAlbhabet extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    public static Model_NewRelease model_nwrel;
     Context context;

    public static String artists1d;

    GridView grid;


    public Json_BrowseAlbumAlbhabet(Context context,GridView grid) {
        // TODO Auto-generated constructor stub
        this.grid=grid;
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
                RegisterUrl.allalbumalphabet);


        try {
            // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("type",params[0]));
           nameValuePairs.add(new BasicNameValuePair("page_number",params[1]));
            nameValuePairs.add(new BasicNameValuePair("alphabet",params[2]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);


                model_nwrel = new Model_NewRelease();

                ArrayList<NewRelease> newrel = new ArrayList<NewRelease>();

                JSONArray ary_products =new JSONArray(result);

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String image = obj.getString("image").replace(" ", "%20");
                    String name = obj.getString("name");
                    String artists = obj.getString("artists");
                    artists1d = obj.getString("artistids");


                  NewRelease f = new NewRelease();
                    f.setId(id);
                    f.setImage(image);
                    f.setName(name);
                    f.setArtists(artists);
                    f.setArtistids(artists1d);


                    newrel.add(f);

                }


               model_nwrel.setAl_newrelease(newrel);

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


        grid.setAdapter(new Adapter_NewRelease(context,model_nwrel));



    }

}
