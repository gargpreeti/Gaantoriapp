package com.zoptal.gaantori.JsonClasses;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.zoptal.gaantori.Fragment.Fragment_Login;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.InAppPaymentActivityMember;
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

import java.util.ArrayList;
import java.util.List;


public class Json_PaymentInApp extends AsyncTask<String, String, String> {

   public static String res="",res1;
    Context context;

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    public static final String Usertype = "usertype";
    public static final String Packagetype = "packagetype";

  String utype,ptype;
    public Json_PaymentInApp(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        sharedpreferences1 = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }
    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.inapppayment);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("do","Save"));
            nameValuePairs.add(new BasicNameValuePair("user_id",params[1]));
            nameValuePairs.add(new BasicNameValuePair("product_id",params[2]));
            nameValuePairs.add(new BasicNameValuePair("order_id",params[3]));
            nameValuePairs.add(new BasicNameValuePair("amount",params[4]));
            nameValuePairs.add(new BasicNameValuePair("type",params[5]));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);


                try {

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
         Toast.makeText(context, "Payment Successfull!", Toast.LENGTH_LONG).show();


        utype="Premium";
        ptype= InAppPaymentActivityMember.ptype;
        SharedPreferences.Editor editor1 =sharedpreferences1.edit();
        editor1.putString(Usertype,utype);
        editor1.putString(Packagetype,ptype);
        editor1.commit();

        Fragment_Login fragment_detail=new Fragment_Login();
        FragmentManager fragmentManager3 = ((Activity)context).getFragmentManager();
        fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();





    }

}
