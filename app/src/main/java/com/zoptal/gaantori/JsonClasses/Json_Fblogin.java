package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
import java.util.List;


public class Json_Fblogin extends AsyncTask<String, String, String> {

    String res="",sn,st,uname,uemail,id;
    Context context;
    String firstName,email,utype;
//
    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;

    public static final String Tokn = "toknKey";
    public static final String Username = "usernameKey";
    public static final String Email = "emailKey";
    public static final String Usertype = "usertype";

        ProgressDialog loading;

       public Json_Fblogin(Context context) {
        // TODO Auto-generated constructor stub

        this.context = context;


      sharedpreferences1 = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
                RegisterUrl.fblogin);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("username",""));
            nameValuePairs.add(new BasicNameValuePair("password",""));
            nameValuePairs.add(new BasicNameValuePair("first_name",params[2]));
            nameValuePairs.add(new BasicNameValuePair("last_name",params[3]));
            nameValuePairs.add(new BasicNameValuePair("email",params[4]));
            nameValuePairs.add(new BasicNameValuePair("gender",params[5]));
            nameValuePairs.add(new BasicNameValuePair("LoginID",params[6]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result1.trim());

                JSONArray ary_products =main_obj.getJSONArray("data");

                res=main_obj.getString("status");
                id=main_obj.getString("id");





                for (int i = 0; i < ary_products.length(); i++) {

                    JSONObject obj = ary_products.getJSONObject(i);

                     id = obj.getString("id");
                    String fname = obj.getString("first_name");
                    String lname = obj.getString("last_name");
                    String uname = obj.getString("username");
                    String gendr = obj.getString("gender");
                    String email = obj.getString("email");
                    String dob = obj.getString("date_of_birth");
                   utype = obj.getString("type");
                }



                SharedPreferences.Editor editor1 = sharedpreferences1.edit();

                editor1.putString(Tokn,id);

               editor1.putString(Usertype,utype);
                editor1.commit();


            }

        } catch (Exception e) {
            Log.e("==+result signup===", "======" + e);

        }


        return res;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();
        if(res.equals("1")){
            Toast.makeText(context, "Login Successfully!", Toast.LENGTH_LONG).show();
//
             Intent intent = new Intent(context,MainActivity1.class);
              context.startActivity(intent);

       }

    }

}
