package com.zoptal.gaantori.JsonClasses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.zoptal.gaantori.Fragment.Fragment_EditProfile;
import com.zoptal.gaantori.R;
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


public class Json_ProfileData extends AsyncTask<String, String, String> {

     ProgressDialog loading;
     Context context;
    public  static Fragment_EditProfile f;
    ModelProfileData obj_profiledata;

       public Json_ProfileData(Context context) {
        // TODO Auto-generated constructor stub
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
                RegisterUrl.profiledata);


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
                JSONArray ary_products =main_obj.getJSONArray("user");


                for (int i = 0; i < ary_products.length(); i++) {

                    JSONObject obj = ary_products.getJSONObject(i);

                    String id = obj.getString("id");
                    String fname= obj.getString("first_name");
                    String lname = obj.getString("last_name");
                    String uname= obj.getString("username");
                    String gendr= obj.getString("gender");
                    String email= obj.getString("email");
                    String city= obj.getString("city");
                    String dob= obj.getString("date_of_birth");
                    String utype= obj.getString("type");

                    obj_profiledata = new ModelProfileData();
                    obj_profiledata.setId(id);
                    obj_profiledata.setFirstname(fname);
                    obj_profiledata.setLastname(lname);
                    obj_profiledata.setUsername(uname);
                    obj_profiledata.setGender(gendr);
                    obj_profiledata.setEmail(email);
                    obj_profiledata.setCity(city);
                    obj_profiledata.setDob(dob);
                    obj_profiledata.setUtype(utype);



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

        Bundle bundle = new Bundle();
        bundle.putSerializable("profiledata", obj_profiledata);

        f= new Fragment_EditProfile();
        android.app.FragmentManager fragmentManager4 = ((Activity) context).getFragmentManager();
        f.setArguments(bundle);
        fragmentManager4.beginTransaction().replace(R.id.activity_main_content_fragment, f).commit();



   }

}
