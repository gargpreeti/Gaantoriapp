package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_Upgrade extends Fragment  implements View.OnClickListener {

         TextView tv_msg;
        Button btn_continue;
        ImageView img_selectedm,img_selectedy,imageView3,imageView4;
        View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

      view = inflater.inflate(R.layout.activity_upgrade, container, false); //inflate the view

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MainActivity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(null);

        MainActivity1.tv_bck.setVisibility(View.VISIBLE);
        MainActivity1.img_srch.setVisibility(View.VISIBLE);

        tv_msg=(TextView)view.findViewById(R.id.msg);
        btn_continue=(Button)view.findViewById(R.id.continue_btn);
        img_selectedm=(ImageView)view.findViewById(R.id.img_selectedm);
        img_selectedy=(ImageView)view.findViewById(R.id.img_selectedy);
        imageView3=(ImageView)view.findViewById(R.id.imageView3);
        imageView4=(ImageView)view.findViewById(R.id.imageView4);

        if(Fragment_Home1.utype1.equals("Premium")){

            btn_continue.setVisibility(View.GONE);
            tv_msg.setVisibility(View.VISIBLE);
        }
        else {
            btn_continue.setVisibility(View.GONE);
            tv_msg.setVisibility(View.GONE);
        }

        if(Fragment_Home1.ptype1.equals("M")){
            img_selectedy.setVisibility(View.GONE);
            img_selectedm.setVisibility(View.VISIBLE);
        }
       else if(Fragment_Home1.ptype1.equals("Y")){

            img_selectedm.setVisibility(View.GONE);
            img_selectedy.setVisibility(View.VISIBLE);

        }
        else{
            img_selectedm.setVisibility(View.GONE);
            img_selectedy.setVisibility(View.GONE);

        }

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_selectedy.setVisibility(View.GONE);
                img_selectedm.setVisibility(View.VISIBLE);
                if(Fragment_Home1.ptype1.equals("M")){
                    btn_continue.setVisibility(View.GONE);
                }
                else{
                    btn_continue.setVisibility(View.VISIBLE);
                }
        if(Fragment_Home1.ptype1.equals("F")){
            btn_continue.setVisibility(View.VISIBLE);
        }

            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_selectedm.setVisibility(View.GONE);
                img_selectedy.setVisibility(View.VISIBLE);

                if(Fragment_Home1.ptype1.equals("Y")){
                    btn_continue.setVisibility(View.GONE);
                }
                else{
                    btn_continue.setVisibility(View.VISIBLE);
                }
                if(Fragment_Home1.ptype1.equals("F")){
                    btn_continue.setVisibility(View.VISIBLE);
                }

            }
        });


//For payment

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkConnection.isConnectedToInternet(getActivity())) {

                    Fragment_PaymentMethod2 fragment_detail = new Fragment_PaymentMethod2();
                    FragmentManager fragmentManager3 =getFragmentManager();
                    fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();

        }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }

            }
        });

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
        MainActivity1.tv_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager1 = getFragmentManager();
                Fragment_Settings fragment_postad = new Fragment_Settings();
                fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_postad).commit();


            }
        });


        return view;

    }


    @Override
    public void onClick(View v) {


    }




}