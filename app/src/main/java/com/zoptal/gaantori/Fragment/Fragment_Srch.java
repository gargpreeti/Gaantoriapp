package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.JsonClasses.Json_Srch;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_Srch extends Fragment  implements View.OnClickListener {

    private GridView grid,grid1,grid2,grid3;
    ListView list_album,list_artist,list_playlist,list_song;
    public static TextView tv_album,tv_artist,tv_playlist,tv_song;
      String srchtext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_srch, container, false); //inflate the view

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MainActivity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(null);

        MainActivity1.tv_bck.setVisibility(View.VISIBLE);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tv_album=(TextView)view.findViewById(R.id.tv_album);
        tv_artist=(TextView)view.findViewById(R.id.tv_artist);
        tv_playlist=(TextView)view.findViewById(R.id.tv_playlist);
        tv_song=(TextView)view.findViewById(R.id.tv_song);


        MainActivity1.img_srch.setVisibility(View.INVISIBLE);

        MainActivity1.tv_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_Home1 fragment_srch = new Fragment_Home1();
                FragmentManager fragmentManager1 =getFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_srch).commit();

            }
        });


        MainActivity1.textToolHeader.setVisibility(View.VISIBLE);
        MainActivity1.ed_srch.setVisibility(View.INVISIBLE);
        MainActivity1.img_cross.setVisibility(View.INVISIBLE);


        list_album=(ListView) view.findViewById(R.id.list_album);
        list_artist=(ListView)view.findViewById(R.id.list_artist);
        list_playlist=(ListView)view.findViewById(R.id.list_playlist);
        list_song=(ListView)view.findViewById(R.id.list_song);


        if (NetworkConnection.isConnectedToInternet(getActivity())) {

            new Json_Srch(getActivity(),list_album, list_artist, list_playlist, list_song).execute(String.valueOf(MainActivity1.ed_srch.getText()),Fragment_Home1.user_id);

        }
        else {
            Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


        }

        return view;

    }

    @Override
    public void onClick(View v) {


    }




}