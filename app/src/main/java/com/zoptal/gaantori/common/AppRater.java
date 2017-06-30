package com.zoptal.gaantori.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

/**
 * Created by zotal.102 on 07/04/17.
 */
public class AppRater {
    private final static String APP_TITLE = "GaantoriApp";// App Name
    private final static String APP_PNAME = "com.zoptal.gaantoriapp";// Package Name

    private final static int DAYS_UNTIL_PROMPT = 3;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 3;//Min number of launches

    public static final String MyPREFERENCES = "MyPrefs1" ;

    public static final String Apprate = "apprateKey";

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);

        if (prefs.getBoolean("dontshowagain", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext);
            }
        }

        editor.commit();
    }

    public static void showRateDialog(final Context mContext) {

        final SharedPreferences   sharedpreferences1 =mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Dialog dialog = new Dialog(mContext);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        String message = "If you enjoy using "
                + APP_TITLE
                + ", please take a moment to rate the app. "
                + "Thank you for your support!";

        builder.setMessage(message)
                .setTitle("Rate " + APP_TITLE)
                .setIcon(mContext.getApplicationInfo().icon)
                .setCancelable(false)
                .setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("clicked on----","rate");
                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                editor1.putString(Apprate,"true");
                editor1.commit();
//                  If your app hasn't been uploaded to market you'll get an exception.
//                  For test purposes we catch it here and show some text.
                        try {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + APP_PNAME)));
                        }catch(ActivityNotFoundException e) {

                        }

                        dialog.dismiss();
                    }
                })
                .setNeutralButton("Later", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("clicked on----","remind");
                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                editor1.putString(Apprate,"false");
                editor1.commit();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No, Thanks", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if(editor != null) {
//                            editor.putBoolean("dontshowagain", true);
//                            editor.commit();
//                        }

                        Log.e("clicked on----","no");
                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                editor1.putString(Apprate,"false");
                editor1.commit();
                        dialog.dismiss();
                    }
                });

        dialog = builder.create();
        dialog.show();




//        dialog.setTitle("Rate " + APP_TITLE);
//
//        LinearLayout l= new LinearLayout(mContext);
//        l.setOrientation(LinearLayout.VERTICAL);
//
//
//        LinearLayout ll = new LinearLayout(mContext);
//      //  ll.setOrientation(LinearLayout.VERTICAL);
//
//        TextView tv = new TextView(mContext);
//        tv.setText("If you enjoy using " + APP_TITLE + ", please take a moment to rate it. Thanks for your support!");
//        tv.setWidth(240);
//        tv.setPadding(4, 0, 4, 10);
//        ll.addView(tv);
//
//        LinearLayout l2 = new LinearLayout(mContext);
//        l2.setOrientation(LinearLayout.HORIZONTAL);
//
//        Button b1 = new Button(mContext);
//        b1.setText("Rate " + APP_TITLE);
//        b1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                Log.e("clicked on----","rate");
//                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
//                editor1.putString(Apprate,"true");
//                editor1.commit();
//                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
//                dialog.dismiss();
//            }
//        });
//        l2.addView(b1);
//
//        Button b2 = new Button(mContext);
//        b2.setText("Remind me later");
//        b2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                Log.e("clicked on----","remind");
//                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
//                editor1.putString(Apprate,"false");
//                editor1.commit();
//                dialog.dismiss();
//            }
//        });
//        l2.addView(b2);
//
//        Button b3 = new Button(mContext);
//        b3.setText("No, thanks");
//        b3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.e("clicked on----","nooo");
//                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
//                editor1.putString(Apprate,"false");
//                editor1.commit();
////                if (editor != null) {
////                    editor.putBoolean("dontshowagain", true);
////                    editor.commit();
////                }
//                dialog.dismiss();
//            }
//        });
//        l2.addView(b3);
//
//        l.addView(ll);
//        l.addView(l2);
//        dialog.setContentView(l);
//
//        dialog.show();
    }
}