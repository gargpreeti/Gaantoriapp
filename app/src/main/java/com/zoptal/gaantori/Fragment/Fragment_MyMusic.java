package com.zoptal.gaantori.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.Adapter.Adapter_offlinesongs;
import com.zoptal.gaantori.JsonClasses.Json_LibrarySong;
import com.zoptal.gaantori.JsonClasses.Json_MyPlaylist;
import com.zoptal.gaantori.JsonClasses.Json_MyPlaylist1;
import com.zoptal.gaantori.JsonClasses.Json_OfflineSongsRemove;
import com.zoptal.gaantori.JsonClasses.Json_SaveplaylistMymusic;
import com.zoptal.gaantori.JsonClasses.Json_StarSong_add_remove;
import com.zoptal.gaantori.JsonClasses.Json_StaredSong;
import com.zoptal.gaantori.JsonClasses.Json_saveplaylistsong;
import com.zoptal.gaantori.JsonClasses.Json_shareapp;
import com.zoptal.gaantori.JsonClasses.TopSongs;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;

import java.io.File;
import java.util.ArrayList;

public class Fragment_MyMusic extends Fragment  implements View.OnClickListener{

    //Declartion
    public static ListView list, list1;
    private Button btn_myplaylist, btn_lib, btn_offsong, btn_stared;
    private ImageView add_img;
    LinearLayout linearLayout;

    public static Dialog dialog1;
    static ArrayList<File> mySongs;


    public static  ArrayList<String> items1;
    public static ArrayList<String> itemsID;
    public static ArrayList<TopSongs> items;
    MainActivity1 activity1;
    LinearLayout menu_linear,line_divide;
    RelativeLayout rel;
    View view;
    Dialog dialog;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Window window = getActivity().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        view= inflater.inflate(R.layout.activity_my_music, container, false); //inflate the view

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MainActivity1.mDrawerToggle.setDrawerIndicatorEnabled(true);

        MainActivity1.tv_bck.setVisibility(View.INVISIBLE);
        MainActivity1.img_srch.setVisibility(View.VISIBLE);
        activity1 = (MainActivity1) getActivity();

        mySongs = findSongs(Environment.getExternalStorageDirectory());

        items = new ArrayList<TopSongs>();
        itemsID=new ArrayList<>();

        for (int i = 0; i < mySongs.size(); i++) {

            TopSongs ob = new TopSongs();
            ob.setTitle(mySongs.get(i).getName().replace(".mp3", "").replace("-","").replaceAll("[0-9]",""));
            String  sid=mySongs.get(i).getName();
            String result = sid.substring(0).replaceAll("-","").replace(".mp3","");
            String str = result.replaceAll("[^\\d.]", "");

            ob.setUrl(mySongs.get(i).getAbsolutePath());
            items.add(ob);
            itemsID.add(str);

         }

        btn_myplaylist = (Button) view.findViewById(R.id.btn_myplaylist);
        btn_lib = (Button) view.findViewById(R.id.btn_lib);
        btn_offsong = (Button) view.findViewById(R.id.btn_offsong);
        btn_stared = (Button) view.findViewById(R.id.btn_stared);

        linearLayout = (LinearLayout) view.findViewById(R.id.linear);
        line_divide= (LinearLayout) view.findViewById(R.id.line_divide);
        linearLayout.setVisibility(View.VISIBLE);

