package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.InAppPaymentActivityMember;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_PaymentMethod2 extends Fragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.dialogpaymentmethod1, container, false); //inflate the view

        Button btn_paypal = (Button)view.findViewById(R.id.btn_paypal);
        Button btn_inapp = (Button) view.findViewById(R.id.btn_inapp);



        MainActivity1.tv_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager1 = getFragmentManager();
                Fragment_Upgrade fragment_postad = new Fragment_Upgrade();
                fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_postad).commit();


            }
        });



// For paypal method using
        btn_paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment_PaymentMethodmembr fragment_detail=new Fragment_PaymentMethodmembr();
                FragmentManager fragmentManager3 = getFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();
            }
        });

        //For Google App billing
        btn_inapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getActivity(), InAppPaymentActivityMember.class);
                getActivity().startActivity(i);
            }
        });
        return view;

    }



}
