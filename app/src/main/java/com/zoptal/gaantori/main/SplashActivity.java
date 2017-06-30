package com.zoptal.gaantori.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.zoptal.gaantori.R;

import io.fabric.sdk.android.Fabric;



 public class SplashActivity extends AppCompatActivity  {

     String MyPREFERENCES = "MyPrefs1";
     SharedPreferences sharedpreferences1;
     public static String usertokn="null",username1,email1,utype1="";
     String tokn = "toknKey";
     String username="usernameKey";
     String email="emailKey";
     String utyp="usertype";

     public static String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
       // Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        Thread t1=new Thread()
        {
            public void run()
            {
                try{

                 Thread.sleep(2000);


                    sharedpreferences1 =getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                    if (sharedpreferences1.contains(tokn)) {
                        usertokn = sharedpreferences1.getString(tokn, "");
                        username1= sharedpreferences1.getString(username, "");
                        email1= sharedpreferences1.getString(email, "");
                        utype1= sharedpreferences1.getString(utyp, "");
                    }



                    user_id=usertokn;
                    if(user_id.equals("null")) {
                       // Intent i = new Intent(SplashActivity.this,LoginSignup.class);
                        Intent i=new Intent(SplashActivity.this,LoginSignup.class);
                        startActivity(i);
                        finish();
                    }
                    else{

                        Intent i = new Intent(SplashActivity.this,MainActivity1.class);
                        startActivity(i);
                        finish();


                    }
                }
                catch(Exception e)
                {

                }

            }


        };
        t1.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.



        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
