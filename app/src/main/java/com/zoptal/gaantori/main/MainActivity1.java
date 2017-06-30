package com.zoptal.gaantori.main;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Adapter.Adapter_AlbumDetail;
import com.zoptal.gaantori.Adapter.Adapter_LibrarySong;
import com.zoptal.gaantori.Adapter.Adapter_MyPlaylistdetail;
import com.zoptal.gaantori.Adapter.Adapter_Playlistdetail;
import com.zoptal.gaantori.Adapter.Adapter_StaredSong;
import com.zoptal.gaantori.Adapter.Adapter_TopSongs;
import com.zoptal.gaantori.Adapter.CustomAdapter;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.Fragment.Fragment_MyMusic;
import com.zoptal.gaantori.JsonClasses.Json_AdService;
import com.zoptal.gaantori.JsonClasses.Json_ProfileData;
import com.zoptal.gaantori.JsonClasses.Json_SongPlayCount;
import com.zoptal.gaantori.JsonClasses.Json_SongPopup;
import com.zoptal.gaantori.JsonClasses.Json_albumdetailAll;
import com.zoptal.gaantori.JsonClasses.Json_artistdetailAll;
import com.zoptal.gaantori.JsonClasses.TopSongs;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.AppRater;
import com.zoptal.gaantori.common.NetworkConnection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.Fabric;


public class MainActivity1 extends AppCompatActivity {

    ShareDialog shareDialog;  // share dialog to post on facebook

    //Declration for media player controller
    boolean small = true;
    ImageView img_song;
    TextView tv_songname;
    ImageView img_play;
    ImageView img_pause;
    ImageView img_bckwrd;
    ImageView img_forwed;
    ToggleButton img_repeat;
    ToggleButton img_shuffl;
    ImageView img_cros, img_cross1;
    TextView tv_duration;
    TextView tv_durationremain;

    ImageView img_song1;
    TextView tv_albumname1;
    TextView tv_songname1;
    //TextView tv_singername1;
    ListView listSinger;
    ImageView img_play1;
    ImageView img_pause1;
    ImageView img_bckwrd1;
    ImageView img_forwed1;
    ToggleButton img_repeat1;
    ToggleButton img_shuffl1;
    ImageView img_cross11;
    TextView tv_duration1;
    TextView tv_durationremain1;
    public static TextView txt_playqueue;
    int currentSongDuration = 0;
    int currentSong70 = 0;

    Context context;
    public static MediaPlayer mp; //media player declration
    public TextView songName, duration;
    private double timeElapsed = 0, finalTime = 0;
    private int forwardTime = 5000, backwardTime = 5000;
    private Handler durationHandler = new Handler();
    private SeekBar seekbar, seekbar1;
    //  int count = 0;
    int queueCount = 0;
    int position1 = 0;

    private ProgressDialog progressDialog;

    public ArrayList<TopSongs> al_topsong;
    ArrayList<TopSongs> queueList;

    public LinearLayout linear_small, linear_large;
    String MyPREFERENCES = "MyPrefs1";
    SharedPreferences sharedpreferences1;
    public static String usertokn = "null", username1 = "", usernamelst = "", email1, utype1 = "", ptype1,rateapp="false";
    public static String user_id;
    String tokn = "toknKey";
    String username = "usernameKey";
    String username11 = "usernameKey1";
    String email = "emailKey";
    String utyp = "usertype";
    String ptyp = "packagetype";
    String apprate = "apprateKey";
    public static final String ModeStatus = "modeStatus";
    String mstatus;

    String TITLES[] = {"Home", "Browse", "New Release", "Top Lists", "Featured Playlists", "My Music", "Discover", "Follow", "Choose Membership", "Settings"};
    int ICONS[] = {R.mipmap.home, R.mipmap.browse, R.mipmap.new_release, R.mipmap.top, R.mipmap.featured, R.mipmap.mymusic, R.mipmap.discover, R.mipmap.follow, R.mipmap.chhosedmember, R.mipmap.setting};

    public static TextView tv_bck;
    public static TextView textToolHeader;
    public static TextView tv_main, tv_name;
    Toolbar toolbar;                              // Declaring the Toolbar Object
    public static Drawable drawable;
    public static RecyclerView mRecyclerView;                           // Declaring RecyclerView

    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager

    public static DrawerLayout Drawer;
    public static ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle
    String srchtext;
    public static ImageView img_bck, img_srch, img_cross;
    public static EditText ed_srch;
    CallbackManager callbackManager;
    public LoginManager loginManager;
    public static String fbchk;
    AdView mAdView;
    int counter = 0;
    int songcounter=0;
    MediaPlayer mpads;
    boolean queueEnable = false;
    Dialog dialog1;
    CustomAdapter adapter;

    boolean offline = false;
    public String playingTAG = "";
    public int playingPosition = -1;
    public Object objectAdapter;
    public List<String> sample;
    public List<String> sample1;

    public String artistname, artistid;

    public static LinearLayout linr_offlinemsg;
    TextView tv_offline;
    public static String netst;


    final Handler handlerd = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main4);   //  define a custom screen layout here

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


      //  AppRater.showRateDialog(MainActivity1.this);

//        AppRater.setPackageName("com.johncrossley");
//        AppRater.app_launched(this);

        linr_offlinemsg = (LinearLayout) findViewById(R.id.linr_offlinemsg);
        tv_offline = (TextView) findViewById(R.id.tv_offline);

        //Broadcast receiver to check internet connection
        IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = intent.getBooleanExtra(NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "connected" : "disconnected";

                netst = networkStatus;
                Toast.makeText(MainActivity1.this, networkStatus, Toast.LENGTH_SHORT).show();

                if (networkStatus.equals("connected")) {
                    linr_offlinemsg.setVisibility(View.GONE);
                } else {

                    linr_offlinemsg.setVisibility(View.VISIBLE);
                }
            }
        }, intentFilter);

        Intent intent = new Intent();
        intent.setAction("com.zoptal.gaantori.main.CallReceiver");
        sendBroadcast(intent);

//
//        CallReceiver listener = new CallReceiver(MainActivity1.this,);
//        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if(tManager != null)
//            tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        shareDialog = new ShareDialog(MainActivity1.this);
        //To save offline songs
        tv_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (utype1.equals("Premium")) {

                    Fragment_MyMusic fragment_myMusic = new Fragment_MyMusic();
                    FragmentManager fragmentManager4 = getFragmentManager();
                    fragmentManager4.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_myMusic).commit();

                }
                else {

                    final Toast toast = Toast.makeText(MainActivity1.this, "Please Register to enjoy Premium Functions", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }

                        public void onFinish() {
                            toast.cancel();
                        }
                    }.start();
                }
            }
        });

