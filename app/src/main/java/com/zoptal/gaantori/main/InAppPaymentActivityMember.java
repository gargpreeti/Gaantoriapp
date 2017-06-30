package com.zoptal.gaantori.main;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.JsonClasses.Json_PaymentInApp;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class InAppPaymentActivityMember extends AppCompatActivity implements View.OnClickListener {

    Button btn_monthly;
    Button btn_yearly;
    private ImageView img_bck;
    public static String ptype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_payment);

        btn_monthly = (Button) findViewById(R.id.btn_monthly);
        btn_yearly = (Button) findViewById(R.id.btn_yearly);
        img_bck=(ImageView)findViewById(R.id.bck);

        btn_monthly.setOnClickListener(this);
        btn_yearly.setOnClickListener(this);
        img_bck.setOnClickListener(this);

        // In app billing
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

    }


    IInAppBillingService mService;
    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }


    public boolean isNetworkAvailable() {

        boolean connected = false;

            ConnectivityManager connectivityManager = (ConnectivityManager) InAppPaymentActivityMember.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            } else
                connected = false;

        return connected;
    }


    public void buy(String product_id) {
//        Toast.makeText(getApplicationContext(), product_info.getProId(), Toast.LENGTH_LONG).show();

        if (isNetworkAvailable()) {
            purchaseSubscription(product_id);
        } else {
            Toast.makeText(getApplicationContext(), "The Internet connection appears to be offline.", Toast.LENGTH_LONG).show();
        }

    }

    public void purchaseSubscription(final String purchaseId) {
        //for testing
        Log.e("purchase product Id:", purchaseId + "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String android_id = android.provider.Settings.Secure.getString(getContentResolver(),
                            android.provider.Settings.Secure.ANDROID_ID);
                    /* getBuyIntent(int apiVersion,
                                      String packageName,
                                      String sku,
                                      String type,
                                      String payload)*/


                    Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(), purchaseId, "inapp", android_id);
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                    startIntentSenderForResult(pendingIntent.getIntentSender(),
                       1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
                            Integer.valueOf(0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.e("======", e + "");
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {

               try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String productId = jo.optString("productId");
                    String orderId = jo.optString("orderId");
                    String amount = "0";
                    String purchaseToken = jo.getString("purchaseToken");


                   //check for response product_Ids //
                  if (productId.contains("monthly")) {
                      //subscription_level = 10;
                    amount = "1";
                      ptype="M";

                      if (NetworkConnection.isConnectedToInternet(InAppPaymentActivityMember.this)) {

                          new Json_PaymentInApp(InAppPaymentActivityMember.this).execute("Save", Fragment_Home1.user_id, productId, orderId, amount, "Month");
                      }
                          else {
                          Toast.makeText(InAppPaymentActivityMember.this,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                      }





                  }
                  else if (productId.contains("yearly")) {
                     //subscription_level = 30;
                      amount = "10";
                      ptype="Y";

                      if (NetworkConnection.isConnectedToInternet(InAppPaymentActivityMember.this)) {

                          new Json_PaymentInApp(InAppPaymentActivityMember.this).execute("Save", Fragment_Home1.user_id,productId,orderId,amount,"Year");

                      }




                      else {
                          Toast.makeText(InAppPaymentActivityMember.this,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                      }

                               }
                   int response = mService.consumePurchase(3, getPackageName(), purchaseToken);

                   if(response==0){

                       Toast.makeText(InAppPaymentActivityMember.this,"You have already subscribed for this plan", Toast.LENGTH_LONG).show();

                   }
                    //int response = mService.consumePurchase(3, getPackageName(), purchaseToken);
//                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//                    params.add(new BasicNameValuePair("inapp_purchase_id", orderId));
//                    params.add(new BasicNameValuePair("platform", "android"));
//                    params.add(new BasicNameValuePair("amount_payed", amount));
//                    params.add(new BasicNameValuePair("subscription_level", subscription_level + ""));
//                    //save details
//                    if (amount.equalsIgnoreCase("0") || subscription_level == 0) {
//                        Toast.makeText(this, "Failed to purchase try again", Toast.LENGTH_LONG).show();
//                    } else {
//                        SendPaymentDetails(params);
//                    }
//                    Prefs.with(this).save("livesPurchase", subscriptionAdded);
                } catch (JSONException e) {
                    Toast.makeText(this, "Failed to purchase data.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

       switch (view.getId()) {

            case R.id.btn_monthly:
                buy("com.zoptal.gaantoriapp.monthly");
                break;

            case R.id.btn_yearly:
                buy("com.zoptal.gaantoriapp.yearly");
                break;

            case R.id.bck:

                finish();
               // Intent intent_bck=new Intent(InAppPaymentActivity.this,LoginSignup.class);
                //startActivity(intent_bck);

//                Fragment_Signup fragment_signup = new Fragment_Signup();
//                android.app.FragmentManager fragmentManager4 = getFragmentManager();
//                fragmentManager4.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_signup).commit();
//



                break;
        }
    }
}
