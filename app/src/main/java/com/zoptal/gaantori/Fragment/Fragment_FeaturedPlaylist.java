package com.zoptal.gaantori.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.JsonClasses.Json_featurelist;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Fragment_FeaturedPlaylist extends Fragment  implements View.OnClickListener {

    //Declration
      private GridView grid;
      private TextView tv_main;
      Dialog dialog1;
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

       final SharedPreferences prefs=getActivity().getSharedPreferences("srchvalue",Context.MODE_PRIVATE); //store in shared preferences

       MainActivity1.img_srch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               MainActivity1.ed_srch.setVisibility(View.VISIBLE);
               MainActivity1.textToolHeader.setVisibility(View.INVISIBLE);
               MainActivity1.img_srch.setVisibility(View.INVISIBLE);
               MainActivity1.img_cross.setVisibility(View.VISIBLE);

               MainActivity1.img_cross.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                                 MainActivity1.ed_srch.setText("");

                   }
               });

               if(prefs.getStringSet("srchvalue", null)!=null) {
                   Set<String> set = prefs.getStringSet("srchvalue", null);
                   List<String> sample = new ArrayList<String>(set);

                   dialog1 = new Dialog(getActivity(), android.R.style.Theme_Translucent){
                       @Override
                       public boolean onTouchEvent(MotionEvent event) {
                           // Tap anywhere to close dialog.
                           dialog1.dismiss();
                           return true;
                       }
                   };


                   dialog1.setCanceledOnTouchOutside(true);
                   dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                   dialog1.setCancelable(true);
                   dialog1.setContentView(R.layout.dialoglist);
                   dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                   final ListView listView = (ListView) dialog1.findViewById(R.id.listView1);


                   ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                           android.R.layout.simple_list_item_1, android.R.id.text1, sample);

                   listView.setAdapter(adapter);

                   //listview clicklistener
                   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                           int position1 = listView.getPositionForView(view);
                           String text =arg0.getItemAtPosition(position).toString();

                           MainActivity1.ed_srch.setText(text);

                       }
                   });

                   dialog1.show();
               }

           }
       });
       MainActivity1.ed_srch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   if (dialog1.isShowing()) {
                       dialog1.dismiss();
                   }
               }
               catch (NullPointerException e) {
               }
           }
       });

       MainActivity1.img_cross.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                        MainActivity1.ed_srch.setText("");

           }
       });

       MainActivity1.ed_srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

               if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                   ArrayList<String> set = new ArrayList<String>();
                   if(prefs.getStringSet("srchvalue", null)!=null) {
                       Set<String> set1 = prefs.getStringSet("srchvalue", null);
                       List<String> sample = new ArrayList<String>(set1);


                       for(int i=0;i<sample.size();i++){

                           set.add(sample.get(i));

                       }

                       set.add(String.valueOf(MainActivity1.ed_srch.getText()));
                       SharedPreferences.Editor edit=prefs.edit();
                       set1.addAll(set);

                       edit.putStringSet("srchvalue", set1);
                       edit.commit();


                   }
                   else{

                       Set<String> set1 = new HashSet<String>();
                       set.add(String.valueOf(MainActivity1.ed_srch.getText()));
                       SharedPreferences.Editor edit=prefs.edit();
                       set1.addAll(set);

                       edit.putStringSet("srchvalue", set1);
                       edit.commit();
                   }


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
          tv_main.setText("Featured Playlists");


       if (NetworkConnection.isConnectedToInternet(getActivity())) {

           new Json_featurelist(getActivity(), grid).execute("0");

       }
       else {
           //just toast it

           Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


       }


        return view;

    }


    @Override
    public void onClick(View v) {


    }




}