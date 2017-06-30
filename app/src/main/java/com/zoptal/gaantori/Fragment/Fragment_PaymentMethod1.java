package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.InAppPaymentActivity;
import com.zoptal.gaantori.main.LoginSignup;


public class Fragment_PaymentMethod1 extends Fragment  {

    private ImageView img_bck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.dialogpaymentmethod, container, false); //inflate the view

        Button btn_paypal = (Button)view.findViewById(R.id.btn_paypal);
        Button btn_inapp = (Button) view.findViewById(R.id.btn_inapp);
        img_bck = (ImageView) view.findViewById(R.id.bck);

        btn_paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment_PaymentMethod fragment_detail=new Fragment_PaymentMethod();
                FragmentManager fragmentManager3 = getFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();
            }
        });

        btn_inapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getActivity(), InAppPaymentActivity.class);
                getActivity().startActivity(i);
            }
        });
        img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_bck= new Intent(getActivity(), LoginSignup.class);
                startActivity(intent_bck);
            }
        });
        return view;

    }



}
