package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zoptal.gaantori.JsonClasses.Json_Payment;
import com.zoptal.gaantori.JsonClasses.Json_SignUp;
import com.zoptal.gaantori.R;


public class Fragment_PaymentMethod extends Fragment implements View.OnClickListener {

    Button btn_monthly;
    Button btn_yearly;
    private ImageView img_bck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_in_app_payment, container, false); //inflate the view

        btn_monthly = (Button)view.findViewById(R.id.btn_monthly);
        btn_yearly = (Button)view.findViewById(R.id.btn_yearly);
        img_bck = (ImageView)view.findViewById(R.id.bck);

        btn_monthly.setOnClickListener(this);
        btn_yearly.setOnClickListener(this);
        img_bck.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_monthly:

                new Json_Payment(getActivity()).execute(Json_SignUp.id, "1","Month");
                break;

            case R.id.btn_yearly:
                new Json_Payment(getActivity()).execute(Json_SignUp.id, "10","Year");
                break;

            case R.id.bck:

                Fragment_PaymentMethod1 fragment_detail = new Fragment_PaymentMethod1();
                FragmentManager fragmentManager3 = getFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();


        }
    }
}