//To set login username
        tv_name = (TextView) findViewById(R.id.name);
        img_bck = (ImageView) findViewById(R.id.bckimg);

        //To store data in loacl database
        sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        rateapp=sharedpreferences1.getString(apprate,"");

        if (sharedpreferences1.contains(tokn)) {
            usertokn = sharedpreferences1.getString(tokn, "");
            username1 = sharedpreferences1.getString(username, "");
            usernamelst = sharedpreferences1.getString(username11, "");
            email1 = sharedpreferences1.getString(email, "");

            utype1 = sharedpreferences1.getString(utyp, "");
            ptype1 = sharedpreferences1.getString(ptyp, "");

        }
        user_id = usertokn;

     //   Log.e("main utype1",""+utype1);
        Log.e("rateappp",""+rateapp);

                   if(rateapp.equals("true")){

                   }
                else {
                     // AppRater.showRateDialog(this);
                   }

        if (NetworkConnection.isConnectedToInternet(MainActivity1.this)) {
            new Json_AdService(this).execute();

        } else {
            //just toast it
            Toast.makeText(getApplicationContext(), "Please Check your internet connection", Toast.LENGTH_SHORT).show();

        }

        linear_small = (LinearLayout) findViewById(R.id.song_smallscreen);
        linear_large = (LinearLayout) findViewById(R.id.song_fullscreen);


        if (NetworkConnection.isConnectedToInternet(getApplicationContext())) {
            linr_offlinemsg.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Check your internet connection", Toast.LENGTH_SHORT).show();

            linr_offlinemsg.setVisibility(View.VISIBLE);

        }
//finding view id
        tv_songname = (TextView) findViewById(R.id.tv_songname);
        img_play = (ImageView) findViewById(R.id.img_play);
        img_pause = (ImageView) findViewById(R.id.img_pause);
        img_bckwrd = (ImageView) findViewById(R.id.img_bck);
        img_forwed = (ImageView) findViewById(R.id.img_forwed);
        img_repeat = (ToggleButton) findViewById(R.id.img_repeat);
        img_shuffl = (ToggleButton) findViewById(R.id.img_shuffl);
        img_cross = (ImageView) findViewById(R.id.img_cross);
        img_cross1 = (ImageView) findViewById(R.id.img_cross1);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        tv_duration = (TextView) findViewById(R.id.tv_duration);
        tv_durationremain = (TextView) findViewById(R.id.tv_durationremain);

        tv_songname1 = (TextView) findViewById(R.id.tv_songname1);
        img_play1 = (ImageView) findViewById(R.id.img_play1);
        img_pause1 = (ImageView) findViewById(R.id.img_pause1);
        img_bckwrd1 = (ImageView) findViewById(R.id.img_bck1);
        img_forwed1 = (ImageView) findViewById(R.id.img_forwed1);
        img_repeat1 = (ToggleButton) findViewById(R.id.img_repeat1);
        img_shuffl1 = (ToggleButton) findViewById(R.id.img_shuffl1);
        img_cross11 = (ImageView) findViewById(R.id.img_cross11);
        txt_playqueue = (TextView) findViewById(R.id.txt_playqueue);
        img_song1 = (ImageView) findViewById(R.id.img_song1);

        listSinger = (ListView) findViewById(R.id.listSinger);

        tv_albumname1 = (TextView) findViewById(R.id.tv_albumname1);

        seekbar1 = (SeekBar) findViewById(R.id.seekBar1);
        tv_duration1 = (TextView) findViewById(R.id.tv_duration1);
        tv_durationremain1 = (TextView) findViewById(R.id.tv_durationremain1);

        seekbar1.setMax((int) finalTime);
        seekbar1.setClickable(true);


        //use to call the pause functionality
        img_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pause();
            }
        });

        txt_playqueue.setEnabled(false);


        if (utype1.equals("Premium")) {
            txt_playqueue.setEnabled(true);
            txt_playqueue.setVisibility(View.VISIBLE);
        } else {

            txt_playqueue.setVisibility(View.GONE);
        }

        //access the playqueue list
        txt_playqueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                dialog1 = new Dialog(MainActivity1.this, android.R.style.Theme_Translucent);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(true);
                dialog1.setContentView(R.layout.dialogplaylistqueue);

                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog1.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                final ListView listView = (ListView) dialog1.findViewById(R.id.listView1);
                Button btn_cncl = (Button) dialog1.findViewById(R.id.btn_cncl);
                ImageView img_bck = (ImageView) dialog1.findViewById(R.id.img_bck);
                btn_cncl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog1.dismiss();
                    }
                });

                img_bck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog1.dismiss();
                    }
                });
                sample = new ArrayList<String>();
                sample1 = new ArrayList<String>();
                for (int i = 0; i < al_topsong.size(); i++) {


                    sample.add(al_topsong.get(i).getTitle());
                }
                for (int i = 0; i < al_topsong.size(); i++) {


                    sample1.add(al_topsong.get(i).getImage());
                }
                adapter = new CustomAdapter(MainActivity1.this, sample, sample1);

                listView.setAdapter(adapter);


                dialog1.show();
            }

        });

        //To show Large view of media player
        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_large.setVisibility(View.VISIBLE);
                linear_small.setVisibility(View.GONE);
                // fun(position1);
                //setup(position1);

            }
        });

        //To show Small view of media player
        linear_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_large.setVisibility(View.VISIBLE);
                linear_small.setVisibility(View.GONE);
            }
        });

        // To play next song
        img_forwed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_pause.setVisibility(View.VISIBLE);
                img_play.setVisibility(View.INVISIBLE);
                img_pause1.setVisibility(View.VISIBLE);
                img_play1.setVisibility(View.INVISIBLE);


                img_play.setEnabled(false);
                img_pause.setEnabled(true);
                img_shuffl.setEnabled(true);
                img_play1.setEnabled(false);
                img_pause1.setEnabled(true);
                img_shuffl1.setEnabled(true);

                if (utype1.equals("Premium")) {

                    nxt(position1);
                }
                else {

                    final Toast toast = Toast.makeText(MainActivity1.this, "Free Users/Guest Users can only play Shuffle,Please Register", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }

                        public void onFinish() {
                            toast.cancel();
                        }
                    }.start();
                }

            }
        });

        // To forward song
        img_forwed.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mp.seekTo(mp.getCurrentPosition() + 5000);
                return true;
            }
        });
//To play back song
        img_bckwrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_pause.setVisibility(View.VISIBLE);
                img_play.setVisibility(View.INVISIBLE);
                img_pause1.setVisibility(View.VISIBLE);
                img_play1.setVisibility(View.INVISIBLE);


                img_play.setEnabled(false);
                img_pause.setEnabled(true);
                img_shuffl.setEnabled(true);
                img_play1.setEnabled(false);
                img_pause1.setEnabled(true);
                img_shuffl1.setEnabled(true);

                if (utype1.equals("Premium")) {
                    backwrd(position1);

                } else {

                    final Toast toast = Toast.makeText(MainActivity1.this, "Free Users/Guest Users can only play Shuffle,Please Register", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }

                        public void onFinish() {
                            toast.cancel();
                        }
                    }.start();
                }

            }
        });

        img_bckwrd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if ((timeElapsed - backwardTime) > 0) {
                    timeElapsed = timeElapsed - backwardTime;
                    mp.seekTo((int) timeElapsed);
                }
                return true;

            }
        });

