package com.zoptal.gaantori .JsonClasses;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.zoptal.gaantori.Fragment.Fragment_Login;
import com.zoptal.gaantori.Fragment.Fragment_PaymentMethod1;
import com.zoptal.gaantori.Fragment.Fragment_Signup;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_SignUp extends AsyncTask<String, String, String> {

    String res="",sn,st,uname,uemail;
    Context context;
    public static String id;
    String msg;
    ProgressDialog loading;

       public Json_SignUp(Context context) {
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
                RegisterUrl.signup);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("username",params[0]));
            nameValuePairs.add(new BasicNameValuePair("password",params[1]));
            nameValuePairs.add(new BasicNameValuePair("first_name",params[2]));
            nameValuePairs.add(new BasicNameValuePair("last_name",params[3]));
            nameValuePairs.add(new BasicNameValuePair("email",params[4]));
            nameValuePairs.add(new BasicNameValuePair("gender",params[5]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result1);

                res=main_obj.getString("status");
                 msg= main_obj.getString("message");

                id=main_obj.getString("id");


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
        if (res.equals("1")) {

            Toast.makeText(context, "User Added Successfully!", Toast.LENGTH_LONG).show();


            if (Fragment_Signup.type_value.trim().equals("Free")) {

                Fragment_Login fragment_detail = new Fragment_Login();
                FragmentManager fragmentManager3 = ((Activity) context).getFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();
            }

            if (Fragment_Signup.type_value.trim().equals("Premium")) {

                Fragment_PaymentMethod1 fragment_detail = new Fragment_PaymentMethod1();
                FragmentManager fragmentManager3 = ((Activity) context).getFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();

            }

        }
        else {

                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

     }

    }}
