package com.zoptal.gaantori.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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


public class Json_CancelMembership extends AsyncTask<String, String, String> {

   public static String res="",res1,msg;
    Context context;
    ProgressDialog loading;
    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    public static final String Usertype = "usertype";
    public static final String Packagetype = "packagetype";
    String utype;

    public Json_CancelMembership(Context context) {
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
                RegisterUrl.cncl_membership);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("submit","Cancel EMI"));
            nameValuePairs.add(new BasicNameValuePair("user_id",params[1]));
            nameValuePairs.add(new BasicNameValuePair("comment",params[2]));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);

              JSONObject main_obj = new JSONObject(result1);

                try {

                    res1 = main_obj.optString("result");
                    msg= main_obj.optString("message");

                }
                catch (Exception e){}

            }


        } catch (Exception e) {
            Log.e("==+result membership===", "======" + e);

        }


        return res1;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

        if(res1.equals("true")) {
            String utype = "Basic";
            String ptype="F";

            SharedPreferences.Editor editor1 = sharedpreferences1.edit();
            editor1.putString(Usertype, utype);
            editor1.putString(Packagetype,ptype);
            editor1.commit();
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

        }
    else {
           Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

        }

    }

}