// To play song
        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();

            }
        });

        // To play shufffle Song
        img_shuffl1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    img_shuffl.setText(".");
                    img_shuffl1.setText(".");

                    img_shuffl.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffleon));
                    img_shuffl1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffleon));
                    img_repeat.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeat));
                    img_repeat1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeat));

                } else {
                    img_shuffl.setText("");
                    img_shuffl1.setText("");
                    img_shuffl.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffle));
                    img_shuffl1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffle));
                }
            }
        });
        img_shuffl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    img_shuffl.setText(".");
                    img_shuffl1.setText(".");
                    img_repeat.setText("");
                    img_repeat1.setText("");
                    img_shuffl.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffleon));
                    img_shuffl1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffleon));
                    img_repeat.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeat));
                    img_repeat1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeat));

                } else {

                    img_shuffl.setText("");
                    img_shuffl1.setText("");
                    img_shuffl.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffle));
                    img_shuffl1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffle));

                }
            }
        });

//TO repeat song
        img_repeat1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    img_repeat.setText(".");
                    img_repeat1.setText(".");
                    img_shuffl.setText("");
                    img_shuffl1.setText("");
                    img_shuffl.setEnabled(false);
                    img_shuffl1.setEnabled(false);
                    img_shuffl.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffle));
                    img_shuffl1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffle));
                    img_repeat.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeaton));
                    img_repeat1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeaton));
                } else {
                    img_repeat.setText("");
                    img_repeat1.setText("");
                    img_shuffl.setText(".");
                    img_shuffl1.setText(".");
                    img_shuffl.setEnabled(true);
                    img_shuffl1.setEnabled(true);
                    img_repeat.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeat));
                    img_repeat1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeat));

                }
            }
        });

        img_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    img_repeat.setText(".");
                    img_repeat1.setText(".");
                    img_shuffl.setText("");
                    img_shuffl1.setText("");
                    img_shuffl.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffle));
                    img_shuffl1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffle));
                    img_repeat.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeaton));
                    img_repeat1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeaton));

                } else {
                    img_repeat.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeat));
                    img_repeat1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.repeat));


                    img_repeat.setText("");
                    img_repeat1.setText("");
                    img_shuffl.setText(".");
                    img_shuffl1.setText(".");
                }
            }
        });
        img_cross11.setVisibility(View.VISIBLE);

//To view Small media player
        img_cross11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_large.setVisibility(View.GONE);
                linear_small.setVisibility(View.VISIBLE);

            }
        });

// To play next song
        img_forwed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_pause.setVisibility(View.VISIBLE);
                img_play.setVisibility(View.INVISIBLE);
                img_pause1.setVisibility(View.VISIBLE);
                img_play1.setVisibility(View.INVISIBLE);


                img_play.setEnabled(false);
                img_pause.setEnabled(true);
                img_shuffl.setEnabled(true);
                img_play1.setEnabled(false);
                img_pause1.setEnabled(true);
                img_shuffl1.setEnabled(true);
                if (utype1.equals("Premium")) {
                    nxt(position1);
                } else {

                    final Toast toast = Toast.makeText(MainActivity1.this, "Free Users/Guest Users can only play Shuffle,Please Register", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }

                        public void onFinish() {
                            toast.cancel();
                        }
                    }.start();
                }

            }
        });


        img_forwed1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mp.seekTo(mp.getCurrentPosition() + 5000);
                return true;
            }
        });

        //To play back song
        img_bckwrd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_pause.setVisibility(View.VISIBLE);
                img_play.setVisibility(View.INVISIBLE);
                img_pause1.setVisibility(View.VISIBLE);
                img_play1.setVisibility(View.INVISIBLE);


                img_play.setEnabled(false);
                img_pause.setEnabled(true);
                img_shuffl.setEnabled(true);
                img_play1.setEnabled(false);
                img_pause1.setEnabled(true);
                img_shuffl1.setEnabled(true);

                if (utype1.equals("Premium")) {
                    backwrd(position1);

                } else {

                    final Toast toast = Toast.makeText(MainActivity1.this, "Free Users/Guest Users can only play Shuffle,Please Register", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }

                        public void onFinish() {
                            toast.cancel();
                        }
                    }.start();
                }


            }
        });

        img_bckwrd1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if ((timeElapsed - backwardTime) > 0) {
                    timeElapsed = timeElapsed - backwardTime;

                    //seek to the exact second of the track
                    mp.seekTo((int) timeElapsed);
                }
                return true;

            }
        });

//T play the song of largeview media player
        img_play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("play", "reached");

                play1();

            }
        });
// To pause the song on largeview of mediaplayer
        img_pause1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pause1();
            }
        });


        queueList = new ArrayList<>();
        facebookSDKInitialize();

        facebookPost();

        //Check if user login with facebook
        if (fbchk.equals("Signed In")) {

            List<String> permissionNeeds = Arrays.asList("publish_actions");
            loginManager = LoginManager.getInstance();
            loginManager.logInWithPublishPermissions(MainActivity1.this, permissionNeeds);

        } else {


        }

//Check if user is login or not
        if (email1 == null || email1.isEmpty()) {

            tv_name.setText("LOGIN OR REGISTER");
            tv_name.setEnabled(true);

        } else {

            tv_name.setText(email1);
            tv_name.setEnabled(false);
        }

        //To login
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mp != null) {

                    mp.stop();
                    mp.release();
                    durationHandler.removeCallbacks(updateSeekBarTime);
                }

                Intent i = new Intent(MainActivity1.this, LoginSignup.class);
                startActivity(i);
                finish();

            }
        });


//Ccreate folder to save offline songs

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + ".GaantoriApp");

        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();

        }
        if (success) {

        } else {

        }

        tv_bck = (TextView) findViewById(R.id.bck);
        tv_bck.setVisibility(View.INVISIBLE);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        textToolHeader = (TextView) toolbar.findViewById(R.id.textView3);
        ed_srch = (EditText) toolbar.findViewById(R.id.ed_srch);
        img_srch = (ImageView) toolbar.findViewById(R.id.srch);
        img_cross = (ImageView) toolbar.findViewById(R.id.cross);
        textToolHeader.setText("");
        //To change the font of text
        Typeface face = Typeface.createFromAsset(textToolHeader.getContext().getAssets(), "fonts/myfont.ttf");
        textToolHeader.setTypeface(face);

        setSupportActionBar(toolbar);

        srchtext = ed_srch.getText().toString().trim();

        img_srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textToolHeader.setVisibility(View.INVISIBLE);

            }
        });

        textToolHeader.setVisibility(View.VISIBLE);
        ed_srch.setVisibility(View.INVISIBLE);
        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity1.ed_srch.setText("");

            }
        });
        img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawer.closeDrawer(Gravity.LEFT);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager

        mAdapter = new MyAdapter(MainActivity1.this, TITLES, ICONS);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);

        // Setting the adapter to RecyclerView
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item != null && item.getItemId() == android.R.id.home) {
                    if (Drawer.isDrawerOpen(Gravity.RIGHT)) {
                        Drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        Drawer.openDrawer(Gravity.RIGHT);
                    }
                }
                return false;
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.menu, getTheme());
        mDrawerToggle.setHomeAsUpIndicator(drawable);

        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Drawer.isDrawerVisible(GravityCompat.START)) {
                    Drawer.closeDrawer(GravityCompat.START);
                } else {
                    Drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        // Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        mDrawerToggle.setHomeAsUpIndicator(drawable);

        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                //  Google ads view
                mAdView = (AdView) findViewById(R.id.adView);
                mAdView.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);

                nxt(position1);

            }

        });


        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        // Request audio focus for playback
        int result = am.requestAudioFocus(focusChangeListener,
// Use the music stream.
                AudioManager.STREAM_MUSIC,
// Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);


        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


        //    mp.start();
// other app had stopped playing song now , so u can do u stuff now .
        }


