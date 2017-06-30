package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.zoptal.gaantori.Adapter.Adapter_SrchAlbums;
import com.zoptal.gaantori.Adapter.Adapter_SrchArtist;
import com.zoptal.gaantori.Adapter.Adapter_SrchPlaylist;
import com.zoptal.gaantori.Adapter.Adapter_SrchSong;
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


public class Json_Srch extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    public static  Model_Srch model_srch;
    ListView listview,listView1,listview3,listView4;
    Context context;


    public Json_Srch(Context context,ListView listview,ListView listView1,ListView listview3,ListView listView4) {
        // TODO Auto-generated constructor stub

        this.listview=listview;
        this.listView1=listView1;
        this.listview3=listview3;
        this.listView4=listView4;

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
                RegisterUrl.srch);


        try {
            // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("search_text",params[0]));
            nameValuePairs.add(new BasicNameValuePair("user_id","0"));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result);

                 model_srch = new Model_Srch();

                ArrayList<SrchAlbum> al_albumsrch = new ArrayList<SrchAlbum>();
                ArrayList<SrchArtist> al_artistsrch = new ArrayList<SrchArtist>();
                ArrayList<Playlist> al_playlist = new ArrayList<Playlist>();
                ArrayList<TopSongs> al_song = new ArrayList<TopSongs>();

                JSONArray ary_albumsrch= main_obj.getJSONArray("albums");

                for (int i = 0; i < ary_albumsrch.length(); i++) {

                    JSONObject obj = ary_albumsrch.getJSONObject(i);
                    String id = obj.getString("id");
                    String image= obj.getString("image").replace(" ", "%20");
                    String title= obj.getString("albumtitle");
                    String artist= obj.getString("artists");
                    String singer= obj.getString("singer");

                    SrchAlbum f = new SrchAlbum();
                    f.setId(id);
                    f.setImage(image);
                    f.setTitle(title);
                    f.setArtists(artist);
                    f.setSinger(singer);

                    al_albumsrch.add(f);

                }

                JSONArray ary_artist= main_obj.getJSONArray("artists");

                for (int i = 0; i < ary_artist.length(); i++) {

                    JSONObject obj = ary_artist.getJSONObject(i);
                    String id = obj.getString("id");
                    String image= obj.getString("image").replace(" ", "%20");
                    String name= obj.getString("name");
                    String fcount= obj.getString("fcount");
                    String fstatus= obj.getString("fstatus");

                   SrchArtist f1 = new SrchArtist();

                    f1.setId(id);
                    f1.setImage(image);
                    f1.setName(name);
                    f1.setFcount(fcount);
                    f1.setFstatus(fstatus);

                    al_artistsrch.add(f1);

                }

                JSONArray ary_playlist1= main_obj.getJSONArray("playlists");

                for (int i = 0; i < ary_playlist1.length(); i++) {

                    JSONObject obj = ary_playlist1.getJSONObject(i);
                    String id = obj.getString("id");
                    String image= obj.getString("albumimage").replace(" ", "%20");
                    String name= obj.getString("albumname");

                    Playlist f2 = new Playlist();

                    f2.setId(id);
                    f2.setAlbumimage(image);
                    f2.setAlbumname(name);

                    al_playlist.add(f2);

                }


                JSONArray ary_song= main_obj.getJSONArray("songs");

                for (int i = 0; i < ary_song.length(); i++) {

                    JSONObject obj = ary_song.getJSONObject(i);
                    String id = obj.getString("id");
                    String title= obj.getString("name");
                    String image= obj.getString("image").replace(" ", "%20");
                    String url= obj.getString("url").replace(" ", "%20");
                    String songlength= obj.getString("songlength");
                    String album= obj.getString("albumname");
                    String album_id= obj.getString("aalbum_id");
                    String artistname= obj.getString("artists_name");
                    String artistid= obj.getString("artists_id");
                    String downloaded=obj.getString("downloaded");



                    TopSongs f2 = new TopSongs();

                    f2.setId(id);
                    f2.setTitle(title);
                    f2.setImage(image);
                    f2.setUrl(url);
                    f2.setSonglength(songlength);
                    f2.setAlbum(album);
                    f2.setAlbumid(album_id);
                    f2.setArtistname(artistname);
                    f2.setArtists(artistid);
                    f2.setDownloaded(downloaded);

                    al_song.add(f2);

                }


                model_srch.setAl_srchalbum(al_albumsrch);
                model_srch.setAl_srchartist(al_artistsrch);
                model_srch.setAl_playlist(al_playlist);
                model_srch.setAl_srchsong(al_song);



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


        listview.setAdapter(new Adapter_SrchAlbums(context,model_srch));
        listView1.setAdapter(new Adapter_SrchArtist(context,model_srch));
        listview3.setAdapter(new Adapter_SrchPlaylist(context,model_srch));
        listView4.setAdapter(new Adapter_SrchSong(context,model_srch));

    }

}
