package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.JsonClasses.Json_CancelMembership;
import com.zoptal.gaantori.JsonClasses.Json_MemberPlan;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_ChooseMember extends Fragment {

    //variable declration
            ImageView imgfree,imgpaid,imgpaidyearly;
            Button btn_select,btn_cncl;
            public static TextView textView;
            RelativeLayout rel_free,rel_paid,rel_paidyearly;
            boolean chk=false;
            String utype;
            View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

      view = inflater.inflate(R.layout.activity_chosemember, container, false); //inflate the view

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(MainActivity1.drawable);


        MainActivity1.tv_bck.setVisibility(View.INVISIBLE);
        MainActivity1.img_srch.setVisibility(View.VISIBLE);
        btn_select=(Button)view.findViewById(R.id.select);
        btn_cncl=(Button)view.findViewById(R.id.btn_cncl);
        textView=(TextView)view.findViewById(R.id.textView);



        if (NetworkConnection.isConnectedToInternet(getActivity())) {

            new Json_MemberPlan(getActivity()).execute();

        }
        else {
            Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


        }




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


        imgfree=(ImageView)view.findViewById(R.id.imgfree);
        imgpaid=(ImageView)view.findViewById(R.id.imgpaid);
        imgpaidyearly=(ImageView)view.findViewById(R.id.imgpaidyearly);


        rel_free=(RelativeLayout)view.findViewById(R.id.rel_free);
        rel_paid=(RelativeLayout)view.findViewById(R.id.rel_paid);
        rel_paidyearly=(RelativeLayout)view.findViewById(R.id.rel_paidyearly);


         Log.e("ptype1",""+Fragment_Home1.ptype1);

//        if(Fragment_Home1.ptype1.equals("M")){
//            imgpaidyearly.setVisibility(View.GONE);
//            imgfree.setVisibility(View.GONE);
//            imgpaid.setVisibility(View.VISIBLE);
//            btn_select.setVisibility(View.GONE);
//            btn_cncl.setVisibility(View.VISIBLE);
//            chk=false;
//
//        }
//        else if(Fragment_Home1.ptype1.equals("Y")){
//
//            imgpaidyearly.setVisibility(View.VISIBLE);
//            imgfree.setVisibility(View.GONE);
//            imgpaid.setVisibility(View.GONE);
//            btn_select.setVisibility(View.GONE);
//            btn_cncl.setVisibility(View.VISIBLE);
//            chk=false;
//
//        }
//        else{
//            imgpaidyearly.setVisibility(View.GONE);
//            imgfree.setVisibility(View.VISIBLE);
//            imgpaid.setVisibility(View.GONE);
//            btn_select.setVisibility(View.GONE);
//            btn_cncl.setVisibility(View.VISIBLE);
//            chk=false;
//        }

        if(Fragment_Home1.utype1.equals("Basic")){

            imgpaidyearly.setVisibility(View.GONE);
            imgfree.setVisibility(View.VISIBLE);
            imgpaid.setVisibility(View.GONE);
            btn_select.setVisibility(View.GONE);
          //  btn_cncl.setVisibility(View.VISIBLE);
            chk=false;
        }
        else if(Fragment_Home1.utype1.equals("Premium")) {
            if (Fragment_Home1.ptype1.equals("M")) {
                imgpaidyearly.setVisibility(View.GONE);
                imgfree.setVisibility(View.GONE);
                imgpaid.setVisibility(View.VISIBLE);
                btn_select.setVisibility(View.GONE);
                btn_cncl.setVisibility(View.VISIBLE);
                chk = false;

            } else if (Fragment_Home1.ptype1.equals("Y")) {

                imgpaidyearly.setVisibility(View.VISIBLE);
                imgfree.setVisibility(View.GONE);
                imgpaid.setVisibility(View.GONE);
                btn_select.setVisibility(View.GONE);
                btn_cncl.setVisibility(View.VISIBLE);
                chk = false;

            }

        }



        utype=Fragment_Home1.utype1;

        rel_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgfree.setVisibility(View.VISIBLE);
                imgpaid.setVisibility(View.INVISIBLE);
                imgpaidyearly.setVisibility(View.INVISIBLE);
                btn_select.setEnabled(true);
                 utype="Basic";
                chk=false;
                btn_select.setVisibility(View.GONE);
                btn_cncl.setVisibility(View.VISIBLE);


                if(Fragment_Home1.utype1.equals("Basic")){

                    btn_cncl.setVisibility(View.GONE);
                    btn_select.setVisibility(View.GONE);

                }

               if(Fragment_Home1.utype1.equals("Premium")){
                   btn_select.setEnabled(false);
                   btn_cncl.setVisibility(View.VISIBLE);
                   btn_select.setVisibility(View.GONE);

               }

            }
        });

        rel_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_select.setEnabled(true);
                imgfree.setVisibility(View.INVISIBLE);
                imgpaid.setVisibility(View.VISIBLE);
                imgpaidyearly.setVisibility(View.INVISIBLE);

                if(Fragment_Home1.utype1.equals("Basic")){
                    chk=true;
                    btn_cncl.setVisibility(View.GONE);
                    btn_select.setVisibility(View.VISIBLE);
                }
                else {
                    if (Fragment_Home1.ptype1.equals("M")) {
                        chk = false;
                        btn_select.setVisibility(View.GONE);
                        btn_cncl.setVisibility(View.VISIBLE);
                    } else if (Fragment_Home1.ptype1.equals("Y")) {
                        chk = true;
                        btn_cncl.setVisibility(View.GONE);
                        btn_select.setVisibility(View.VISIBLE);
                    }
//                    else {
//                        chk = true;
//                        btn_cncl.setVisibility(View.GONE);
//                        btn_select.setVisibility(View.VISIBLE);
//                    }


                }


                utype="Premium";


            }
        });

        rel_paidyearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_select.setEnabled(true);
                imgfree.setVisibility(View.INVISIBLE);
                imgpaid.setVisibility(View.INVISIBLE);
                imgpaidyearly.setVisibility(View.VISIBLE);


                if(Fragment_Home1.utype1.equals("Basic")){
                    chk=true;
                    btn_cncl.setVisibility(View.GONE);
                    btn_select.setVisibility(View.VISIBLE);
                }
                else {
                    if (Fragment_Home1.ptype1.equals("Y")) {
                        chk = false;
                        btn_select.setVisibility(View.GONE);
                        btn_cncl.setVisibility(View.VISIBLE);
                    } else if (Fragment_Home1.ptype1.equals("M")) {
                        chk = true;
                        btn_cncl.setVisibility(View.GONE);
                        btn_select.setVisibility(View.VISIBLE);
                    }
//                    else {
//                        chk = true;
//                        btn_cncl.setVisibility(View.GONE);
//                        btn_select.setVisibility(View.VISIBLE);
//                    }
                }
                utype="Premium";


            }
        });

if(chk==true){

    btn_cncl.setVisibility(View.GONE);
    btn_select.setVisibility(View.VISIBLE);
}

        btn_cncl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e("userid cncl----",""+Fragment_Home1.user_id);
                        new Json_CancelMembership(getActivity()).execute("",Fragment_Home1.user_id,"");
                    }
                });
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!Fragment_Home1.user_id.equals("null")) {

                     if (utype.equals("Premium")) {
                         Fragment_PaymentMethod2 fragment_detail = new Fragment_PaymentMethod2();
                         FragmentManager fragmentManager3 = getFragmentManager();
                         fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).commit();
                     }
                }

               if(!Fragment_Home1.user_id.equals("null")){

                   if(utype.equals("Basic")){

                       final Toast toast =Toast.makeText(getActivity(), "You have already free membership", Toast.LENGTH_LONG);
                       toast.show();
                       new CountDownTimer(1000, 500)
                       {
                           public void onTick(long millisUntilFinished) {toast.show();}
                           public void onFinish() {toast.cancel();}
                       }.start();

                   }

                     }

            }
        });


        return view;

    }




}