//        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//
//// Request audio focus for playback
//        int result = am.requestAudioFocus(focusChangeListener,
//// Use the music stream.
//                AudioManager.STREAM_MUSIC,
//// Request permanent focus.
//                AudioManager.AUDIOFOCUS_GAIN);
//
//
//        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//// other app had stopped playing song now , so u can do u stuff now .
//            mp.start();
//        }
//
//


    }

//    private AudioManager.OnAudioFocusChangeListener focusChangeListener =
//            new AudioManager.OnAudioFocusChangeListener() {
//                public void onAudioFocusChange(int focusChange) {
//                    AudioManager am =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
//                    switch (focusChange) {
//
//                        case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) :
//                            // Lower the volume while ducking.
//                          //  mp.setVolume(0.2f, 0.2f);
//                            mp.pause();
//                            break;
//                        case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) :
//                            pause();
//                            break;
//
//                        case (AudioManager.AUDIOFOCUS_LOSS) :
//                            pause();
//
//                            break;
//
//                        case (AudioManager.AUDIOFOCUS_GAIN) :
//                            // Return the volume to normal and resume if paused.
//                          //  mediaPlayer.setVolume(1f, 1f);
//                            mp.start();
//                            break;
//                        default: break;
//                    }
//                }
//            };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onBackPressed() {

        this.moveTaskToBack(true);

//        if(getFragmentManager().getBackStackEntryCount() == 0) {
//           // super.onBackPressed();
//            this.moveTaskToBack(true);
//        }
////       if(getFragmentManager().getBackStackEntryCount() == 1) {
////            moveTaskToBack(false);
////        }
//        else {
//            getFragmentManager().popBackStack();
//        }
    }

//    @Override
//    public void onBackPressed() {
//
//      //  super.onBackPressed();
//        //this.moveTaskToBack(true);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            if (NetworkConnection.isConnectedToInternet(MainActivity1.this)) {
                Json_ProfileData.f.onActivityResult1(requestCode, resultCode, data);
            }
            else {
                Toast.makeText(getApplicationContext(), "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                return;

            }

        }
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

    private AudioManager.OnAudioFocusChangeListener focusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    AudioManager am =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    switch (focusChange) {

                        case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) :
                            // Lower the volume while ducking.
                       mp.setVolume(0.2f, 0.2f);
                            Log.e("in case---","1");
//                            mp.pause();
//                            img_play.setVisibility(View.GONE);
//                            img_pause.setVisibility(View.VISIBLE);
                            break;
                        case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) :
                            Log.e("in case---","2");
                            img_pause.setVisibility(View.VISIBLE);
                            img_play.setVisibility(View.INVISIBLE);
                            mp.start();
                            break;

                        case (AudioManager.AUDIOFOCUS_LOSS) :
                            Log.e("in case---","3");
                            img_pause.setVisibility(View.VISIBLE);
                            img_play.setVisibility(View.INVISIBLE);
                            mp.pause();

//                            ComponentName component =new ComponentName(AudioPlayerActivity.this,MediaControlReceiver.class);
//                            am.unregisterMediaButtonEventReceiver(component);
                            break;

                        case (AudioManager.AUDIOFOCUS_GAIN) :
                            Log.e("in case---","4");
                            // Return the volume to normal and resume if paused.
                         mp.setVolume(5f, 5f);
                         mp.start();
