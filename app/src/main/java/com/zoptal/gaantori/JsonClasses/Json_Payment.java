package com.zoptal.gaantori.JsonClasses;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.gaantori.Fragment.Fragment_Payment;
import com.zoptal.gaantori.R;

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


public class Json_Payment extends AsyncTask<String, String, String> {

   public static String res="",res1,successurl,failure_url;
    Context context;
    ProgressDialog loading;

    public Json_Payment(Context context) {
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
                "http://www.gaantori.com/app/webroot/android/recurring-payment/expresscheckout.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("user_id",params[0]));
            nameValuePairs.add(new BasicNameValuePair("amt",params[1]));
            nameValuePairs.add(new BasicNameValuePair("type",params[2]));



            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);


                JSONObject main_obj = new JSONObject(result1);

                try {
                    res = main_obj.optString("url");
                    successurl=main_obj.optString("success_url");
                    failure_url=main_obj.optString("failure_url");
                    res1 = main_obj.optString("result");

                }
                catch (Exception e){}
                Log.e("payment==========",""+result1);

            }


        } catch (Exception e) {
            Log.e("==+result payment===", "======" + e);

        }


        return res1;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();


        if(res1.equals("true")){


            Fragment_Payment fragment_detail=new Fragment_Payment();
            FragmentManager fragmentManager3 = ((Activity)context).getFragmentManager();
            fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();

        }

    }

}
