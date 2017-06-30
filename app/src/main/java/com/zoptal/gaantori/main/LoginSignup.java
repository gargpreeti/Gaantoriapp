package com.zoptal.gaantori.main;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zoptal.gaantori.Fragment.Fragment_Login;
import com.zoptal.gaantori.Fragment.Fragment_Signup;
import com.zoptal.gaantori.JsonClasses.Json_Fblogin;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginSignup extends AppCompatActivity implements View.OnClickListener {

    ImageView img_crs;
    RelativeLayout rel_login,rel_signup;
    LoginButton btn_fb;
    CallbackManager callbackManager;


    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;

    public static final String Tokn = "toknKey";
    public static final String Username = "usernameKey";
    public static final String Username1 = "usernameKey1";
    public static final String Email = "emailKey";
    public static final String Usertype = "usertype";


     public static   String emailaddress; // to store email address
     ImageView  img_nxt;
    EditText ed_email;

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        //facebook sdk inilization
        facebookSDKInitialize();
        setContentView(R.layout.activity_loginsignup);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        img_crs=(ImageView)findViewById(R.id.crs);
        rel_login=(RelativeLayout)findViewById(R.id.rel_login);
        rel_signup=(RelativeLayout)findViewById(R.id.rel_signup);
        btn_fb=(LoginButton)findViewById(R.id.connectWithFbButton);
        btn_fb.setPublishPermissions(Arrays.asList("publish_actions"));

        img_nxt=(ImageView)findViewById(R.id.img_nxt);
        ed_email=(EditText)findViewById(R.id.ed_email);


        sharedpreferences1 =getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE); //to store in shared preferences

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                      if (currentAccessToken == null){
                    //User logged out
                    LoginManager.getInstance().logOut();


                    AccessToken.setCurrentAccessToken((AccessToken)null);
                    Profile.setCurrentProfile((Profile)null);
                }
            }
        };

        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getLoginDetails(btn_fb);
            }
        });

        img_crs.setOnClickListener(this);
        rel_login.setOnClickListener(this);
        rel_signup.setOnClickListener(this);
        img_nxt.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onClick(View v) {

       switch (v.getId()) {

           case R.id.img_nxt:
                emailaddress=ed_email.getText().toString().trim();
                if (NetworkConnection.isConnectedToInternet(LoginSignup.this)) {
                   FragmentManager fragmentManager = getFragmentManager();
                   Fragment_Login fragment_edit= new Fragment_Login();
                   fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_edit).commit();
               }

               else {
                   Toast.makeText(LoginSignup.this,"Please Check your internet connection", Toast.LENGTH_SHORT).show();
                   return;

               }


               break;


            case R.id.crs:
                Intent i=new Intent(LoginSignup.this,MainActivity1.class);
                startActivity(i);
                finish();
                break;

            case R.id.rel_login:

                if (NetworkConnection.isConnectedToInternet(LoginSignup.this)) {
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment_Login fragment_edit= new Fragment_Login();
                    fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_edit).commit();
                }

                else {
                    Toast.makeText(LoginSignup.this,"Please Check your internet connection", Toast.LENGTH_SHORT).show();
                 return;

                }

                break;

            case R.id.rel_signup:

                if (NetworkConnection.isConnectedToInternet(LoginSignup.this)) {
                    FragmentManager fragmentManager1 = getFragmentManager();
                    Fragment_Signup fragment_signup= new Fragment_Signup();
                    fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_signup).commit();
                    break;

                }

                else {
                    Toast.makeText(LoginSignup.this,"Please Check your internet connection", Toast.LENGTH_SHORT).show();
                    return;

                }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }



    @Override
    protected void onResume() {
        super.onResume();
             // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
           AppEventsLogger.deactivateApp(this);
    }

    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    protected void getLoginDetails(LoginButton login_button){

        // Callback registration


               login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult login_result) {

                    Log.e("success----", "test");

                    LoginManager.getInstance().logInWithPublishPermissions(LoginSignup.this, Arrays.asList("publish_actions"));

                    LoginManager.getInstance().logInWithReadPermissions(LoginSignup.this,
                            Arrays.asList("email", "public_profile"));


                    GraphRequest request = GraphRequest.newMeRequest(
                            login_result.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {


                                        String email = response.getJSONObject().getString("email");
                                        String firstName = response.getJSONObject().getString("first_name");
                                        String lastName = response.getJSONObject().getString("last_name");
                                        String gender = response.getJSONObject().getString("gender");
                                        //  String bday= response.getJSONObject().getString("birthday");

                                        Profile profile = Profile.getCurrentProfile();
                                        try {
                                            String id = profile.getId();

                                        String link = profile.getLinkUri().toString();
                                        // Log.i("Link",link);
                                        if (Profile.getCurrentProfile()!=null)
                                        {
                                            // Log.e("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                                        }

                                        Log.e("Login" + "ID", id);
                                        Log.e("Login" + "Email", email);
                                        //  Log.e("Login" + "Bday", bday);
                                        Log.e("Login"+ "FirstName", firstName);
                                        Log.e("Login" + "LastName", lastName);
                                        Log.e("Login" + "Gender", gender);

                                        String utype="Basic";

                                        SharedPreferences.Editor editor1 = sharedpreferences1.edit();

                                        editor1.putString(Tokn,id);
                                        editor1.putString(Username,firstName);
                                        editor1.putString(Username1,lastName);
                                        editor1.putString(Email,email);
                                        editor1.putString(Usertype,utype);
                                        editor1.commit();
                                            Log.e("Response",response.toString());
                                        new Json_Fblogin(LoginSignup.this).execute("","",firstName,lastName,email,gender,id);
                                        }catch (NullPointerException e){}

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,email,first_name,last_name,gender");
                    request.setParameters(parameters);
                    request.executeAsync();
                    LoginManager.getInstance().registerCallback(callbackManager,
                            new FacebookCallback<LoginResult>() {
                                @Override
                                public void onSuccess(LoginResult loginResult) {
                                    Log.e("in sucesss","test");
                                    setFacebookData(loginResult);
                                }

                                @Override
                                public void onCancel() {
                                }

                                @Override
                                public void onError(FacebookException exception) {
                                }
                            });


                }

                @Override
                public void onCancel() {
                    // code for cancellation
                }

                @Override
                public void onError(FacebookException exception) {
                    //  code to handle error
                }
            });

    }

    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {


                           String email = response.getJSONObject().getString("email");
                           String firstName = response.getJSONObject().getString("first_name");
                           String lastName = response.getJSONObject().getString("last_name");
                           String gender = response.getJSONObject().getString("gender");
                        //  String bday= response.getJSONObject().getString("birthday");

                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();

                            String link = profile.getLinkUri().toString();
                           // Log.i("Link",link);
                            if (Profile.getCurrentProfile()!=null)
                            {
                               // Log.e("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                            }

                            Log.e("Login" + "ID", id);
                            Log.e("Login" + "Email", email);
                         //  Log.e("Login" + "Bday", bday);
                           Log.e("Login"+ "FirstName", firstName);
                           Log.e("Login" + "LastName", lastName);
                           Log.e("Login" + "Gender", gender);

                            String utype="Basic";

                            SharedPreferences.Editor editor1 = sharedpreferences1.edit();

                            editor1.putString(Tokn,id);
                            editor1.putString(Username,firstName);
                            editor1.putString(Email,email);
                            editor1.putString(Usertype,utype);
                            editor1.commit();
                            Log.e("Response..",response.toString());
                            new Json_Fblogin(LoginSignup.this).execute("","",firstName,lastName,email,gender,id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }

}
