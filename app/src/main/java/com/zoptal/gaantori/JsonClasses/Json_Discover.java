package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.gaantori.Adapter.Adapter_Discover;
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


public class Json_Discover extends AsyncTask<String, String, String> {

    ProgressDialog loading;

    public static Model_Discover model_discover=new Model_Discover();

     Context context;
     public Adapter_Discover adapter_discover;

    public Json_Discover(Context context,Adapter_Discover adapter_discover) {
        // TODO Auto-generated constructor stub
        this.adapter_discover = adapter_discover;
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
                RegisterUrl.discover);


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
                String result1 = EntityUtils.toString(entity);


                model_discover = new Model_Discover();

                ArrayList<Discover> discover = new ArrayList<Discover>();

                JSONObject main_obj = new JSONObject(result1);
                JSONArray ary_products =main_obj.getJSONArray("result");


                for (int i = 0; i < ary_products.length(); i++) {

                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String image = obj.getString("image").replace(" ", "%20");
                    String albumtitle = obj.getString("albumtitle");
                    String artists = obj.getString("artists");
                    String singer= obj.getString("singer");


                    Discover f = new Discover();
                    f.setId(id);
                    f.setImage(image);
                    f.setAlbumtitle(albumtitle);
                    f.setArtists(artists);
                    f.setSinger(singer);

                    discover.add(f);

                }


                model_discover.setAl_discover(discover);


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


        ArrayList<Discover> discoverArrayList=new ArrayList<>();
        ArrayList<Discover> discoverlist= adapter_discover.getCurrentList();

        for (int i = 0; i <model_discover.getAl_discover().size(); i++) {
            boolean check=true;
            for (int j = 0; j< discoverlist.size() ; j++) {

                if (discoverlist.get(j).getId().equalsIgnoreCase(model_discover.getAl_discover().get(i).getId()) )
                {
                    check=false;
                }
            }
            if(check)
            {
                discoverArrayList.add(model_discover.getAl_discover().get(i));
            }
        }
        adapter_discover.setAddList(discoverArrayList);


    }

}
