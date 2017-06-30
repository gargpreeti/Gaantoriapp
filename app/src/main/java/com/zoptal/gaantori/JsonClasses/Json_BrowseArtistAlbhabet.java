package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import com.zoptal.gaantori.Adapter.Adapter_AritistBrowse;
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


public class Json_BrowseArtistAlbhabet extends AsyncTask<String, String, String> {

     ProgressDialog loading;
     public static Model_BrowseArtist model_browseArtist;
     Context context;

     GridView grid;

    public Json_BrowseArtistAlbhabet(Context context, GridView grid) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.grid=grid;
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
                RegisterUrl.allartistsalphabet);
        try {
            // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("user_id",params[0]));
             nameValuePairs.add(new BasicNameValuePair("page_number",params[1]));
             nameValuePairs.add(new BasicNameValuePair("alphabet",params[2]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);


                model_browseArtist = new Model_BrowseArtist();

                ArrayList<BrowseArtist> browseArtists = new ArrayList<BrowseArtist>();

                JSONArray ary_products =new JSONArray(result);

                for (int i = 0; i < ary_products.length(); i++) {

                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String image = obj.getString("image").replace(" ", "%20");
                    String name = obj.getString("name");
                    String desc = obj.getString("desc");
                    String fol = obj.getString("fol");
                    String fcount= obj.getString("fcount");


                    BrowseArtist f = new BrowseArtist();
                    f.setId(id);
                    f.setImage(image);
                    f.setName(name);
                    f.setDesc(desc);
                    f.setFol(fol);
                    f.setFcount(fcount);

                    browseArtists.add(f);

                }


                model_browseArtist.setAl_browseartist(browseArtists);

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

        grid.setAdapter(new Adapter_AritistBrowse(context,model_browseArtist));


    }

}