//                            img_pause.setVisibility(View.VISIBLE);
//                            img_play.setVisibility(View.GONE);
                            break;
                        default: break;
                    }
                }
            };

    public void setListOnPlayer(ArrayList<TopSongs> songList, int pos, boolean offline, Object object) {
        // TODO: 10/13/2016 add list to curent list and queue list

        position1 = pos;
        this.offline = offline;
        objectAdapter = object;
        this.al_topsong = songList;
        queueList.add(al_topsong.get(position1));

        fun(position1);
        setup(position1);


    }

    // To add the songs in playqueue
    public void addSongToQueue(TopSongs songData) {
        //queueList = new ArrayList<>();
        queueList.add(songData);
    }

    //Media player functionality
    public void setup(final int pos) {

        img_cross.setVisibility(View.GONE);
        position1 = pos;

        try {
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                    mp.seekTo(seekBar.getProgress());
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    // ///play auto next song after complete.

                    if (mp.getDuration() <= progress + 1000) {
                        Log.e("if----", "" + img_repeat.getText());

                        if (img_repeat.getText().toString().equalsIgnoreCase(".")) {
                            mp.pause();
                            fun(position1);
                            setup(position1);
                            img_shuffl.setEnabled(false);
                            img_shuffl1.setEnabled(false);
                        }
                        else if (img_shuffl.getText().toString().equalsIgnoreCase(".")) {
                            mp.pause();
                            Random r = new Random();
                            position1 = r.nextInt(al_topsong.size() - 1);
                            fun(position1);
                            setup(position1);
                        }
                        else if (img_shuffl1.getText().toString().equalsIgnoreCase(".")) {
                            mp.pause();
                            Random r = new Random();
                            position1 = r.nextInt(al_topsong.size() - 1);
                            fun(position1);
                            setup(position1);
                        }
                        else if (img_repeat1.getText().toString().equalsIgnoreCase(".")) {
                            mp.pause();
                            fun(position1);
                            setup(position1);
                            img_shuffl.setEnabled(false);
                            img_shuffl1.setEnabled(false);
                        }
                        else {
                            songcounter++;
                            Log.e("countr====",""+songcounter);
                            nxt(position1);
                        }
                    }

                    showSongTime(progress, mp.getDuration());
                }
            });

            seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                    mp.seekTo(seekBar.getProgress());
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    // ///play auto next song after complete.

                    if (mp.getDuration() <= progress + 1000) {

                        if (img_repeat.getText().toString().equalsIgnoreCase(".")) {

                            mp.pause();
                            fun(position1);
                            setup(position1);

                        } else if (img_shuffl.getText().toString().equalsIgnoreCase(".")) {
                            mp.pause();
                            Random r = new Random();
                            position1 = r.nextInt(al_topsong.size() - 1);
                            fun(position1);
                            setup(position1);
                        } else if (img_shuffl1.getText().toString().equalsIgnoreCase(".")) {
                            mp.pause();
                            Random r = new Random();
                            position1 = r.nextInt(al_topsong.size() - 1);
                            fun(position1);
                            setup(position1);
                        } else if (img_repeat1.getText().toString().equalsIgnoreCase(".")) {
                            mp.pause();
                            fun(position1);
                            setup(position1);

                        } else {
                            Log.e("else----", "" + img_repeat.getText());
                            nxt(position1);
                        }
                    }

                    showSongTime(progress, mp.getDuration());
                }
            });

            // To play song if song not save offline
            if (offline == false) {
                try {

                    mp.setDataSource(al_topsong.get(position1).getUrl());
                    tv_songname.setText(al_topsong.get(position1).getTitle());  //To get song name
                    tv_duration.setText(al_topsong.get(position1).getSonglength()); //To get song duration
                    tv_songname1.setText(al_topsong.get(position1).getTitle());
                    tv_duration1.setText(al_topsong.get(position1).getSonglength());

                    Log.e("song id---",""+al_topsong.get(position1).getId());
                    Log.e("song id---",""+al_topsong.get(position1).getAlbumid());
                    Log.e("userid---",""+user_id);

                    Log.e("userid.......----", "" + user_id);


                    if (user_id.equals("null")) {
                    }
                    else{
                        Log.e("in elseeeee----", "elseee");
                        new Json_SongPopup(getApplicationContext()).execute(al_topsong.get(position1).getAlbumid(),user_id);

                    }
                    if(user_id.equals("null")) {

                    }
                    else {
                        new Json_SongPlayCount(getApplicationContext()).execute(al_topsong.get(position1).getId(), user_id);
                    }
                    ViewGroup.LayoutParams listh = (ViewGroup.LayoutParams) listSinger.getLayoutParams();
                    listh.height = 80;
                    listSinger.setLayoutParams(listh);

                    artistname = al_topsong.get(position1).getArtistname();
                    List<String> alistname = Arrays.asList(artistname.split(","));

                    artistid = al_topsong.get(position1).getArtists();

                    final List<String> alistid = Arrays.asList(artistid.split(","));

                    for (int i = 0; i < alistname.size(); i++) {

                        int totalh = alistname.size();


                        listh.height = totalh * 80;
                        listSinger.setLayoutParams(listh);

                    }

                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MainActivity1.this, R.layout.list_itemsinger, alistname);
                    stringArrayAdapter.setNotifyOnChange(true);
                    listSinger.setAdapter(stringArrayAdapter);


                    listSinger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                            new Json_artistdetailAll(MainActivity1.this).execute(Fragment_Home1.user_id, alistid.get(position));

                            linear_large.setVisibility(View.GONE);
                            linear_small.setVisibility(View.VISIBLE);

                        }
                    });

                    //To underline album name
                    SpannableString content1 = new SpannableString(al_topsong.get(position1).getAlbum());
                    content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
                    tv_albumname1.setText(content1);

                    //To goto Albumdetail
                    tv_albumname1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            linear_large.setVisibility(View.GONE);
                            linear_small.setVisibility(View.VISIBLE);

                            String user_id;
                            if (Fragment_Home1.user_id.equals("null")) {
                                user_id = "0";
                            } else {

                                user_id = Fragment_Home1.user_id;
                            }

                            if (NetworkConnection.isConnectedToInternet(MainActivity1.this)) {
                                new Json_albumdetailAll(MainActivity1.this).execute(al_topsong.get(position1).getAlbumid(), user_id);
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                                return;


                            }

                        }
                    });
                    Picasso.with(context).load(al_topsong.get(position1).getImage()).resize(600, 400).into(img_song1);

                    img_song1.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v)
                        {
                            linear_large.setVisibility(View.GONE);
                            linear_small.setVisibility(View.VISIBLE);

                            String user_id;

                            if (Fragment_Home1.user_id.equals("null")) {
                                user_id = "0";
                            }
                            else {

                                user_id = Fragment_Home1.user_id;
                            }


                            if (NetworkConnection.isConnectedToInternet(MainActivity1.this)) {

                                new Json_albumdetailAll(MainActivity1.this).execute(al_topsong.get(position1).getAlbumid(), user_id);

                            }
                            else {

                                Toast.makeText(getApplicationContext(), "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                                 return;
                }
                      }
                    });

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                mp.prepareAsync();

                String ns = Context.NOTIFICATION_SERVICE;

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(ns);

                Notification notification = new Notification(R.mipmap.ic_launcher, null,
                        System.currentTimeMillis());

                RemoteViews  notificationView = new RemoteViews(getPackageName(),R.layout.mynotification);

                notification.contentView = notificationView;
                notification.contentView.setTextViewText(R.id.albumName,al_topsong.get(position1).getTitle());

                Intent switchIntent = new Intent(this, switchButtonListener.class);

                PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 0,
                        switchIntent, 0);

                notificationView.setOnClickPendingIntent(R.id.btn_play,
                        pendingSwitchIntent);

               // notificationView.setViewVisibility(R.id.btn_play,View.GONE);
                notificationManager.notify(1, notification);


                            //  startNotification();

//                try {
//
//
//                    final ShareLinkContent content = new ShareLinkContent.Builder().setContentTitle(username1 + " " + usernamelst + " " + "played Album  " + al_topsong.get(position1).getAlbum() + " Song: " + al_topsong.get(position1).getTitle() + " by Artist: " + al_topsong.get(position1).getArtistname())
//                            .setContentUrl(Uri.parse(al_topsong.get(position1).getUrl())).setImageUrl(Uri.parse(al_topsong.get(position1).getImage())).setContentDescription("Gaantori Player")
//                            .build();
//
//                    Log.e("before time", "10");
//
//                    final Runnable runnabled = new Runnable() {
//                        @Override
//                        public void run() {
//
//
//                            Log.e("after time", "10");
//
//                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//
//
//                            if (ShareDialog.canShow(ShareLinkContent.class)) {
//
//                                Log.e("userid----", "" + user_id);
//
//                                if (user_id.equals("null")) {
//
//                                    Log.e("in if----", "if");
//                                    shareDialog.show(content);
//                                }
//                                else {
//                                                    try {
//
//                                                        Log.e("jsonresvalue===",""+Json_SongPopup.res);
//
//                                                        if (!Json_SongPopup.res.equals("0")) {
//
//
//                                        } else {
//                                            shareDialog.show(content);
//                                        }
//                                    } catch (NumberFormatException e)
//                                    {}
//                                    Log.e("in else----", "else");
//                                }
//
//                            }
//
//
//                        }
//
//
//                    };
//
//
//                    handlerd.postDelayed(runnabled, 10000);
//                }
//                catch (NullPointerException e){
//
//                }

            }

            //offline case
            else {
                try {
                    String filePath = Environment.getExternalStorageDirectory() + "/.GaantoriApp/" + al_topsong.get(position1).getTitle() + ".mp3";

                    mp.setDataSource(filePath);
                    mp.prepare();
                    long mili = mp.getDuration();
                    String time = String.format("%d :%d",
                            TimeUnit.MILLISECONDS.toMinutes(mili),
                            TimeUnit.MILLISECONDS.toSeconds(mili) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mili)));

                    tv_songname.setText(al_topsong.get(position1).getTitle());
                    tv_duration.setText(time + "");
                    tv_songname1.setText(al_topsong.get(position1).getTitle());
                    tv_duration1.setText(time + "");

                   // tv_albumname1.setText(al_topsong.get(position1).getAlbum());

                    try {
                       // Log.e("imgeeee",""+al_topsong.get(position1).getImage());
                 //   Picasso.with(context).load(al_topsong.get(position1).getImage()).into(img_song1);

                    ViewGroup.LayoutParams listh = (ViewGroup.LayoutParams) listSinger.getLayoutParams();
                    listh.height = 80;
                    listSinger.setLayoutParams(listh);

                    artistname = al_topsong.get(position1).getArtistname();
                    List<String> alistname = Arrays.asList(artistname.split(","));

                    artistid = al_topsong.get(position1).getArtists();
                    final List<String> alistid = Arrays.asList(artistid.split(","));

                    for (int i = 0; i < alistname.size(); i++) {

                        int totalh = alistname.size();


                        listh.height = totalh * 80;
                        listSinger.setLayoutParams(listh);

                    }

                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MainActivity1.this, R.layout.list_itemsinger, alistname);
                    stringArrayAdapter.setNotifyOnChange(true);
                    listSinger.setAdapter(stringArrayAdapter);


                    listSinger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                            new Json_artistdetailAll(MainActivity1.this).execute(Fragment_Home1.user_id, alistid.get(position));

                        }
                    });


                    tv_albumname1.setText(al_topsong.get(position1).getAlbum());
                } catch (NullPointerException e) {
                }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            mstatus = sharedpreferences1.getString(ModeStatus, "");



            seekbar.setVisibility(ProgressBar.VISIBLE);
            seekbar.setMax((int) finalTime);
            seekbar.setClickable(true);

            seekbar1.setVisibility(ProgressBar.VISIBLE);
            seekbar1.setMax((int) finalTime);
            seekbar1.setClickable(true);

            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    if (mp.isPlaying()) {
                        mp.stop();
                        mp.release();
                    }

                    mp.start();


