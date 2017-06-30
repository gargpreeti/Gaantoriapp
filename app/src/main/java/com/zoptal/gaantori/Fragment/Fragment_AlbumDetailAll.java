package com.zoptal.gaantori.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Adapter.Adapter_AlbumDetail;
import com.zoptal.gaantori.JsonClasses.Json_MyPlaylist1;
import com.zoptal.gaantori.JsonClasses.Json_Saveplaylist;
import com.zoptal.gaantori.JsonClasses.Json_StarSong_add_remove;
import com.zoptal.gaantori.JsonClasses.Json_albumdetail;
import com.zoptal.gaantori.JsonClasses.Json_albumdetailAll;
import com.zoptal.gaantori.JsonClasses.Json_artistdetailAll;
import com.zoptal.gaantori.JsonClasses.Json_saveplaylistsong;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Fragment_AlbumDetailAll extends Fragment  implements View.OnClickListener {

    //initilize  variable
     private ListView list;
     private  ImageView img;
     public  TextView album_name;
     LinearLayout linear_saveplaylist,linear,linear_shuffle;
     Button btn_shuffle,btn;
     MainActivity1 activity1;
     int position1=0;
     Dialog dialog1;
     View view;
     Json_albumdetail json_albumdetail;
     ListView listSinger;
      public static String id, artistname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        Window window = getActivity().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);


         view= inflater.inflate(R.layout.activity_album_detail, container, false); //layout inflate

         getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
         getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        MainActivity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(null);
        MainActivity1.tv_bck.setVisibility(View.VISIBLE);
        MainActivity1.img_srch.setVisibility(View.VISIBLE);


        linear_saveplaylist=(LinearLayout)view.findViewById(R.id.linear_saveplaylist);
        linear=(LinearLayout)view.findViewById(R.id.linear);
        linear_shuffle=(LinearLayout)view.findViewById(R.id.linear_shuffle);
        getActivity().setFinishOnTouchOutside(true);
        activity1 = (MainActivity1) getActivity();

        // For searching on textbase
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

        // create a new ListView, set the adapter and item click listener

        list=(ListView)view.findViewById(R.id.list);
        img=(ImageView)view.findViewById(R.id.img);
        album_name=(TextView)view.findViewById(R.id.name);

        listSinger = (ListView) view.findViewById(R.id.listSinger);
        btn_shuffle=(Button)view.findViewById(R.id.btn_shuffle);
        btn=(Button)view.findViewById(R.id.btn);


      Picasso.with(getActivity()).load(Json_albumdetailAll.albumimage).into(img);

        album_name.setText(Json_albumdetailAll.albumname);

        artistname=Json_albumdetailAll.albumartist;
        List<String> alistname = Arrays.asList(artistname.split(","));

        ViewGroup.LayoutParams listh = listSinger.getLayoutParams();
        listh.height =50;
       listSinger.setLayoutParams(listh);

        id = Json_albumdetailAll.albumartistid;
        final List<String> alistid = Arrays.asList(id.split(","));

        for(int i=0;i<alistname.size();i++){

            int totalh=alistname.size();


            listh.height =totalh*80;
           listSinger.setLayoutParams(listh);

        }
        // our adapter instance

        final ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_itemsinger,alistname);
        stringArrayAdapter.setNotifyOnChange(true);
        listSinger.setAdapter(stringArrayAdapter);

        String user_id;
        if(Fragment_Home1.user_id.equals("null")){
            user_id="0";
        }
        else{

            user_id=Fragment_Home1.user_id;
        }


        if (NetworkConnection.isConnectedToInternet(getActivity())) {

            json_albumdetail= new Json_albumdetail(getActivity(),list);
            json_albumdetail.execute(Json_albumdetailAll.albumid,user_id);

        }
        else {
            Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


        }
        // For artist detail
       listSinger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {



                if (NetworkConnection.isConnectedToInternet(getActivity())) {

                    new Json_artistdetailAll(getActivity()).execute(Fragment_Home1.user_id, alistid.get(position));
                }
                    else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }



            }
        });



        MainActivity1.tv_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                			FragmentManager fragmentManager1 = getFragmentManager();
				            fragmentManager1.popBackStack();

            }
        });
        //To play shuffle song
        linear_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     final Toast toast = Toast.makeText(getActivity(), "Shuffle play song",Toast.LENGTH_SHORT);
                toast.show();

                new CountDownTimer(1000, 500)
                {
                    public void onTick(long millisUntilFinished) {toast.show();}
                    public void onFinish() {toast.cancel();}
                }.start();


                linear_shuffle.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffle_btn));
                Random r = new Random();
                position1 = r.nextInt(Json_albumdetailAll.model_albumDetail.getAl_songs().size() - 1);

                activity1.linear_small.setVisibility(View.VISIBLE);
                activity1.playingTAG="Adapter_AlbumDetail";
                activity1.playingPosition=position1;
                json_albumdetail.getCurrentAdaptor().notifyDataSetChanged();
                activity1.setListOnPlayer(Json_albumdetailAll.model_albumDetail.getAl_songs(), position1,false,null);

            }
        });


