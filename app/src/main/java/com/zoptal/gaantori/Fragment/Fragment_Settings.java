package com.zoptal.gaantori.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.login.LoginManager;
import com.zoptal.gaantori.JsonClasses.Json_ProfileData;
import com.zoptal.gaantori.JsonClasses.Json_shareapp;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.LoginSignup;
import com.zoptal.gaantori.main.MainActivity1;

public class  Fragment_Settings extends Fragment  implements View.OnClickListener {

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    public static final String Tokn = "toknKey";
    public static final String Username = "usernameKey";
    public static final String Email = "emailKey";
    public static final String Usertype = "usertype";

    public static final String ModeStatus = "modeStatus";
    String mstatus;
    String tokn = "toknKey";
    RelativeLayout rel_public,rel_edit,rel_privacy,rel_terms,rel_upgrade,rel_share,rel_logout;
    ToggleButton tg1;
    MainActivity1 activity1;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

       view = inflater.inflate(R.layout.activity_settings, container, false); //inflate view

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        activity1 = (MainActivity1) getActivity();
        sharedpreferences1 =getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        MainActivity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(MainActivity1.drawable);
        MainActivity1.tv_bck.setVisibility(View.INVISIBLE);
        MainActivity1.img_srch.setVisibility(View.VISIBLE);


        MainActivity1.img_srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity1.ed_srch.setVisibility(View.VISIBLE);
                MainActivity1.textToolHeader.setVisibility(View.INVISIBLE);
                MainActivity1.img_srch.setVisibility(View.INVISIBLE);

            }
        });

        MainActivity1.ed_srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    Fragment_Srch fragment_srch = new Fragment_Srch();
                    FragmentManager fragmentManager1 =getFragmentManager();
                    fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_srch).commit();
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }
                return false;
            }

        });


        MainActivity1.textToolHeader.setVisibility(View.VISIBLE);
        MainActivity1.ed_srch.setVisibility(View.INVISIBLE);


        rel_public=(RelativeLayout)view.findViewById(R.id.rel_public);
        rel_edit=(RelativeLayout)view.findViewById(R.id.rel_edit);
        rel_privacy=(RelativeLayout)view.findViewById(R.id.rel_privacy);
        rel_terms=(RelativeLayout)view.findViewById(R.id.rel_terms);
        rel_upgrade=(RelativeLayout)view.findViewById(R.id.rel_upgrade);
        rel_share=(RelativeLayout)view.findViewById(R.id.rel_share);
        rel_logout=(RelativeLayout)view.findViewById(R.id.rel_logout);

        if(Fragment_Home1.user_id.equals("null")){

            rel_logout.setVisibility(View.INVISIBLE);

        }
    else{
            rel_logout.setVisibility(View.VISIBLE);
        }

        rel_public.setOnClickListener(this);
        rel_edit.setOnClickListener(this);
        rel_privacy.setOnClickListener(this);
        rel_terms.setOnClickListener(this);
        rel_upgrade.setOnClickListener(this);
        rel_share.setOnClickListener(this);
        rel_logout.setOnClickListener(this);

        tg1=(ToggleButton)view.findViewById(R.id.toggleButton);


        mstatus=sharedpreferences1.getString(ModeStatus, "");
        if(mstatus.equals("publicon")){

            tg1.setBackgroundResource(R.mipmap.activetg);
            tg1.setText("ON");
            tg1.setChecked(true);

        }
        else if(mstatus.equals("privateon")){
            tg1.setBackgroundResource(R.mipmap.disable);
            tg1.setText("Off");
            tg1.setChecked(false);
        }

        else
        {
            tg1.setBackgroundResource(R.mipmap.disable);
            mstatus="privateon";
            tg1.setText("Off");
            tg1.setChecked(false);
        }

        tg1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tg1.setBackgroundResource(R.mipmap.activetg);


                    mstatus="publicon";

                    SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                    editor1.putString(ModeStatus,mstatus);
                    editor1.commit();
                    tg1.setChecked(true);

                    if(MainActivity1.email1 == null || MainActivity1.email1.isEmpty()) {
                        if(MainActivity1.fbchk.equals("Signed Out")) {
                            createDialog();
                        }
                    }
                    // The toggle is enabled
                } else {

                    tg1.setBackgroundResource(R.mipmap.disable);

                    mstatus="privateon";
                    SharedPreferences.Editor editor1 = sharedpreferences1.edit();

                    editor1.putString(ModeStatus,mstatus);
                    editor1.commit();
                    tg1.setChecked(false);
                    // The toggle is disabled
                }
            }
        });




        return view;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rel_public:


                break;

            // To edit user profile
            case R.id.rel_edit:
                if(Fragment_Home1.user_id.equals("null")){


                    final Toast toast = Toast.makeText(getActivity(),"Please login to edit profile data",Toast.LENGTH_SHORT);
                    toast.show();

                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();


                }
              else {

                    if (NetworkConnection.isConnectedToInternet(getActivity())) {

                        new Json_ProfileData(getActivity()).execute(Fragment_Home1.user_id);

                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                    }

                }

                break;

            //For privacy policy
            case R.id.rel_privacy:


                if (NetworkConnection.isConnectedToInternet(getActivity())) {

                    FragmentManager fragmentManager1 = getFragmentManager();
                    Fragment_Privacy fragment_privacy= new Fragment_Privacy();
                    fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_privacy).commit();


                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }


                break;

            //For terms and conditions
            case R.id.rel_terms:


                if (NetworkConnection.isConnectedToInternet(getActivity())) {


                    FragmentManager fragmentManager2 = getFragmentManager();
                    Fragment_Terms fragment_terms = new Fragment_Terms();
                    fragmentManager2.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_terms).commit();


                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }


                break;

            //For upgrade
            case R.id.rel_upgrade:

                if(Fragment_Home1.user_id.equals("null")){
                    final Toast toast = Toast.makeText(getActivity(),"Please login to upgarde membership",Toast.LENGTH_SHORT);
                    toast.show();

                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();
                }
