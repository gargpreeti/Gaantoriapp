package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.Adapter.Adapter_Discover;
import com.zoptal.gaantori.JsonClasses.Json_Discover;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_Discover extends Fragment  implements View.OnClickListener {

    //Variable declration
    private GridView grid;
    TextView tv_main;
    Boolean flag=false;
    int pagenum=1;
    Adapter_Discover adapter_discover;
     View view;
    public static TextView tv_main1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

     view = inflater.inflate(R.layout.activity_featured_playlist, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(MainActivity1.drawable);
        MainActivity1.tv_bck.setVisibility(View.INVISIBLE);
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
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }
                return false;
            }

        });

        MainActivity1.textToolHeader.setVisibility(View.VISIBLE);
        MainActivity1.ed_srch.setVisibility(View.INVISIBLE);

          grid=(GridView)view.findViewById(R.id.gridView);
          tv_main=(TextView)view.findViewById(R.id.tv_main);
          tv_main.setText("Recommend for you");

        tv_main1=(TextView)view.findViewById(R.id.tv_main1);

        adapter_discover= new Adapter_Discover(getActivity(),Json_Discover.model_discover);
        grid.setAdapter(adapter_discover);


        if (NetworkConnection.isConnectedToInternet(getActivity())) {

            new Json_Discover(getActivity(), adapter_discover).execute(Fragment_Home1.user_id, "1");
        }
        else {
            Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


        }

        grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == 2)
                    flag = true;

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                // TODO Auto-generated method stub
                if ((visibleItemCount == (totalItemCount - firstVisibleItem))
                        && flag) {
                    flag = false;

                    pagenum++;
                    String newpage= String.valueOf(pagenum);


                    if(Fragment_Home1.user_id.equals("null")){

                        final Toast toast = Toast.makeText(getActivity(),"Please Login",Toast.LENGTH_SHORT);
                        toast.show();
                                new CountDownTimer(1000, 500)
                        {
                            public void onTick(long millisUntilFinished) {toast.show();}
                            public void onFinish() {toast.cancel();}
                        }.start();
                }

                    else {

                        if (NetworkConnection.isConnectedToInternet(getActivity())) {

                            new Json_Discover(getActivity(), adapter_discover).execute(Fragment_Home1.user_id, newpage);

                        }
                        else {
                            Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                        }
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