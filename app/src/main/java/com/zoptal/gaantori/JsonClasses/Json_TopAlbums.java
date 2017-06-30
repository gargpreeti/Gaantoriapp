package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.gaantori.Adapter.Adapter_TopAlbums;
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


public class Json_TopAlbums extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    Context context;
    public static Model_Top model_top=new Model_Top();
    public Adapter_TopAlbums adapter_topAlbums;

    public Json_TopAlbums(Context context, Adapter_TopAlbums adapter_topAlbums) {
        // TODO Auto-generated constructor stub
        this.adapter_topAlbums = adapter_topAlbums;
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
                RegisterUrl.top_albums);


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


                model_top = new Model_Top();

                ArrayList<NewRelease> newrel = new ArrayList<NewRelease>();

                JSONArray ary_products =new JSONArray(result);

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String image = obj.getString("image").replace(" ", "%20");
                    String name = obj.getString("name");

                  NewRelease f = new NewRelease();
                    f.setId(id);
                    f.setImage(image);
                    f.setName(name);

                    newrel.add(f);

                }

                model_top.setAl_newrelease(newrel);


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


        ArrayList<NewRelease> newReleasesArrayList=new ArrayList<>();
        ArrayList<NewRelease> newReleaseslist= adapter_topAlbums.getCurrentList();

        for (int i = 0; i <model_top.getAl_newrelease().size(); i++) {
            boolean check=true;
            for (int j = 0; j< newReleaseslist.size() ; j++) {

                if (newReleaseslist.get(j).getId().equalsIgnoreCase(model_top.getAl_newrelease().get(i).getId()) )
                {
                    check=false;
                }
            }
            if(check)
            {
                newReleasesArrayList.add(model_top.getAl_newrelease().get(i));
            }
        }
        adapter_topAlbums.setAddList(newReleasesArrayList);


    }

}