        list = (ListView) view.findViewById(R.id.list);
        list1 = (ListView) view.findViewById(R.id.list1);
        add_img = (ImageView) view.findViewById(R.id.img);

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Fragment_Home1.user_id.equals("null")) {

                    final Toast toast = Toast.makeText(getActivity(),"Please Register to enjoy Premium Functions",Toast.LENGTH_SHORT);
                    toast.show();
                    // Toast.makeText(getActivity(), "Please Login see Top List", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();

                }
                else {
                    dialog_listname();
                }
            }
        });


        if (Fragment_Home1.user_id.equals("null")) {
            final Toast toast = Toast.makeText(getActivity(),"Please Register to enjoy Premium Functions",Toast.LENGTH_SHORT);
            toast.show();

            new CountDownTimer(1000, 500)
            {
                public void onTick(long millisUntilFinished) {toast.show();}
                public void onFinish() {toast.cancel();}
            }.start();

        } else {

            if (NetworkConnection.isConnectedToInternet(getActivity())) {

                new Json_MyPlaylist(getActivity(), list).execute(Fragment_Home1.user_id);

            }

                else {
                MainActivity1.linr_offlinemsg.setVisibility(View.INVISIBLE);
                btn_myplaylist.setBackgroundResource(R.mipmap.quick_select);
                btn_lib.setBackgroundResource(R.mipmap.quick_select);
                btn_offsong.setBackgroundResource(R.mipmap.selected);
                btn_stared.setBackgroundResource(R.mipmap.quick_select);


                linearLayout.setVisibility(View.INVISIBLE);
                list.setVisibility(View.INVISIBLE);
                list1.setVisibility(View.VISIBLE);
                line_divide.setVisibility(View.GONE);


                mySongs = findSongs(Environment.getExternalStorageDirectory());

                items = new ArrayList<TopSongs>();
                itemsID=new ArrayList<>();


                for (int i = 0; i < mySongs.size(); i++) {

                    TopSongs ob = new TopSongs();
                    ob.setTitle(mySongs.get(i).getName().replace(".mp3", "").replace("-","").replaceAll("[0-9]",""));
                    String  sid=mySongs.get(i).getName();
                    String result = sid.substring(0).replaceAll("-","").replace(".mp3","");
                    String str = result.replaceAll("[^\\d.]", "");

                    ob.setUrl(mySongs.get(i).getAbsolutePath());
                    items.add(ob);
                    itemsID.add(str);

                }

                menu_linear = (LinearLayout) view.findViewById(R.id.menu_linear);
                rel=(RelativeLayout)view.findViewById(R.id.rel);

                items1 = new ArrayList<String>();

                for (int i = 0; i < items.size(); i++) {

                    items1.add(String.valueOf(items.get(i).getTitle()));

                }

                list1.setAdapter(new Adapter_offlinesongs(getActivity(),items1));

//just toast it
                final Toast toast = Toast.makeText(getActivity(),"Please Check your internet connection",Toast.LENGTH_SHORT);
                toast.show();
                // Toast.makeText(getActivity(), "Please Login see Top List", Toast.LENGTH_SHORT).show();
                new CountDownTimer(1000, 500)
                {
                    public void onTick(long millisUntilFinished) {toast.show();}
                    public void onFinish() {toast.cancel();}
                }.start();
            }


        }

        btn_myplaylist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                linearLayout.setVisibility(View.VISIBLE);
                list1.setVisibility(View.INVISIBLE);
                list.setVisibility(View.VISIBLE);
                line_divide.setVisibility(View.VISIBLE);


                btn_myplaylist.setBackgroundResource(R.mipmap.selected);
                btn_lib.setBackgroundResource(R.mipmap.quick_select);
                btn_offsong.setBackgroundResource(R.mipmap.quick_select);
                btn_stared.setBackgroundResource(R.mipmap.quick_select);

                if (NetworkConnection.isConnectedToInternet(getActivity())) {

                    new Json_MyPlaylist(getActivity(), list).execute(Fragment_Home1.user_id);

                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }

            }
        });


        btn_lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayout.setVisibility(View.INVISIBLE);
                list.setVisibility(View.INVISIBLE);
                list1.setVisibility(View.VISIBLE);
                line_divide.setVisibility(View.GONE);


                btn_myplaylist.setBackgroundResource(R.mipmap.quick_select);
                btn_lib.setBackgroundResource(R.mipmap.selected);
                btn_offsong.setBackgroundResource(R.mipmap.quick_select);
                btn_stared.setBackgroundResource(R.mipmap.quick_select);

                if (NetworkConnection.isConnectedToInternet(getActivity())) {

                    new Json_LibrarySong(getActivity(), list1).execute(Fragment_Home1.user_id);

                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }

            }
        });



        btn_offsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity1.linr_offlinemsg.setVisibility(View.GONE);

                btn_myplaylist.setBackgroundResource(R.mipmap.quick_select);
                btn_lib.setBackgroundResource(R.mipmap.quick_select);
                btn_offsong.setBackgroundResource(R.mipmap.selected);
                btn_stared.setBackgroundResource(R.mipmap.quick_select);

