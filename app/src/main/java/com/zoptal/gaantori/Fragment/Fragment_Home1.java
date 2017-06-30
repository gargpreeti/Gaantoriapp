package com.zoptal.gaantori.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.zoptal.gaantori.Adapter.Adapter_HomeEnjoyMusic;
import com.zoptal.gaantori.JsonClasses.Json_HomeService;
import com.zoptal.gaantori.JsonClasses.Json_Usertype;
import com.zoptal.gaantori.JsonClasses.Json_shareapp;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Fragment_Home1 extends Fragment  implements View.OnClickListener {

    String MyPREFERENCES = "MyPrefs1";
    SharedPreferences sharedpreferences1;
    public static String usertokn="null",username1,email1,utype1="",ptype1;
    String tokn = "toknKey";
    String username="usernameKey";
    String email="emailKey";
    String utyp="usertype";
    String ptyp = "packagetype";


    public static String user_id;
    private GridView grid,grid1,grid2;
    public static GridView grid3;
    Button btn_seemore,btn_see,btn_see1;
    int img[]={R.mipmap.toph1,R.mipmap.browseh1,R.mipmap.discoverh1,R.mipmap.artist1,R.mipmap.artist1,R.mipmap.artist1,R.mipmap.artist1,R.mipmap.artist1,R.mipmap.artist1,R.mipmap.artist1};
    String name[]={"Toplists","Browse","Discover"};
   // InterstitialAd mInterstitialAd;
    List<String> al = new ArrayList<String>();
    Dialog dialog1;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        Window window = getActivity().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        view = inflater.inflate(R.layout.activity_home1, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //get data from shared preferences
        sharedpreferences1 =getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences prefs=getActivity().getSharedPreferences("srchvalue",Context.MODE_PRIVATE);

        if (sharedpreferences1.contains(tokn)) {
            usertokn = sharedpreferences1.getString(tokn, "");
            username1= sharedpreferences1.getString(username, "");
            email1= sharedpreferences1.getString(email, "");
            utype1= sharedpreferences1.getString(utyp, "");
            ptype1 = sharedpreferences1.getString(ptyp, "");
        }
                 user_id=usertokn;
                 if(email1==null|| email1.isEmpty()){

                    MainActivity1.tv_name.setText("LOGIN OR REGISTER");
                    MainActivity1.tv_name.setEnabled(true);
        }
        else{
                    MainActivity1.tv_name.setText(email1);
                    MainActivity1.tv_name.setEnabled(false);
        }

        if(utype1.equals("Premium")) {
            MainActivity1.txt_playqueue.setEnabled(true);
            MainActivity1.txt_playqueue.setVisibility(View.VISIBLE);
        }
        else{

            MainActivity1.txt_playqueue.setVisibility(View.GONE);
        }


          Log.e("usertype====",""+utype1);


        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(MainActivity1.drawable);
        MainActivity1.tv_bck.setVisibility(View.INVISIBLE);
        MainActivity1.img_srch.setVisibility(View.VISIBLE);

        MainActivity1.img_srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity1.ed_srch.setVisibility(View.VISIBLE);
                MainActivity1.textToolHeader.setVisibility(View.INVISIBLE);
                MainActivity1.img_srch.setVisibility(View.INVISIBLE);
                MainActivity1.img_cross.setVisibility(View.VISIBLE);

                MainActivity1.img_cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                             MainActivity1.ed_srch.setText("");

                    }
                });

               if(prefs.getStringSet("srchvalue", null)!=null) {
                    Set<String> set = prefs.getStringSet("srchvalue", null);
                    List<String> sample = new ArrayList<String>(set);

                   //show popup

               dialog1 = new Dialog(getActivity(), android.R.style.Theme_Translucent){
                        @Override
                        public boolean onTouchEvent(MotionEvent event) {
                            // Tap anywhere to close dialog.
                            dialog1.dismiss();
                            return true;
                        }
                    };


                    dialog1.setCanceledOnTouchOutside(true);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setCancelable(true);
                    dialog1.setContentView(R.layout.dialoglist);
                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    final ListView listView = (ListView) dialog1.findViewById(R.id.listView1);


                      ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                              android.R.layout.simple_list_item_1, android.R.id.text1, sample);

                     listView.setAdapter(adapter);

                   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                           int position1 = listView.getPositionForView(view);
                           String text =arg0.getItemAtPosition(position).toString();

                           MainActivity1.ed_srch.setText(text);

                       }
                   });

                    dialog1.show();
                }

            }
        });
        MainActivity1.ed_srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (dialog1.isShowing()) {
                        dialog1.dismiss();
                    }
                }
                catch (NullPointerException e) {
                }
            }
        });

        MainActivity1.img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MainActivity1.ed_srch.setText("");

            }
        });

        MainActivity1.ed_srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    ArrayList<String> set = new ArrayList<String>();
                    if(prefs.getStringSet("srchvalue", null)!=null) {
                        Set<String> set1 = prefs.getStringSet("srchvalue", null);
                        List<String> sample = new ArrayList<String>(set1);

                        for(int i=0;i<sample.size();i++){

                            set.add(sample.get(i));

                        }

                        set.add(String.valueOf(MainActivity1.ed_srch.getText()));
                        SharedPreferences.Editor edit=prefs.edit();
                        set1.addAll(set);

                        edit.putStringSet("srchvalue", set1);
                        edit.commit();

                    }
                    else{


                        Set<String> set1 = new HashSet<String>();
                        set.add(String.valueOf(MainActivity1.ed_srch.getText()));
                        SharedPreferences.Editor edit=prefs.edit();
                        set1.addAll(set);

                        edit.putStringSet("srchvalue", set1);
                        edit.commit();
                    }

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
        MainActivity1.img_cross.setVisibility(View.INVISIBLE);

        btn_seemore=(Button)view.findViewById(R.id.btn_seemore);
        btn_see=(Button)view.findViewById(R.id.btn_see);
        btn_see1=(Button)view.findViewById(R.id.btn_see1);
        grid=(GridView)view.findViewById(R.id.gridView);
        grid1=(GridView)view.findViewById(R.id.gridView1);
        grid2=(GridView)view.findViewById(R.id.gridView2);
        grid3=(GridView)view.findViewById(R.id.gridView3);

        if (NetworkConnection.isConnectedToInternet(getActivity())) {
            new Json_Usertype(getActivity()).execute(user_id);
            new Json_HomeService(getActivity(), grid, grid2, grid3).execute(user_id);
            new Json_shareapp(getActivity()).execute();

        } else {


        }


            grid1.setAdapter(new Adapter_HomeEnjoyMusic(getActivity(),name,img));

        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }

        });

        btn_seemore.setOnClickListener(this);
        btn_see.setOnClickListener(this);
        btn_see1.setOnClickListener(this);

        return view;

    }

   @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_seemore:

                Fragment_NewReleases fragment_newreleases = new Fragment_NewReleases();
                FragmentManager fragmentManager1 = getFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_newreleases).commit();
                MainActivity1.Drawer.closeDrawer(Gravity.LEFT);

            break;

            case R.id.btn_see:

                Fragment_FeaturedPlaylist fragment_featuredPlaylist = new Fragment_FeaturedPlaylist();
                FragmentManager fragmentManager3 = getFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_featuredPlaylist).addToBackStack(null).commit();
                MainActivity1.Drawer.closeDrawer(Gravity.LEFT);

                break;

            case R.id.btn_see1:

                break;



        }
    }


}