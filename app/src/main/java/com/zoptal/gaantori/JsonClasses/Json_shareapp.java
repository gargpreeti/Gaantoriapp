package com.zoptal.gaantori.JsonClasses;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.gaantori.url.RegisterUrl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Json_shareapp extends AsyncTask<String, String, String> {

    	Context context;
       public static String userlink;

	public Json_shareapp(Context context) {
		// TODO Auto-generated constructor stub

		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				RegisterUrl.shareapp);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);

				JSONObject main_obj = new JSONObject(result);
				//JSONObject data = main_obj.getJSONObject("data");

				 userlink= main_obj.getString("appurl");



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



	}

}