//Calculate 70% of the song
                    currentSongDuration = mp.getDuration();
                    currentSong70 = (currentSongDuration * 70) / 100;


                    timeElapsed = mp.getCurrentPosition();
                    seekbar.setProgress((int) timeElapsed);
                    seekbar1.setProgress((int) timeElapsed);
                    durationHandler.postDelayed(updateSeekBarTime, 1000);

                    seekbar.setMax(mp.getDuration());
                    seekbar1.setMax(mp.getDuration());

                }
            });

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        img_play.setEnabled(true);
        img_pause.setEnabled(true);
        img_shuffl.setEnabled(true);
        img_play.setVisibility(View.INVISIBLE);
        img_pause.setVisibility(View.VISIBLE);
    }

    //To pause the song on small media player
    private void pause() {

        mp.pause();

        img_play.setVisibility(View.VISIBLE);
        img_pause.setVisibility(View.INVISIBLE);
        img_play.setEnabled(true);
        img_pause.setEnabled(false);
        img_shuffl.setEnabled(true);


        img_play1.setVisibility(View.VISIBLE);
        img_pause1.setVisibility(View.INVISIBLE);
        img_play1.setEnabled(true);
        img_pause1.setEnabled(false);
        img_shuffl1.setEnabled(true);

    }

    //To pause the song on large media player
    private void pause1() {

        mp.pause();

        img_play.setVisibility(View.VISIBLE);
        img_pause.setVisibility(View.INVISIBLE);
        img_play.setEnabled(true);
        img_pause.setEnabled(false);
        img_shuffl.setEnabled(true);


        img_play1.setVisibility(View.VISIBLE);
        img_pause1.setVisibility(View.INVISIBLE);
        img_play1.setEnabled(true);
        img_pause1.setEnabled(false);
        img_shuffl1.setEnabled(true);

    }

    //To play song on large media player
    private void play1() {
        img_pause.setVisibility(View.VISIBLE);
        img_play.setVisibility(View.INVISIBLE);
        img_pause1.setVisibility(View.VISIBLE);
        img_play1.setVisibility(View.INVISIBLE);

        mp.start();

        img_play.setEnabled(false);
        img_pause.setEnabled(true);
        img_shuffl.setEnabled(true);
        img_play1.setEnabled(false);
        img_pause1.setEnabled(true);
        img_shuffl1.setEnabled(true);

    }

    //To play song on small media player
    private void play() {
        img_pause.setVisibility(View.VISIBLE);
        img_play.setVisibility(View.INVISIBLE);
        img_pause1.setVisibility(View.VISIBLE);
        img_play1.setVisibility(View.INVISIBLE);

        mp.start();

        img_play.setEnabled(false);
        img_pause.setEnabled(true);
        img_shuffl.setEnabled(true);
        img_play1.setEnabled(false);
        img_pause1.setEnabled(true);
        img_shuffl1.setEnabled(true);

    }

    private void backwrd(int pos) {
        position1 = pos;
        if (position1 != 0) {
            position1--;
        }

        if (mp != null) {
            mp.pause();
            mp.stop();
            mp.release();
        }
        mp = new MediaPlayer();

        try {

            try {

                try {
                    mp.setDataSource(al_topsong.get(position1).getUrl());

                } catch (IndexOutOfBoundsException e) {
                }
                tv_songname.setText(al_topsong.get(position1).getTitle());
                tv_duration.setText(al_topsong.get(position1).getSonglength());
                tv_songname1.setText(al_topsong.get(position1).getTitle());
                tv_duration1.setText(al_topsong.get(position1).getSonglength());


                try {
                    if (al_topsong.get(position1).getImage().equals("null")) {

                    } else {
                        Picasso.with(context).load(al_topsong.get(position1).getImage()).into(img_song1);
                    }
                } catch (NullPointerException e) {
                }

                try {

                    if(user_id.equals("null")) {

                    }
                    else{


                        new Json_SongPlayCount(getApplicationContext()).execute(al_topsong.get(position1).getId(), user_id);
                    }

                    ViewGroup.LayoutParams listh = (ViewGroup.LayoutParams) listSinger.getLayoutParams();
                    listh.height = 80;
                    listSinger.setLayoutParams(listh);

                    artistname = al_topsong.get(position1).getArtistname();
                    List<String> alistname = Arrays.asList(artistname.split(","));

                    artistid = al_topsong.get(position1).getArtists();
                    final List<String> alistid = Arrays.asList(artistid.split(","));

                    for (int i = 0; i < alistname.size(); i++) {

                        int totalh = alistname.size();


                        listh.height = totalh * 80;
                        listSinger.setLayoutParams(listh);

                    }

                    final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MainActivity1.this, R.layout.list_itemsinger, alistname);
                    stringArrayAdapter.setNotifyOnChange(true);
                    listSinger.setAdapter(stringArrayAdapter);


                    listSinger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                            new Json_artistdetailAll(MainActivity1.this).execute(Fragment_Home1.user_id, alistid.get(position));

                        }
                    });
                    tv_albumname1.setText(al_topsong.get(position1).getAlbum());
                } catch (NullPointerException e) {
                }
                //	Picasso.with(context).load(topsongstitlelist.get(count)).into(img_song);

            } catch (IndexOutOfBoundsException e) {


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mp.prepareAsync();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.start();

            }

        });

    }

    //To play next song
    private void nxt(int pos1) {

        img_pause.setVisibility(View.VISIBLE);
        img_play.setVisibility(View.INVISIBLE);
        img_pause1.setVisibility(View.VISIBLE);
        img_play1.setVisibility(View.INVISIBLE);


        img_play.setEnabled(false);
        img_pause.setEnabled(true);
        img_shuffl.setEnabled(true);
        img_play1.setEnabled(false);
        img_pause1.setEnabled(true);
        img_shuffl1.setEnabled(true);


//        songcounter++;
//        Log.e("songcounter---",""+songcounter);
//
//        if(songcounter==6){
//            try {
//
//                final ShareLinkContent content = new ShareLinkContent.Builder().setContentTitle(username1 + " " + usernamelst + " " + "played Album  " + al_topsong.get(position1).getAlbum() + " Song: " + al_topsong.get(position1).getTitle() + " by Artist: " + al_topsong.get(position1).getArtistname())
//                        .setContentUrl(Uri.parse(al_topsong.get(position1).getUrl())).setImageUrl(Uri.parse(al_topsong.get(position1).getImage())).setContentDescription("Gaantori Player")
//                        .build();
//
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//
//                shareDialog.show(content);
//                songcounter=0;
//            }
//            catch (NullPointerException e){
//
//            }
//
//        }



        if (queueCount < queueList.size() - 1) {
            //queue
            queueEnable = true;
            ++queueCount;
        } else {
            queueEnable = false;
        }

        if (!queueEnable) {
            try {
                if (position1 >= al_topsong.size() - 1) {
                    position1 = 0;
                } else {
                    ++position1;
                }
            } catch (NullPointerException e) {
            }
        }

        if (mp != null) {
            mp.stop();
            mp.release();
        }
        mp = new MediaPlayer();

        try {

            try {
                try {
                    if (queueEnable) {

                        mp.setDataSource(queueList.get(queueCount).getUrl());
                        tv_songname.setText(queueList.get(queueCount).getTitle());

                        mp.prepareAsync();


//                        if(user_id.equals("null")) {
//
//                        }
//                        else {
//                            new Json_SongPlayCount(getApplicationContext()).execute(queueList.get(queueCount).getId(), user_id);
//                        }
                    }
                    else if (offline == false) {

                         mp.setDataSource(al_topsong.get(position1).getUrl());

//                        if(user_id.equals("null")) {
//
//                        }
//                        else {
//                            new Json_SongPlayCount(getApplicationContext()).execute(al_topsong.get(position1).getId(), user_id);
//                        }
                        tv_songname.setText(al_topsong.get(position1).getTitle());
                        tv_duration.setText(al_topsong.get(position1).getSonglength());
                        tv_songname1.setText(al_topsong.get(position1).getTitle());
                        tv_duration1.setText(al_topsong.get(position1).getSonglength());
                        Picasso.with(context).load(al_topsong.get(position1).getImage()).into(img_song1);


                        try {


                            ViewGroup.LayoutParams listh = (ViewGroup.LayoutParams) listSinger.getLayoutParams();
                            listh.height = 80;
                            listSinger.setLayoutParams(listh);

                            artistname = al_topsong.get(position1).getArtistname();
                            List<String> alistname = Arrays.asList(artistname.split(","));

                            artistid = al_topsong.get(position1).getArtists();
                            final List<String> alistid = Arrays.asList(artistid.split(","));

                            for (int i = 0; i < alistname.size(); i++) {

                                int totalh = alistname.size();


                                listh.height = totalh * 80;
                                listSinger.setLayoutParams(listh);

                            }

                            final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MainActivity1.this, R.layout.list_itemsinger, alistname);
                            stringArrayAdapter.setNotifyOnChange(true);
                            listSinger.setAdapter(stringArrayAdapter);


                            listSinger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                                    new Json_artistdetailAll(MainActivity1.this).execute(Fragment_Home1.user_id, alistid.get(position));

                                }
                            });

                            tv_albumname1.setText(al_topsong.get(position1).getAlbum());

                            if (objectAdapter instanceof Adapter_AlbumDetail) {
                                playingPosition = position1;
                                ((Adapter_AlbumDetail) objectAdapter).notifyDataSetChanged();
                            } else if (objectAdapter instanceof Adapter_TopSongs) {

                                playingPosition = position1;
                                ((Adapter_TopSongs) objectAdapter).notifyDataSetChanged();
                            } else if (objectAdapter instanceof Adapter_LibrarySong) {

                                playingPosition = position1;
                                ((Adapter_LibrarySong) objectAdapter).notifyDataSetChanged();
                            } else if (objectAdapter instanceof Adapter_Playlistdetail) {

                                playingPosition = position1;
                                ((Adapter_Playlistdetail) objectAdapter).notifyDataSetChanged();
                            } else if (objectAdapter instanceof Adapter_StaredSong) {

                                playingPosition = position1;
                                ((Adapter_StaredSong) objectAdapter).notifyDataSetChanged();
                            } else if (objectAdapter instanceof Adapter_MyPlaylistdetail) {

                                playingPosition = position1;
                                ((Adapter_MyPlaylistdetail) objectAdapter).notifyDataSetChanged();
                            }
                        } catch (NullPointerException e) {
                        }



//                        al_topsong.get(position1).getArtistname()


                        if(songcounter==6){
                            try {

                                final ShareLinkContent content = new ShareLinkContent.Builder().setContentTitle(username1 + " " + usernamelst + " " + "played Album  " + al_topsong.get(position1).getAlbum() + " Song: " + al_topsong.get(position1).getTitle() + " by Artist: " + al_topsong.get(position1).getArtistname())
                                        .setContentUrl(Uri.parse(al_topsong.get(position1).getUrl())).setImageUrl(Uri.parse(al_topsong.get(position1).getImage())).setContentDescription("Gaantori Player")
                                        .build();

                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                                shareDialog.show(content);
                                songcounter=0;
                            }
                            catch (NullPointerException e){

                            }

                        }
                        mp.prepareAsync();

                        String ns = Context.NOTIFICATION_SERVICE;

                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(ns);

                        Notification notification = new Notification(R.mipmap.ic_launcher, null,
                                System.currentTimeMillis());

                        RemoteViews notificationView = new RemoteViews(getPackageName(),
                                R.layout.mynotification);


                        notification.contentView = notificationView;

                        notification.contentView.setTextViewText(R.id.albumName,al_topsong.get(position1).getTitle());

                        Intent switchIntent = new Intent(this, switchButtonListener.class);

                        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 0,
                                switchIntent, 0);

                        notificationView.setOnClickPendingIntent(R.id.btn_play,
                                pendingSwitchIntent);

                        notificationManager.notify(1, notification);



