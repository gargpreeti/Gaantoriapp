package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zoptal.gaantori.JsonClasses.Json_ForgotPw;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;


public class Fragment_Forgotpw extends Fragment  implements View.OnClickListener {

    //variable declartion
       EditText ed_email;
       ImageView img_bck;
      Button btnsubmit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_forgotpw, container, false); //inflate the view

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        ed_email=(EditText)view.findViewById(R.id.email);
        btnsubmit=(Button)view.findViewById(R.id.btnsubmit);
        img_bck=(ImageView)view.findViewById(R.id.bck);


        img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fragmentManager1 = getFragmentManager();
                Fragment_Login fragment_postad = new Fragment_Login();
                fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_postad).commit();


            }
        });

          btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                         String email=ed_email.getText().toString().trim();
                if(email.equals("")){

                    Toast.makeText(getActivity(),"Please enter Email Id", Toast.LENGTH_LONG).show();
                }
            else {

                    if (NetworkConnection.isConnectedToInternet(getActivity())) {
                        new Json_ForgotPw(getActivity()).execute(email);

                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();
                        return;

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