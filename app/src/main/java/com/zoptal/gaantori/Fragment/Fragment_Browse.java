package com.zoptal.gaantori.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.Adapter.Adapter_AritistBrowse;
import com.zoptal.gaantori.Adapter.Adapter_NewRelease;
import com.zoptal.gaantori.JsonClasses.Json_BrowseAlbum;
import com.zoptal.gaantori.JsonClasses.Json_BrowseAlbumAlbhabet;
import com.zoptal.gaantori.JsonClasses.Json_BrowseArtist;
import com.zoptal.gaantori.JsonClasses.Json_BrowseArtistAlbhabet;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;
import com.zoptal.gaantori.main.MainActivity1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class

Fragment_Browse extends Fragment  implements View.OnClickListener {

    //initilization
    private GridView grid;
    private Button btn_albums,btn_artists;
    Boolean flag=false;
    int pagenum=1;
    Adapter_NewRelease adapter_newRelease;
    Adapter_AritistBrowse adapter_aritistBrowse;
    Dialog dialog1;
      static final String[] numbers = new String[] {
            "All","#",
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

     View view;
     ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

         view = inflater.inflate(R.layout.activity_artists_list, container, false); //inflate the view

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        MainActivity1.mDrawerToggle.setHomeAsUpIndicator(MainActivity1.drawable);

        MainActivity1.tv_bck.setVisibility(View.INVISIBLE);
        MainActivity1.img_srch.setVisibility(View.VISIBLE);


        final SharedPreferences prefs=getActivity().getSharedPreferences("srchvalue", Context.MODE_PRIVATE); // store in shared preferences

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

        list=(ListView)view.findViewById(R.id.side_index);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.side_index_item, numbers);

        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                int position1 = list.getPositionForView(view);
                String text =arg0.getItemAtPosition(position).toString();

                for (int i = 0; i < list.getChildCount(); i++) {
                    if (position == i) {
                        list.getChildAt(i).setBackgroundColor(Color.parseColor("#F0754C"));
                    } else {
                        list.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }

                }
            if(position==0){
                       adapter_newRelease= new Adapter_NewRelease(getActivity(),Json_BrowseAlbum.model_nwrel);
                grid.setAdapter(adapter_newRelease);


                if (NetworkConnection.isConnectedToInternet(getActivity())) {
                    new Json_BrowseAlbum(getActivity(),adapter_newRelease).execute("","1");
                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }



            }

            else   if(position==1){

                if (NetworkConnection.isConnectedToInternet(getActivity())) {
                    new Json_BrowseAlbumAlbhabet(getActivity(), grid).execute("", "1","hsh");
                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }



            }
                     else{

                if (NetworkConnection.isConnectedToInternet(getActivity())) {
                    new Json_BrowseAlbumAlbhabet(getActivity(), grid).execute("", "1", text);
                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }

                }

            }
        });

        MainActivity1.textToolHeader.setVisibility(View.VISIBLE);
        MainActivity1.ed_srch.setVisibility(View.INVISIBLE);
        MainActivity1.img_cross.setVisibility(View.INVISIBLE);

        //find the id of views
        btn_albums=(Button)view.findViewById(R.id.btn_albums);
        btn_artists=(Button)view.findViewById(R.id.btn_artists);
        grid=(GridView)view.findViewById(R.id.gridView);

        adapter_newRelease= new Adapter_NewRelease(getActivity(), Json_BrowseAlbum.model_nwrel);
        grid.setAdapter(adapter_newRelease);


        if (NetworkConnection.isConnectedToInternet(getActivity())) {
            new Json_BrowseAlbum(getActivity(),adapter_newRelease).execute("","1");

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


                    if (NetworkConnection.isConnectedToInternet(getActivity())) {
                        new Json_BrowseAlbum(getActivity(),adapter_newRelease).execute("",newpage);

                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                    }




                }
            }
        });

        btn_albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                btn_albums.setBackgroundResource(R.mipmap.selected);
                btn_artists.setBackgroundResource(R.mipmap.quick_select);


                adapter_newRelease= new Adapter_NewRelease(getActivity(), Json_BrowseAlbum.model_nwrel);
                grid.setAdapter(adapter_newRelease);


                if (NetworkConnection.isConnectedToInternet(getActivity())) {
                    new Json_BrowseAlbum(getActivity(),adapter_newRelease).execute("","1");


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


                            if (NetworkConnection.isConnectedToInternet(getActivity())) {
                                new Json_BrowseAlbum(getActivity(),adapter_newRelease).execute("",newpage);




                            }
                            else {
                                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                            }




                        }
                    }
                });

           list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {


                        int position1 = list.getPositionForView(view);
                        String text =arg0.getItemAtPosition(position).toString();

                        for (int i = 0; i < list.getChildCount(); i++) {
                            if (position == i) {
                                list.getChildAt(i).setBackgroundColor(Color.parseColor("#F0754C"));
                            } else {
                                list.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                            }

                        }
                                  if(position==0){

                                       adapter_newRelease= new Adapter_NewRelease(getActivity(),Json_BrowseAlbum.model_nwrel);
                            grid.setAdapter(adapter_newRelease);


                            if (NetworkConnection.isConnectedToInternet(getActivity())) {
                                new Json_BrowseAlbum(getActivity(),adapter_newRelease).execute("","1");


                            }
                            else {
                                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                            }




                        }
                        else  if(position==1){



                            if (NetworkConnection.isConnectedToInternet(getActivity())) {
                                new Json_BrowseAlbumAlbhabet(getActivity(), grid).execute("", "1","hsh");

                            }
                            else {
                                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                            }





                        }
                        else{


                            if (NetworkConnection.isConnectedToInternet(getActivity())) {
                                new Json_BrowseAlbumAlbhabet(getActivity(),grid).execute("","1",text);
                            }
                            else {
                                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                            }


                        }


                    }
                });


            }
        });


        btn_artists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                btn_artists.setBackgroundResource(R.mipmap.selected);
                btn_albums.setBackgroundResource(R.mipmap.quick_select);

                adapter_aritistBrowse= new Adapter_AritistBrowse(getActivity(), Json_BrowseArtist.model_browseArtist);
                grid.setAdapter(adapter_aritistBrowse);

                if (NetworkConnection.isConnectedToInternet(getActivity())) {
                    new Json_BrowseArtist(getActivity(), adapter_aritistBrowse).execute(Fragment_Home1.user_id, "1");
                }
                else {
                    Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                        int position1 = list.getPositionForView(view);
                        String text =arg0.getItemAtPosition(position).toString();

                        for (int i = 0; i < list.getChildCount(); i++) {
                            if (position == i) {
                                list.getChildAt(i).setBackgroundColor(Color.parseColor("#F0754C"));
                            } else {
                                list.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                            }

                        }


                        if(position==0){

                            adapter_aritistBrowse= new Adapter_AritistBrowse(getActivity(), Json_BrowseArtist.model_browseArtist);
                            grid.setAdapter(adapter_aritistBrowse);


                            if (NetworkConnection.isConnectedToInternet(getActivity())) {
                                new Json_BrowseArtist(getActivity(), adapter_aritistBrowse).execute(Fragment_Home1.user_id, "1");
                            }
                            else {
                                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                            }






                        }
                        else  if(position==1){


                            if (NetworkConnection.isConnectedToInternet(getActivity())) {
                                new Json_BrowseArtistAlbhabet(getActivity(), grid).execute(Fragment_Home1.user_id, "1","hsh");
                            }
                            else {
                                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                            }





                        }
                        else{


                            if (NetworkConnection.isConnectedToInternet(getActivity())) {

                                new Json_BrowseArtistAlbhabet(getActivity(), grid).execute(Fragment_Home1.user_id, "1",text);

                            }
                            else {
                                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                            }

                        }



                              }
                });


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

                            if (NetworkConnection.isConnectedToInternet(getActivity())) {

                                new Json_BrowseArtist(getActivity(), adapter_aritistBrowse).execute(Fragment_Home1.user_id,newpage);

                            }
                            else {
                                Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                            }



                        }
                    }
                });




            }
        });

        return view;

    }

    @Override
    public void onClick(View v) {


    }



}