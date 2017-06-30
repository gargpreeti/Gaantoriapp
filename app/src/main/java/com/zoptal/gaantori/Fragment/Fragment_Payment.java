package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.zoptal.gaantori.JsonClasses.Json_Payment;
import com.zoptal.gaantori.R;


public class Fragment_Payment extends Fragment {

    //variable declartion
    private WebView webView;
    String successUrl;
    boolean loadingFinished = true;
    boolean redirect = false;
    ImageView bck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_paypal1, container, false); //inflate the view


        bck=(ImageView)view.findViewById(R.id.bck);

        //webview open
         webView = (WebView) view.findViewById(R.id.webView1);
         webView.setWebViewClient(new WebViewClient());
         webView.getSettings().setJavaScriptEnabled(true);
         webView.loadUrl(Json_Payment.res);


        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_PaymentMethod fragment_detail = new Fragment_PaymentMethod();
                FragmentManager fragmentManager3 = getFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();



            }
        });


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


                        //HIDE LOADING IT HAS FINISHED

                    }
                else {

                    successUrl ="http://www.gaantori.com/app/webroot/android/recurring-payment/success.php";

                    if (url.equals(successUrl.trim())) {


                        Toast.makeText(getActivity(), "Payment successfull!", Toast.LENGTH_LONG).show();
                        Fragment_Login fragment_detail=new Fragment_Login();
                        FragmentManager fragmentManager3 =getFragmentManager();
                        fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();

                    } else {

                        Toast.makeText(getActivity(), "Payment failed!", Toast.LENGTH_LONG).show();
                        Fragment_Signup fragment_detail=new Fragment_Signup();
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
