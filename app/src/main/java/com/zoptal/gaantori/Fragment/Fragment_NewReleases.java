package com.zoptal.gaantori.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
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

import com.zoptal.gaantori.Adapter.Adapter_NewRelease;
import com.zoptal.gaantori.JsonClasses.Json_NewRelease;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;


public class Fragment_NewReleases extends Fragment  implements View.OnClickListener {

    //variable declartion
    private GridView grid;
    Boolean flag=false;
    int pagenum=1;
    TextView tv_main;
     Adapter_NewRelease adapter_newRelease;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view = inflater.inflate(R.layout.activity_featured_playlist, container, false); //inflate the view

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
          tv_main.setText("New Releases");

//adapter instance
        adapter_newRelease= new Adapter_NewRelease(getActivity(), Json_NewRelease.model_nwrel);
        grid.setAdapter(adapter_newRelease);



        if (NetworkConnection.isConnectedToInternet(getActivity())) {



            new Json_NewRelease(getActivity(),adapter_newRelease).execute("","1");

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
                   // String filter = "0";
                    pagenum++;
                    String newpage= String.valueOf(pagenum);


                    if (NetworkConnection.isConnectedToInternet(getActivity())) {


                        new Json_NewRelease(getActivity(),adapter_newRelease).execute("",newpage);


                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


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