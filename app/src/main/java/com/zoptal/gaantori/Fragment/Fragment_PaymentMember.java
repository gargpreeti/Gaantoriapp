package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zoptal.gaantori.JsonClasses.Json_PaymentMember;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_PaymentMember extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    public static final String Usertype = "usertype";
    public static final String Packagetype = "packagetype";
    private WebView webView;
    String successUrl;
    boolean loadingFinished = true;
    boolean redirect = false;
    String utype,ptype;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_paypal, container, false);

          sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE); // store data in shared preferences


        MainActivity1.tv_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager1 = getFragmentManager();
                Fragment_PaymentMethodmembr fragment_postad = new Fragment_PaymentMethodmembr();
                fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_postad).commit();


            }
        });


//open webview
        webView = (WebView) view.findViewById(R.id.webView1);
         webView.setWebViewClient(new WebViewClient());
         webView.getSettings().setJavaScriptEnabled(true);
         webView.loadUrl(Json_PaymentMember.res);



        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                webView.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!redirect) {
                    loadingFinished = true;


                    }
                else {


                    successUrl ="http://www.gaantori.com/app/webroot/android/recurring-payment/success.php";

                 Log.e("success url-----",""+url);

                    if (url.equals(successUrl.trim())) {


                        Toast.makeText(getActivity(), "Payment successfull!", Toast.LENGTH_LONG).show();
                        Fragment_Home1 fragment_detail=new Fragment_Home1();
                        FragmentManager fragmentManager3 =getFragmentManager();
                        fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();

                        utype="Premium";
                        ptype=Fragment_PaymentMethodmembr.ptype;

                        //to store data in share preferences
                SharedPreferences.Editor editor1 =sharedpreferences1.edit();
                editor1.putString(Usertype,utype);
                editor1.putString(Packagetype,ptype);
                editor1.commit();


                    }
                    else {

                        Toast.makeText(getActivity(), "Payment failed!", Toast.LENGTH_LONG).show();
                        Fragment_ChooseMember fragment_detail=new Fragment_ChooseMember();
                        FragmentManager fragmentManager3 =getFragmentManager();
                        fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();

                    }
                        redirect = false;
                    }

            }
        });


        return view;

    }



    }

