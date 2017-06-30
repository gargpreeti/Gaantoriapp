package com.zoptal.gaantori.Fragment;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.Adapter.Adapter_TopAlbums;
import com.zoptal.gaantori.Adapter.Adapter_TopArtists;
import com.zoptal.gaantori.Adapter.Adapter_TopSongs;
import com.zoptal.gaantori.JsonClasses.Json_TopAlbums;
import com.zoptal.gaantori.JsonClasses.Json_TopArtist;
import com.zoptal.gaantori.JsonClasses.Json_TopSongs;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_TopLists extends Fragment  implements View.OnClickListener {

    private ListView list;
    private Button btn_songs,btn_albums,btn_artists;
    Adapter_TopSongs adapter_topSongs;
    Adapter_TopAlbums adapter_topAlbums;
    Adapter_TopArtists adapterTopArtists;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Window window = getActivity().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

         view = inflater.inflate(R.layout.activity_top_lists, container, false); //inflate the view
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MainActivity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(MainActivity1.drawable);
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
        MainActivity1.tv_bck.setVisibility(View.INVISIBLE);

        btn_songs=(Button)view.findViewById(R.id.btn_songs);
        btn_albums=(Button)view.findViewById(R.id.btn_albums);
        btn_artists=(Button)view.findViewById(R.id.btn_artists);
        list=(ListView)view.findViewById(R.id.list);


        adapter_topAlbums= new Adapter_TopAlbums(getActivity(), Json_TopAlbums.model_top);
        list.setAdapter(adapter_topAlbums);

        if(Fragment_Home1.user_id.equals("null")){

            final Toast toast = Toast.makeText(getActivity(), "Please Login see Top List",Toast.LENGTH_SHORT);
            toast.show();

            new CountDownTimer(1000, 500)
            {
                public void onTick(long millisUntilFinished) {toast.show();}
                public void onFinish() {toast.cancel();}
            }.start();
        }

        else {


            if (NetworkConnection.isConnectedToInternet(getActivity())) {

                new Json_TopAlbums(getActivity(), adapter_topAlbums).execute(Fragment_Home1.user_id, "1");
            }
            else {
                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


            }




       }

           btn_songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_songs.setBackgroundResource(R.mipmap.selected);
                btn_albums.setBackgroundResource(R.mipmap.quick_select);
                btn_artists.setBackgroundResource(R.mipmap.quick_select);


                if(Fragment_Home1.user_id.equals("null")){


                    final Toast toast = Toast.makeText(getActivity(), "Please Login see Top Songs",Toast.LENGTH_SHORT);
                    toast.show();
                    // Toast.makeText(getActivity(), "Please Login see Top List", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();
                }


                else {

                    if (NetworkConnection.isConnectedToInternet(getActivity())) {

                        new Json_TopSongs(getActivity(),list).execute(Fragment_Home1.user_id, "1");
                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                    }





                }
                    }
        });



        btn_albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_albums.setBackgroundResource(R.mipmap.selected);
                btn_artists.setBackgroundResource(R.mipmap.quick_select);
                btn_songs.setBackgroundResource(R.mipmap.quick_select);

                adapter_topAlbums= new Adapter_TopAlbums(getActivity(), Json_TopAlbums.model_top);
                list.setAdapter(adapter_topAlbums);

                if(Fragment_Home1.user_id.equals("null")){

                    final Toast toast = Toast.makeText(getActivity(), "Please Login see Top Albums",Toast.LENGTH_SHORT);
                    toast.show();

                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();


                }

                else {

                    if (NetworkConnection.isConnectedToInternet(getActivity())) {
                        new Json_TopAlbums(getActivity(), adapter_topAlbums).execute(Fragment_Home1.user_id, "1");
                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                    }

                }
            }
        });


        btn_artists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_artists.setBackgroundResource(R.mipmap.selected);
                btn_albums.setBackgroundResource(R.mipmap.quick_select);
                btn_songs.setBackgroundResource(R.mipmap.quick_select);

                adapterTopArtists= new Adapter_TopArtists(getActivity(), Json_TopArtist.model_top);
                list.setAdapter(adapterTopArtists);

                if(Fragment_Home1.user_id.equals("null")){


                    final Toast toast = Toast.makeText(getActivity(), "Please Login see Top Artist",Toast.LENGTH_SHORT);
                    toast.show();

                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();

                }

                else {


                    if (NetworkConnection.isConnectedToInternet(getActivity())) {

                        new Json_TopArtist(getActivity(), adapterTopArtists).execute(Fragment_Home1.user_id, "1");
                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                    }




                }


            }
        });
      return view;

    }
    @Override
    public void onClick(View v) {


    }




}