//                        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                            @Override
//                            public void onPrepared(MediaPlayer player) {
//                                Log.e("nxttttt", "caling");
//                            }
//
//                        });


                    } else {

                        String filePath = Environment.getExternalStorageDirectory() + "/.GaantoriApp/" + al_topsong.get(position1).getTitle() + ".mp3";
                        mp.setDataSource(filePath);

                        mp.prepare();
                        long mili = mp.getDuration();

                        String time = String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes(mili),
                                TimeUnit.MILLISECONDS.toSeconds(mili) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mili)));


                        tv_songname.setText(al_topsong.get(position1).getTitle());
                        tv_duration.setText(time + "");
                        tv_songname1.setText(al_topsong.get(position1).getTitle());
                        tv_duration1.setText(time + "");

                        try {
                       //     Picasso.with(context).load(al_topsong.get(position1).getImage()).into(img_song1);

                            ViewGroup.LayoutParams listh = (ViewGroup.LayoutParams) listSinger.getLayoutParams();
                            listh.height = 80;
                            listSinger.setLayoutParams(listh);

                            artistname = al_topsong.get(position1).getArtistname();
                            List<String> alistname = Arrays.asList(artistname.split(","));

                            artistid = al_topsong.get(position1).getArtists();
                            final List<String> alistid = Arrays.asList(artistid.split(","));

                            for (int i = 0; i < alistname.size(); i++) {

                                int totalh = alistname.size();


                                listh.height = totalh * 80;
                                listSinger.setLayoutParams(listh);

                            }

                            final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MainActivity1.this, R.layout.list_itemsinger, alistname);
                            stringArrayAdapter.setNotifyOnChange(true);
                            listSinger.setAdapter(stringArrayAdapter);


                            listSinger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                                    new Json_artistdetailAll(MainActivity1.this).execute(Fragment_Home1.user_id, alistid.get(position));

                                }
                            });


                            tv_albumname1.setText(al_topsong.get(position1).getAlbum());
                        } catch (NullPointerException e) {
                        }

                    }
                } catch (IndexOutOfBoundsException e) {
                }

            } catch (IndexOutOfBoundsException e) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



