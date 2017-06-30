package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.JsonClasses.Json_FollowUnfollow;
import com.zoptal.gaantori.JsonClasses.Json_artistdetail;
import com.zoptal.gaantori.JsonClasses.Json_artistdetail1;
import com.zoptal.gaantori.JsonClasses.Json_artistdetailAll;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_ArtistDetailAll extends Fragment  implements View.OnClickListener {

    //initilization
    private ListView list;
    private Button btn_popular,btn_albums;
     private ImageView img;
    private TextView aname,fnum;
    private Button btn,btn1;
     LinearLayout linear_shuffle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Window window = getActivity().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        View view = inflater.inflate(R.layout.activity_artists_detail, container, false); //inflate layout

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MainActivity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(null);

        MainActivity1.tv_bck.setVisibility(View.VISIBLE);

        MainActivity1.img_srch.setVisibility(View.VISIBLE);


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
                    return true;
                }
                return false;
            }

        });

        MainActivity1.textToolHeader.setVisibility(View.VISIBLE);
        MainActivity1.ed_srch.setVisibility(View.INVISIBLE);


        btn_popular=(Button)view.findViewById(R.id.btn_popular);
        btn_albums=(Button)view.findViewById(R.id.btn_albums);
        linear_shuffle=(LinearLayout)view.findViewById(R.id.linear_shuffle);

        linear_shuffle.setVisibility(View.GONE);
        list=(ListView)view.findViewById(R.id.list);

        //find the id of views
        img=(ImageView)view.findViewById(R.id.img);
        aname=(TextView)view.findViewById(R.id.name);
        fnum=(TextView)view.findViewById(R.id.follow);
        btn=(Button)view.findViewById(R.id.btn);
        btn1=(Button)view.findViewById(R.id.btn1);

        Picasso.with(getActivity()).load(Json_artistdetailAll.artistimage).into(img);
        aname.setText(Json_artistdetailAll.artistname);
        fnum.setText(Json_artistdetailAll.fcount);



        if(Json_artistdetailAll.fstat.equals("following")){

             btn.setVisibility(View.INVISIBLE);
             btn1.setVisibility(View.VISIBLE);
        }
        else {
          btn1.setVisibility(View.INVISIBLE);
           btn.setVisibility(View.VISIBLE);
        }


        if(Fragment_Home1.user_id.equals("null")){
            btn.setEnabled(false);
            btn1.setEnabled(false);
            btn.setTextColor(Color.parseColor("#ffffff"));
            btn1.setTextColor(Color.parseColor("#ffffff"));
        }
   else{
            btn.setEnabled(true);
            btn1.setEnabled(true);
        }
        //To click for follow or unfollow
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               btn1.setVisibility(View.VISIBLE);
              btn.setVisibility(View.INVISIBLE);

                if(Fragment_Home1.user_id.equals("null")){

                    final Toast toast = Toast.makeText(getActivity(),"Please Login to follow Artist",Toast.LENGTH_SHORT);
                    toast.show();
                    // Toast.makeText(getActivity(), "Please Login see Top List", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();

                             }
                else {

                    if (NetworkConnection.isConnectedToInternet(getActivity())) {

                        new Json_FollowUnfollow(getActivity()).execute(Fragment_Home1.user_id,Json_artistdetailAll.id, "1");

                        int previouscount= Integer.parseInt(fnum.getText().toString());
                        int newvalue=1;
                        newvalue+=previouscount;
                                   fnum.setText(String.valueOf(newvalue));

                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                    }





                }

            }
        });

      btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             btn.setVisibility(View.VISIBLE);
             btn1.setVisibility(View.INVISIBLE);

                if(Fragment_Home1.user_id.equals("null")){

                    final Toast toast = Toast.makeText(getActivity(),"Please Login to unfollow Artist",Toast.LENGTH_SHORT);
                    toast.show();
                    // Toast.makeText(getActivity(), "Please Login see Top List", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();

                       }
                else {

                    if (NetworkConnection.isConnectedToInternet(getActivity())) {

                        new Json_FollowUnfollow(getActivity()).execute(Fragment_Home1.user_id, Json_artistdetailAll.id, "0");

                        int previouscount= Integer.parseInt(fnum.getText().toString());
                        int newvalue=1;
                        newvalue-=previouscount;
                                if(newvalue<0) {
                            fnum.setText("0");
                        }
                        else {
                            fnum.setText(String.valueOf(newvalue));
                        }
                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                    }




                     }
            }
        });


        if (NetworkConnection.isConnectedToInternet(getActivity())) {

            new Json_artistdetail1(getActivity(), list).execute(Fragment_Home1.user_id,Json_artistdetailAll.id);

        }
        else {
            Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


        }
        // For artist Detail
        btn_albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_albums.setBackgroundResource(R.mipmap.selected);
                btn_popular.setBackgroundResource(R.mipmap.quick_select);



                if (NetworkConnection.isConnectedToInternet(getActivity())) {
                    new Json_artistdetail(getActivity(), list).execute(Fragment_Home1.user_id,Json_artistdetailAll.id);

                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }

            }
        });
        // To get popular list
        btn_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_popular.setBackgroundResource(R.mipmap.selected);
                btn_albums.setBackgroundResource(R.mipmap.quick_select);



                if (NetworkConnection.isConnectedToInternet(getActivity())) {
                    new Json_artistdetail1(getActivity(), list).execute(Fragment_Home1.user_id,Json_artistdetailAll.id);
                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }


            }
        });


        MainActivity1.tv_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              FragmentManager fragmentManager1 = getFragmentManager();
             fragmentManager1.popBackStack();

            }
        });
        return view;

    }


    @Override
    public void onClick(View v) {


    }




}