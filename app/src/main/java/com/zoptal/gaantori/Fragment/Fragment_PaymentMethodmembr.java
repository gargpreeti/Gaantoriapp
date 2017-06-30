package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zoptal.gaantori.JsonClasses.Json_PaymentMember;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_PaymentMethodmembr extends Fragment implements View.OnClickListener {

    //Declration
    Button btn_monthly;
    Button btn_yearly;
     public static String ptype;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_in_app_paymentmember, container, false); //inflate the view

        btn_monthly = (Button)view.findViewById(R.id.btn_monthly);
        btn_yearly = (Button)view.findViewById(R.id.btn_yearly);

        btn_monthly.setOnClickListener(this);
        btn_yearly.setOnClickListener(this);

        MainActivity1.tv_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager1 = getFragmentManager();
                Fragment_PaymentMethod2 fragment_postad = new Fragment_PaymentMethod2();
                fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_postad).commit();


            }
        });


        return view;

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_monthly:

                ptype="M";

                if (NetworkConnection.isConnectedToInternet(getActivity())) {


                    new Json_PaymentMember(getActivity()).execute(Fragment_Home1.user_id, "1","Month");


                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }

                break;

            case R.id.btn_yearly:
                ptype="Y";


                if (NetworkConnection.isConnectedToInternet(getActivity())) {


                    new Json_PaymentMember(getActivity()).execute(Fragment_Home1.user_id, "10","Year");

                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }
              break;

        }
    }
}
