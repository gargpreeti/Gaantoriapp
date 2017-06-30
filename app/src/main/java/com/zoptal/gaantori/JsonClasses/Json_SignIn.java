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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class Json_SignIn extends AsyncTask<String, String, String> {

    String res="",sn,uname,uemail,utype,ptype,msg;
    Context context;
    String id,type;

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;

    public static final String Tokn = "toknKey";
    public static final String Username = "usernameKey";
    public static final String Email = "emailKey";
    public static final String Usertype = "usertype";
    public static final String Packagetype = "packagetype";


       ProgressDialog loading;

       public Json_SignIn(Context context) {
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
                RegisterUrl.signin);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("email", params[0]));
            nameValuePairs.add(new BasicNameValuePair("password", params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);

                Log.e("user signin---",""+result1);

                JSONObject main_obj = new JSONObject(result1);
              String status=main_obj.getString("status");
                if(status.equals("0")) {
                    msg = main_obj.getString("message");
                }
                JSONObject  obj=main_obj.getJSONObject("User");


                id =obj.getString("id");


                uname=obj.getString("username");
                uemail=obj.getString("email");
                utype=obj.getString("type");
                ptype=obj.getString("period");



                res=main_obj.getString("status");


               SharedPreferences.Editor editor1 = sharedpreferences1.edit();

                editor1.putString(Tokn,id);
                editor1.putString(Username,uname);
                editor1.putString(Email,uemail);
                editor1.putString(Usertype,utype);
                editor1.putString(Packagetype,ptype);
                editor1.commit();


            }

        } catch (Exception e) {
            Log.e("==+result signin===", "======" + e);

        }


        return res;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();


        if(res.equals("1")) {

            Intent i=new Intent(context,MainActivity1.class);
            context.startActivity(i);
        }

        else{

            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

        }

    }

}
