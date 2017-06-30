package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.zoptal.gaantori.Adapter.Adapter_TopSongs;
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


public class Json_TopSongs extends AsyncTask<String, String, String> {

     ProgressDialog loading;
     Context context;
     ListView list;
    public static ArrayList<TopSongs> al_topsong= new ArrayList<TopSongs>();
    public static TopSongs f;

    public Json_TopSongs(Context context,ListView list) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.list=list;
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
                RegisterUrl.top_songs);

        try {
            // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("user_id",params[0]));
             nameValuePairs.add(new BasicNameValuePair("page_number",params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONArray ary_products =new JSONArray(result);

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String artistids= obj.getString("artistids");
                    String name = obj.getString("artistnames");
                    String artists= obj.getString("artists");
                    String timesplayed= obj.getString("timesplayed");
                    String title= obj.getString("title");
                    String artist= obj.getString("artist");
                    String album_id= obj.getString("album_id");
                    String album= obj.getString("album");
                    String value= obj.getString("value");
                    String url= obj.getString("url").replace(" ", "%20");
                    String image1 = obj.getString("image").replace(" ","%20");
                    String songlength= obj.getString("songlength");
                    String timesofplay= obj.getString("timesofplay");
                    String downloaded=obj.getString("downloaded");



                    f = new TopSongs();

                    f.setId(id);
                    f.setArtistid(artistids);
                    f.setArtistname(name);
                    f.setArtists(artists);
                    f.setTimesplayed(timesplayed);
                    f.setTitle(title);
                    f.setArtist(artist);
                    f.setAlbumid(album_id);
                    f.setAlbum(album);
                    f.setValue(value);
                    f.setUrl(url);
                    f.setImage(image1);
                    f.setSonglength(songlength);
                    f.setTimesofplay(timesofplay);
                    f.setDownloaded(downloaded);

                    al_topsong.add(f);
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

        list.setAdapter(new Adapter_TopSongs(context,al_topsong));

   }

}
