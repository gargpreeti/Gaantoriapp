package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.zoptal.gaantori.Adapter.Adapter_Popular;
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


public class Json_artistdetail extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    public static  Model_ArtistDetail model_artistDetail;
    ListView list;
    Context context;


    public Json_artistdetail(Context context, ListView list) {
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
                RegisterUrl.artist_detail);


        try {
            // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("user_id",params[0]));
             nameValuePairs.add(new BasicNameValuePair("artist_id",params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

               JSONObject main_obj = new JSONObject(result);

               String id = main_obj.getString("artistid");
                String artistname=main_obj.getString("artistname");
               String artistimage= main_obj.getString("artistimages").replace(" ", "%20");

                String fcount= main_obj.getString("fcount");
                String fstat= main_obj.getString("userfollowstatus_toartist");


                model_artistDetail = new Model_ArtistDetail();

                model_artistDetail.setArtistid(id);
                model_artistDetail.setArtistame(artistname);
                model_artistDetail.setArtistimages(artistimage);
                model_artistDetail.setFcount(fcount);
                model_artistDetail.setFstat(fstat);


                ArrayList<Album> al_albm = new ArrayList<Album>();

                JSONArray ary_songs= main_obj.getJSONArray("albums");

                for (int i = 0; i < ary_songs.length(); i++) {

                    JSONObject obj = ary_songs.getJSONObject(i);
                    String songid = obj.getString("id");
                    String songurl= obj.getString("url");
                    String songtitle= obj.getString("title");
                    String songartist= obj.getString("artists");

                    Album f = new Album();

                    f.setId(songid);
                    f.setUrl(songurl);
                    f.setTitle(songtitle);
                    f.setArtists(songartist);

                    al_albm.add(f);

                }


                model_artistDetail.setAl_album(al_albm);


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


         list.setAdapter(new Adapter_Popular(context,model_artistDetail));


    }

}