//
                if (!MainActivity1.utype1.equals("Premium")) {

                    final Toast toast = Toast.makeText(getActivity(),"Please Register to enjoy Premium Functions",Toast.LENGTH_SHORT);
                    toast.show();
                               new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();

            }

                else {

                    linearLayout.setVisibility(View.INVISIBLE);
                    list.setVisibility(View.INVISIBLE);
                    list1.setVisibility(View.VISIBLE);
                    line_divide.setVisibility(View.GONE);


                    view = inflater.inflate(R.layout.song_layout, container, false);
                    btn_myplaylist.setBackgroundResource(R.mipmap.quick_select);
                    btn_lib.setBackgroundResource(R.mipmap.quick_select);
                    btn_offsong.setBackgroundResource(R.mipmap.selected);
                    btn_stared.setBackgroundResource(R.mipmap.quick_select);

                    menu_linear = (LinearLayout) view.findViewById(R.id.menu_linear);
                    rel=(RelativeLayout)view.findViewById(R.id.rel);

                    items1 = new ArrayList<String>();
                    for (int i = 0; i < items.size(); i++) {

                        items1.add(String.valueOf(items.get(i).getTitle()));

                    }

                    list1.setAdapter(new Adapter_offlinesongs(getActivity(),items1));

                    menu_linear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                }
            }
        });

        btn_stared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayout.setVisibility(View.INVISIBLE);
                list.setVisibility(View.INVISIBLE);
                list1.setVisibility(View.VISIBLE);
                line_divide.setVisibility(View.GONE);


                btn_myplaylist.setBackgroundResource(R.mipmap.quick_select);
                btn_lib.setBackgroundResource(R.mipmap.quick_select);
                btn_offsong.setBackgroundResource(R.mipmap.quick_select);
                btn_stared.setBackgroundResource(R.mipmap.selected);



                if (NetworkConnection.isConnectedToInternet(getActivity())) {

                    new Json_StaredSong(getActivity(), list1).execute(Fragment_Home1.user_id);

                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }

            }
        });

        return view;

    }


    @Override
    public void onClick(View v) {


    }
            public void dialog_offline(final int position){

                dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent){
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        // Tap anywhere to close dialog.
                        dialog.dismiss();
                        return true;
                    }
                };

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_addplaylist6);

                TextView tv_cncl=(TextView)dialog.findViewById(R.id.tv_cncl);
                final RelativeLayout play=(RelativeLayout)dialog.findViewById(R.id.relativeLayout11);
                RelativeLayout removes=(RelativeLayout)dialog.findViewById(R.id.relativeLayout);
                RelativeLayout playnxt=(RelativeLayout)dialog.findViewById(R.id.relativeLayout2);
                       RelativeLayout share=(RelativeLayout)dialog.findViewById(R.id.relativeLayout3);

                removes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String filePath = Environment.getExternalStorageDirectory() + "/.GaantoriApp/" +items.remove(position).getTitle() + ".mp3";

                             File file = new File(filePath);
                        if (file.exists()) {
                            file.delete();
                        }
                        items1.remove(position);



                        if (NetworkConnection.isConnectedToInternet(getActivity())) {


                            new Json_OfflineSongsRemove(getActivity()).execute(Fragment_Home1.user_id,itemsID.get(position),"0");


                        }
                        else {
                            Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                        }




                        ArrayAdapter<String> adp = new ArrayAdapter<String>(
                                getActivity(), R.layout.song_layout, R.id.textView1,
                                items1);

                            list1.setAdapter(adp);
                        dialog.dismiss();

                    }
                });
                playnxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(Fragment_Home1.utype1.equals("Premium")) {

                            final Toast toast = Toast.makeText(getActivity(),"Song added to queue list",Toast.LENGTH_SHORT);
                            toast.show();
                                         new CountDownTimer(1000, 500)
                            {
                                public void onTick(long millisUntilFinished) {toast.show();}
                                public void onFinish() {toast.cancel();}
                            }.start();
                            activity1.addSongToQueue(items.get(position));
                            dialog.dismiss();
                        }
                        else{

                            Toast.makeText(getActivity(), "Please Register to enjoy Premium Functions", Toast.LENGTH_LONG).show();
                        }

                    }
                });
              play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                         activity1.linear_small.setVisibility(View.VISIBLE);
                         activity1.setListOnPlayer(items, position, true, null);
                         dialog.dismiss();


                    }
                });

                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        shareIt();
                    }
                });

                tv_cncl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });




                dialog.show();





            }
    public void dialog_listname() {


        dialog1 = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_listname);


        TextView text_cncl = (TextView) dialog1.findViewById(R.id.cncl);
        TextView text_create = (TextView) dialog1.findViewById(R.id.create);
        final EditText ed_name = (EditText) dialog1.findViewById(R.id.name);
         text_cncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        text_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = ed_name.getText().toString().toLowerCase().trim();

                if (Fragment_Home1.user_id.equals("null")) {


                    final Toast toast = Toast.makeText(getActivity(),"Please Login to create playlist",Toast.LENGTH_SHORT);
                    toast.show();
                    // Toast.makeText(getActivity(), "Please Login see Top List", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();
          } else {

                    if (NetworkConnection.isConnectedToInternet(getActivity())) {


                        new Json_SaveplaylistMymusic(getActivity()).execute(name, Fragment_Home1.user_id);

                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                    }



                }

                dialog1.dismiss();
            }

        });


        dialog1.show();
    }


    public void dialog_addplaylist(final int pos) {


        final Dialog dialoglist = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        dialoglist.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialoglist.setCancelable(true);
        dialoglist.setContentView(R.layout.dialog_addplaylist);


        ImageView img_cross = (ImageView) dialoglist.findViewById(R.id.img_cross);
        RelativeLayout rel = (RelativeLayout) dialoglist.findViewById(R.id.star_id);
        final ListView list = (ListView) dialoglist.findViewById(R.id.list);



        if (NetworkConnection.isConnectedToInternet(getActivity())) {


            new Json_MyPlaylist1(getActivity(), list).execute(Fragment_Home1.user_id);

        }
        else {
            Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


        }


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (NetworkConnection.isConnectedToInternet(getActivity())) {


                    new Json_saveplaylistsong(getActivity()).execute(Fragment_Home1.user_id, Json_MyPlaylist1.model_myPlaylist.getAl_myplaylist().get(position).getId(),items.get(pos).getId(),"1");

                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }






                dialoglist.dismiss();
            }
        });


        LinearLayout newlist = (LinearLayout) dialoglist.findViewById(R.id.newlist);

        newlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialoglist.dismiss();

                dialog_listname();

            }
        });

        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (NetworkConnection.isConnectedToInternet(getActivity())) {



                    new Json_StarSong_add_remove(getActivity()).execute(Fragment_Home1.user_id, items.get(pos).getId(), "1");

                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }


                dialoglist.dismiss();

            }
        });


        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialoglist.dismiss();

            }
        });


        dialoglist.show();
    }

    public ArrayList<File> findSongs(File root) {
        final String MEDIA_PATH = "/sdcard/.GaantoriApp/";

        File home = new File(MEDIA_PATH);
        ArrayList<File> a1 = new ArrayList<File>();


        File[] files = home.listFiles();



        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                a1.addAll(findSongs(singleFile));

            } else {
                if (singleFile.getName().endsWith(".mp3")) {
                    a1.add(singleFile);

                }

            }
        }
        return a1;
    }


    public ArrayList<File> findSongs1(File root1) {
        final String MEDIA_PATH = "/sdcard/.GaantoriApp/";

        File home1 = new File(MEDIA_PATH);
        ArrayList<File> a2 = new ArrayList<File>();


        File[] files1 = home1.listFiles();



        for (File singleFile1 : files1) {
            if (singleFile1.isDirectory() && !singleFile1.isHidden()) {
                a2.addAll(findSongs1(singleFile1));

            } else {
                if (singleFile1.getName().endsWith("")) {
                    a2.add(singleFile1);

                }

            }
        }
        return a2;
    }

    private void shareIt() {
//sharing implementation here
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Gaantori App");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, Json_shareapp.userlink);
        getActivity().startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

}