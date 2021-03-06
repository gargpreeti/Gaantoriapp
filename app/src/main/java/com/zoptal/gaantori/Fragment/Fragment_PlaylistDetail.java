package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.Adapter.Adapter_HomeFeatured;
import com.zoptal.gaantori.JsonClasses.Json_PlaylistDetail;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_PlaylistDetail extends Fragment  implements View.OnClickListener {

    private ListView list;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

     view = inflater.inflate(R.layout.activity_playlistdetail, container, false); //inflate the view

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MainActivity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(null);
        MainActivity1.tv_bck.setVisibility(View.VISIBLE);
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
        list=(ListView)view.findViewById(R.id.list);

        String user_id;
        if(Fragment_Home1.user_id.equals("null")){
            user_id="0";
        }
        else{

            user_id=Fragment_Home1.user_id;
        }



        if (NetworkConnection.isConnectedToInternet(getActivity())) {


            new Json_PlaylistDetail(getActivity(),list).execute(Adapter_HomeFeatured.pid,user_id);

        }
        else {
            Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


        }




        MainActivity1.tv_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment_Home1 fragment=new Fragment_Home1();
                FragmentManager fragmentManager3 = getFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
                MainActivity1.Drawer.closeDrawer(Gravity.LEFT);

            }
        });
        return view;

    }


    @Override
    public void onClick(View v) {


    }




}