else {
                    FragmentManager fragmentManager3 = getFragmentManager();
                    Fragment_Upgrade fragment_upgrade = new Fragment_Upgrade();
                    fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_upgrade).commit();
                }
                break;

//To share on facebook
            case R.id.rel_share:

                if (NetworkConnection.isConnectedToInternet(getActivity())) {

                    shareIt();

                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();

                }

                break;

//To logout
            case R.id.rel_logout:

                LoginManager.getInstance().logOut();

                SharedPreferences.Editor editor1 = sharedpreferences1.edit();

                editor1.putString(Tokn,"null");
                editor1.putString(Username,"");
                editor1.putString(Email,"");
                editor1.putString(Usertype,"Basic");

                editor1.commit();


                FragmentManager fragmentManager4 = getFragmentManager();
                Fragment_Home1 fragment_home = new Fragment_Home1();
                fragmentManager4.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_home).commit();


            //    rateApp();
                break;

        }
    }

     private void shareIt() {

       //sharing implementation here

         try
         {
             // Check if the facebook app is installed on the phone.

             Log.e("sharelink===",""+Json_shareapp.userlink);

             Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
             sharingIntent.setType("text/plain");
             sharingIntent.setPackage("com.facebook.katana");
             sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,Json_shareapp.userlink);
             sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,Json_shareapp.userlink);
             startActivity(Intent.createChooser(sharingIntent, "Share via"));

         }
         catch (Exception e)
         {
             Toast.makeText(getActivity(),"Facebook have not been installed on this device",Toast.LENGTH_LONG).show();

         }

    }

    public void createDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage((Html.fromHtml("<font color='#000000'>" + "Connect with facebook account")));
        builder.setCancelable(false);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

           Intent intent = new Intent(getActivity(),LoginSignup.class);
            startActivity(intent);

            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.WHITE);
        pbutton.setTextColor(Color.BLACK);
    }

    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)

    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url,getActivity().getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

}