/////////////
//        if (mstatus.equals("publicon")) {
//
//            if (fbchk.equals("Signed In")) {
//
//                ShareLinkContent content = new ShareLinkContent.Builder().setContentTitle(username1 + usernamelst + "played Album  " + al_topsong.get(position1).getAlbum() + " Song: " + al_topsong.get(position1).getTitle() + " by Artist: " + al_topsong.get(position1).getArtistname())
//                        .setContentUrl(Uri.parse(al_topsong.get(position1).getUrl())).setImageUrl(Uri.parse(al_topsong.get(position1).getImage())).setContentDescription("Gaantori Player")
//                        .build();
//                ShareApi.share(content, null);
//
//            } else {
//
//
//            }
//
//        } else if (mstatus.equals("privateon")) {
//
//        } else {
//
//        }

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.start();
                //progressDialog.dismiss();
            }

        });

    }

    // show time on text view
    public void showSongTime(int progress, int current_duration) {

        int mint = progress / 60000;
        int sec = (progress % 60000) / 1000;
        tv_durationremain.setText(mint + ":" + sec);
        tv_durationremain1.setText(mint + ":" + sec);


    }

    //handler to change seekBarTime
    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            //get current position

            try {
                timeElapsed = mp.getCurrentPosition();
//            seekbar.setVisibility(ProgressBar.VISIBLE);
//            seekbar1.setVisibility(ProgressBar.VISIBLE);
                //set seekbar progress
                if (!utype1.equals("Premium")) {
                    if (timeElapsed >= currentSong70) {
                        //call next song method
                        // nxt(position1);

                        if (al_topsong.size() > 1) {
                            Random r = new Random();
                            position1 = r.nextInt(al_topsong.size() - 1);
                            fun(position1);
                            setup(position1);
                        } else {
                            fun(position1);
                            setup(position1);
                        }
                    }
                }

                seekbar.setProgress((int) timeElapsed);
                seekbar1.setProgress((int) timeElapsed);
                //set time remaing
                double timeRemaining = finalTime - timeElapsed;
                // TODO: 11/18/2016 check time


                //	 tv_durationremain.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
                // Log.e("time remaining", "" + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

                durationHandler.postDelayed(this, 1000);
            } catch (NullPointerException e) {

            }

        }


    };


    public void fun(final int pos) {

        position1 = pos;
        //img_song = (ImageView) dialog2.findViewById(R.id.img_song);
        seekbar.setMax((int) finalTime);
        seekbar1.setMax((int) finalTime);
        seekbar.setClickable(true);
        seekbar1.setClickable(true);

        songcounter++;
        Log.e("songcounter---",""+songcounter);

        if(songcounter==5){
            try {

                final ShareLinkContent content = new ShareLinkContent.Builder().setContentTitle(username1 + " " + usernamelst + " " + "played Album  " + al_topsong.get(position1).getAlbum() + " Song: " + al_topsong.get(position1).getTitle() + " by Artist: " + al_topsong.get(position1).getArtistname())
                        .setContentUrl(Uri.parse(al_topsong.get(position1).getUrl())).setImageUrl(Uri.parse(al_topsong.get(position1).getImage())).setContentDescription("Gaantori Player")
                        .build();

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                shareDialog.show(content);
                songcounter=0;
            }
            catch (NullPointerException e){

            }

        }

        if (!utype1.equals("Premium")) {
            counter++;

            if (counter == 4) {
                if (!utype1.equals("Premium")) {

                    int position7 = 0;

                    mp.pause();
                    mp.stop();
                    mpads = MediaPlayer.create(this, Uri.parse(Json_AdService.al_ads.get(position7).getUrl()));


                    mpads.start();

                    mAdView = (AdView) findViewById(R.id.adView);
                    mAdView.setVisibility(View.VISIBLE);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);

                    mAdView.postDelayed(new Runnable() {
                        public void run() {
                            mAdView.setVisibility(View.GONE);
                            counter = 0;
                            mp.start();
                        }
                    }, 30000);
                }
            }
        }


        if (mp != null) {
            mp.pause();
            mp.stop();
            mp.release();
        }
        mp = new MediaPlayer();

        img_cross.setVisibility(View.VISIBLE);
        img_cross1.setVisibility(View.INVISIBLE);

    }

    private void facebookPost() {
        //check login
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken == null) {
            Log.e("signout", ">>>" + "Signed Out");
            fbchk = "Signed Out";
        } else {
            Log.e("signin", ">>>" + "Signed In");
            fbchk = "Signed In";
        }
    }

    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(MainActivity1.this);
        callbackManager = CallbackManager.Factory.create();
    }

    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>()

    {
        @Override
        public void onSuccess(Sharer.Result result) {

            Log.e("on sucess==", "" + result);
            // TODO Auto-generated method stub

        }

        @Override
        public void onError(FacebookException error) {
            // TODO Auto-generated method stub
            Log.e("on error==", "error");
        }

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub
            Log.e("on cancl==", "cancl method");
            return;

//            if ("com.facebook.platform.extra.COMPLETION_GESTURE".equals("cancel"))
//                return;
//           // onBackPressed();
//         //   if (window.location.hash == '#close_window') window.close();.
//         //  window.close();
        }
    };


    private void startNotification(){

        String ns = Context.NOTIFICATION_SERVICE;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(ns);

        Notification notification = new Notification(R.mipmap.ic_launcher, null,
                System.currentTimeMillis());

        RemoteViews notificationView = new RemoteViews(getPackageName(),
                R.layout.mynotification);

        notification.contentView = notificationView;

        Intent switchIntent = new Intent(this, switchButtonListener.class);
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 0,
                switchIntent, 0);

        notificationView.setOnClickPendingIntent(R.id.btn_play,
                pendingSwitchIntent);

        notificationManager.notify(1, notification);

    }


    public  static class switchButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("Here", "clicked on play");
            RemoteViews notificationView = new RemoteViews(context.getPackageName(),
                  R.layout.mynotification);

            // notificationView = new RemoteViews(getPackageName(),R.layout.mynotification);
                       notificationView.setViewVisibility(R.id.btn_play,View.GONE);
           // rv.setViewVisibility(R.id.page1, View. INVISIBLE);
        }
    }
    }