//To play shuffle song
        btn_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final Toast toast = Toast.makeText(getActivity(), "Shuffle play song",Toast.LENGTH_SHORT);
                toast.show();
                      new CountDownTimer(1000, 500)
                {
                    public void onTick(long millisUntilFinished) {toast.show();}
                    public void onFinish() {toast.cancel();}
                }.start();


                linear_shuffle.setBackgroundDrawable(getResources().getDrawable(R.mipmap.shuffle_btn));

                if(Json_albumdetailAll.model_albumDetail.getAl_songs().size()>1) {

                    Random r = new Random();
                    position1 = r.nextInt(Json_albumdetailAll.model_albumDetail.getAl_songs().size() - 1);
                    activity1.linear_small.setVisibility(View.VISIBLE);
                    activity1.playingTAG="Adapter_AlbumDetail";
                    activity1.playingPosition=position1;
                    json_albumdetail.getCurrentAdaptor().notifyDataSetChanged();

                         activity1.setListOnPlayer(Json_albumdetailAll.model_albumDetail.getAl_songs(), position1,false,null);
                }
                else {
                    activity1.linear_small.setVisibility(View.VISIBLE);
                    activity1.setListOnPlayer(Json_albumdetailAll.model_albumDetail.getAl_songs(), position1, false, null);
                }

            }
        });
        //To save in playlist
        linear_saveplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Fragment_Home1.user_id.equals("null")) {

                    //just toast it
                    final Toast toast = Toast.makeText(getActivity(),"Guest users cannot add to playlist",Toast.LENGTH_SHORT);
                    toast.show();
                      new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();

                }
                else
                {
                    dialog_addplaylist(Adapter_AlbumDetail.testpos);


                }

                 }
        });
        //To add in playlist
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Fragment_Home1.utype1.equals("Premium")) {
                    dialog_addplaylist(Adapter_AlbumDetail.testpos);
                }

                else
                {

                    final Toast toast = Toast.makeText(getActivity(),"Free user cannot add to playlist",Toast.LENGTH_SHORT);
                    toast.show();
                               new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();

                }

            }
        });
        return view;

    }

    public void dialog_addplaylist(final int pos) {

            final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_addplaylist);


        ImageView img_cross = (ImageView) dialog.findViewById(R.id.img_cross);
        RelativeLayout rel = (RelativeLayout) dialog.findViewById(R.id.star_id);
        final ListView list = (ListView) dialog.findViewById(R.id.list);



        if (NetworkConnection.isConnectedToInternet(getActivity())) {

            new Json_MyPlaylist1(getActivity(), list).execute(Fragment_Home1.user_id);
        }
        else {
            Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


        }





        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                dialog.dismiss();


                        for(int i=0;i<Json_albumdetailAll.model_albumDetail.getAl_songs().size();i++) {

                                            if (NetworkConnection.isConnectedToInternet(getActivity())) {
                                new Json_saveplaylistsong(getActivity()).execute(Fragment_Home1.user_id, Json_MyPlaylist1.model_myPlaylist.getAl_myplaylist().get(position).getId(),Adapter_AlbumDetail.m.getAl_songs().get(i).getId(), "1");


                            }
                            else {
                                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                            }




                        }
            }
        });

        LinearLayout newlist = (LinearLayout) dialog.findViewById(R.id.newlist);

        newlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

              dialog_listname();

            }
        });

        //To save song in started list
        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (NetworkConnection.isConnectedToInternet(getActivity())) {
                    new Json_StarSong_add_remove(getActivity()).execute(Fragment_Home1.user_id, Adapter_AlbumDetail.m.getAl_songs().get(pos).getId(), "1");

                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }

                         dialog.dismiss();

            }
        });


        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void dialog_listname() {


        dialog1 = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog1.setCancelable(true);

        dialog1.setContentView(R.layout.dialog_listname);


        TextView text_cncl = (TextView) dialog1.findViewById(R.id.cncl);
        TextView text_create = (TextView) dialog1.findViewById(R.id.create);
        final EditText ed_name = (EditText) dialog1.findViewById(R.id.name);

        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if(dialog1.isShowing()) {

                    dialog1.setCanceledOnTouchOutside(true);
                    dialog1.setCancelable(true);
                }
            }
        });
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

                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();



                } else {

                    if (NetworkConnection.isConnectedToInternet(getActivity())) {

                        new Json_Saveplaylist(getActivity()).execute(name, Fragment_Home1.user_id);
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
    @Override
    public void onClick(View v) {


    }



}