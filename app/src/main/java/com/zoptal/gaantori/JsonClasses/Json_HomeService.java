package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.zoptal.gaantori.Adapter.Adapter_HomeFeatured;
import com.zoptal.gaantori.Adapter.Adapter_HomeNewRelease;
import com.zoptal.gaantori.Adapter.Adapter_SubscribedHome;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_HomeService extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    public static  Model_Home model_home;
    GridView grid;
    GridView grid1;
    GridView grid2;
    Context context;


    public Json_HomeService(Context context, GridView grid,GridView grid1,GridView grid2) {
        // TODO Auto-generated constructor stub
        this.grid=grid;
        this.grid1=grid1;
        this.grid2=grid2;
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
                RegisterUrl.homeservice);


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


                model_home = new Model_Home();


                ArrayList<HomeData> al_homenewrel = new ArrayList<HomeData>();
                ArrayList<HomeDataPlaylist> al_homeplay = new ArrayList<HomeDataPlaylist>();
                ArrayList<HomeDataArtists> al_homearti= new ArrayList<HomeDataArtists>();

                JSONArray ary_newrel= main_obj.getJSONArray("newreleases");

                for (int i = 0; i < ary_newrel.length(); i++) {

                    JSONObject obj = ary_newrel.getJSONObject(i);
                    String id = obj.getString("id");
                    String image= obj.getString("image");
                    String title= obj.getString("albumtitle");
                    String artist= obj.getString("artists");
                    String singer= obj.getString("singer");
                    String artistid= obj.getString("artistid");

                    HomeData f = new HomeData();
                    f.setId(id);
                    f.setImage(image);
                    f.setAlbumtitle(title);
                    f.setArtists(artist);
                    f.setSinger(singer);
                    f.setArtistid(artistid);

                    al_homenewrel.add(f);

                }

                JSONArray ary_artist= main_obj.getJSONArray("artists");

                for (int i = 0; i < ary_artist.length(); i++) {

                    JSONObject obj = ary_artist.getJSONObject(i);
                    String id = obj.getString("id");
                    String image= obj.getString("image");
                    String name= obj.getString("name");
                    String fcount= obj.getString("fcount");


                    HomeDataArtists f1 = new HomeDataArtists();

                    f1.setId(id);
                    f1.setImage(image);
                    f1.setName(name);
                    f1.setFcount(fcount);


                    al_homearti.add(f1);

                }

                JSONArray ary_playlist1= main_obj.getJSONArray("playlists");

                for (int i = 0; i < ary_playlist1.length(); i++) {

                    JSONObject obj = ary_playlist1.getJSONObject(i);
                    String id = obj.getString("id");
                    String image= obj.getString("albumimage").replace(" ", "%20");
                    String name= obj.getString("albumname");
                    String tsong= obj.getString("totalsongs");

                    HomeDataPlaylist f2 = new HomeDataPlaylist();

                    f2.setId(id);
                    f2.setAlbumimage(image);
                    f2.setAlbumname(name);
                    f2.setTotalsongs(tsong);

                    al_homeplay.add(f2);

                }

                model_home.setAl_homenewreleases(al_homenewrel);
                model_home.setAl_homeartist(al_homearti);
                model_home.setAl_homeplaylist(al_homeplay);

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


        if(model_home.getAl_homeartist().isEmpty()){

            Fragment_Home1.grid3.setVisibility(View.GONE);
        }
        grid.setAdapter(new Adapter_HomeNewRelease(context,model_home));
        grid1.setAdapter(new Adapter_HomeFeatured(context,model_home));
        grid2.setAdapter(new Adapter_SubscribedHome(context,model_home));

    }

}
