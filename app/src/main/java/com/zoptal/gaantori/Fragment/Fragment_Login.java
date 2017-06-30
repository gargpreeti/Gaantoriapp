package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zoptal.gaantori.JsonClasses.Json_SignIn;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.LoginSignup;

public class Fragment_Login extends Fragment  implements View.OnClickListener {

    //Variable declartion
    private EditText ed_username, ed_pw1;
    private ImageView img_bck;
    private TextView tv_fpw;
    private Button btnlogin;
    CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_login,container, false); //inflate the view

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ed_username = (EditText) view.findViewById(R.id.username);
        ed_pw1 = (EditText) view.findViewById(R.id.pw1);

        img_bck = (ImageView) view.findViewById(R.id.bck);
        tv_fpw = (TextView) view.findViewById(R.id.fpw);
        btnlogin = (Button) view.findViewById(R.id.btnlogin);

        img_bck.setOnClickListener(this);
        tv_fpw.setOnClickListener(this);
        btnlogin.setOnClickListener(this);


       ed_username.setText(LoginSignup.emailaddress);


        return view;

    }

    protected void getLoginDetails(LoginButton login_button){

        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {

              //  Intent intent = new Intent(getActivity(),MainActivity1.class);
               // startActivity(intent);

            }

            @Override
            public void onCancel() {
                // code for cancellation
            }

            @Override
            public void onError(FacebookException exception) {
                //  code to handle error
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnlogin:

                signin();

                break;


            case R.id.bck:

                Intent intent_bck= new Intent(getActivity(), LoginSignup.class);
                startActivity(intent_bck);

                break;

            case R.id.fpw:

                FragmentManager fragmentManager1 = getFragmentManager();
                Fragment_Forgotpw fragment_pw = new Fragment_Forgotpw();
                fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_pw).commit();

                break;
        }

    }

// method for login
    public void signin() {

        String email = ed_username.getText().toString().trim().toLowerCase();
        String pw = ed_pw1.getText().toString().trim().toLowerCase();


        if (email.equals("")) {
            ed_username.setError("Username should not be empty");
            return;
        }
        if (pw.equals("")) {
            ed_pw1.setError("Password should not be empty");

        }
        else {
            if (NetworkConnection.isConnectedToInternet(getActivity())) {
                new Json_SignIn(getActivity()).execute(email,pw);

            }

            else {
                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();
                return;

            }




        }


    }


}


