package com.zoptal.gaantori.JsonClasses;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.zoptal.gaantori.Fragment.Fragment_AlbumDetailAll;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.MainActivity1;
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
import java.util.Arrays;
import java.util.List;


public class Json_albumdetailAll extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    public static  Model_AlbumDetail model_albumDetail;
    ListView list;
    Context context;
    MainActivity1 activity1;
    public static String albumid,albumname,albumartist,albumartistid,albumimage;

    public Json_albumdetailAll(Context context) {
        // TODO Auto-generated constructor stub

        this.context = context;
        activity1 = (MainActivity1) context;
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
             RegisterUrl.album_detail);

        try {
            // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("album_id",params[0]));
            nameValuePairs.add(new BasicNameValuePair("user_id",params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);



                JSONObject main_obj = new JSONObject(result);

                //To get from database
                albumid = main_obj.getString("albumid");
              albumname=main_obj.getString("albumname");
               albumartist= main_obj.getString("albumartist");
               albumartistid= main_obj.getString("albumartistid");
                albumimage= main_obj.getString("albumimage").replace(" ", "%20");



                final List<String> aimage = Arrays.asList(albumimage.split(","));
                String img=aimage.get(0);
                albumimage=img;

                // add your items, that is from database

                model_albumDetail = new Model_AlbumDetail();

                model_albumDetail.setAlbumid(albumid);
                model_albumDetail.setAlbumname(albumname);
                model_albumDetail.setAlbumartist(albumartist);
                model_albumDetail.setAlbumartistid(albumartistid);


                 ArrayList<TopSongs> al_song = new ArrayList<TopSongs>();
                JSONArray ary_songs= main_obj.getJSONArray("songs");

                for (int i = 0; i < ary_songs.length(); i++) {

                    JSONObject obj = ary_songs.getJSONObject(i);
                    String songid = obj.getString("id");
                    String songurl= obj.getString("url").replace(" ", "%20");
                    String songtitle= obj.getString("title");
                    String songartist= obj.getString("artists");
                    String singer= obj.getString("singer");
                    String songlength= obj.getString("songlength");
                    String image1 = obj.getString("albumimage").replace(" ", "%20").replace(",","");
                    String album= obj.getString("albumname");
                    String albumid= obj.getString("albumid");
                    String downloaded=obj.getString("downloaded");



                    TopSongs f = new TopSongs();

                    f.setId(songid);
                    f.setUrl(songurl);
                    f.setTitle(songtitle);
                    f.setArtists(songartist);
                    f.setArtistname(singer);
                    f.setSonglength(songlength);
                    f.setImage(image1);
                    f.setAlbum(album);
                    f.setAlbumid(albumid);
                    f.setDownloaded(downloaded);

                    al_song.add(f);

                }
                model_albumDetail.setAl_songs(al_song);

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

        activity1.linear_large.setVisibility(View.GONE);
        //Album detail fragment
        Fragment_AlbumDetailAll fragment_detail=new Fragment_AlbumDetailAll();
        FragmentManager fragmentManager3 = ((Activity)context).getFragmentManager();
        fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).addToBackStack(null).commit();
        MainActivity1.Drawer.closeDrawer(Gravity.LEFT);

    }

}
