package com.zoptal.gaantori.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.gaantori.Fragment.Fragment_Browse;
import com.zoptal.gaantori.Fragment.Fragment_ChooseMember;
import com.zoptal.gaantori.Fragment.Fragment_Discover;
import com.zoptal.gaantori.Fragment.Fragment_FeaturedPlaylist;
import com.zoptal.gaantori.Fragment.Fragment_Follow;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.Fragment.Fragment_MyMusic;
import com.zoptal.gaantori.Fragment.Fragment_NewReleases;
import com.zoptal.gaantori.Fragment.Fragment_Settings;
import com.zoptal.gaantori.Fragment.Fragment_TopLists;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;
    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java

    Context context;
    // private SparseBooleanArray selectedPosition = new SparseBooleanArray();

    public int selectedPosition = 0;
    MainActivity1 activity1;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        LinearLayout root;

        public ViewHolder(final View itemView, int ViewType) {
            // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
            imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
            root = (LinearLayout) itemView.findViewById(R.id.root);
            // setting holder id as 1 as the object being populated are of type item row

        }
    }


    public MyAdapter(Context context, String Titles[], int Icons[]) { // MyAdapter Constructor with titles and icons parameter
        // titles, icons, name, email, profile pic are passed from the main activity as we
        mNavTitles = Titles;                //have seen earlier
        mIcons = Icons;
        this.context = context;
        activity1 = (MainActivity1) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        ViewHolder vhItem = new ViewHolder(v, viewType);
        return vhItem;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // as the list view is going to be called after the header view so we decrement the
        // position by 1 and pass it to the holder while setting the text and image
        holder.textView.setText(mNavTitles[position]); // Setting the Text with the array of our Titles
        holder.imageView.setImageResource(mIcons[position]);// Settimg the image with array of our icons

        if (selectedPosition == position) {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.selectclr));
        } else {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.unselectclr));
        }

        switch (selectedPosition) {
            case 0:

                Fragment_Home1 fragment_home = new Fragment_Home1();
                FragmentManager fragmentManager0 = ((Activity) context).getFragmentManager();
                fragmentManager0.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_home).addToBackStack(null).commit();
                MainActivity1.Drawer.closeDrawer(Gravity.LEFT);

                break;
            case 1:
                if (NetworkConnection.isConnectedToInternet(context)) {
                    Fragment fragment_browse = new Fragment_Browse();
                    FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_browse).commit();
                    MainActivity1.Drawer.closeDrawer(Gravity.LEFT);         }
                else {


                    final Toast toast = Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();

                }
                       break;

            case 2:

                if (NetworkConnection.isConnectedToInternet(context)) {
                    Fragment_NewReleases fragment_newreleases = new Fragment_NewReleases();
                    FragmentManager fragmentManager1 = ((Activity) context).getFragmentManager();
                    fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_newreleases).commit();
                    MainActivity1.Drawer.closeDrawer(Gravity.LEFT);
                }
                else {

                    final Toast toast = Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();



                }


                break;

            case 3:
                if (NetworkConnection.isConnectedToInternet(context)) {
                    Fragment fragment_toplist = new Fragment_TopLists();
                    FragmentManager fragmentManager2 = ((Activity) context).getFragmentManager();
                    fragmentManager2.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_toplist).commit();
                    MainActivity1.Drawer.closeDrawer(Gravity.LEFT);
                }
                else {

                    final Toast toast = Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();



                }

                break;

            case 4:

                if (NetworkConnection.isConnectedToInternet(context)) {
                    Fragment_FeaturedPlaylist fragment_featuredPlaylist = new Fragment_FeaturedPlaylist();
                    FragmentManager fragmentManager3 = ((Activity) context).getFragmentManager();
                    fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_featuredPlaylist).commit();
                    MainActivity1.Drawer.closeDrawer(Gravity.LEFT);      }
                else {

                    final Toast toast = Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();


                }

                break;

            case 5:

                if(Fragment_Home1.user_id.equals("null")){
                    final Toast toast =Toast.makeText(context, "Please Register to enjoy Premium Functions", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();


                          }
                else {

                    Fragment_MyMusic fragment_myMusic = new Fragment_MyMusic();
                    FragmentManager fragmentManager4 = ((Activity) context).getFragmentManager();
                    fragmentManager4.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_myMusic).commit();
                    MainActivity1.Drawer.closeDrawer(Gravity.LEFT);

                }
                break;


            case 6:
                if(Fragment_Home1.user_id.equals("null")){

                    final Toast toast =Toast.makeText(context, "Please Register to enjoy Premium Functions", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();

                }
                else {
                    if (NetworkConnection.isConnectedToInternet(context)) {


                        Fragment_Discover fragment_discover = new Fragment_Discover();
                        FragmentManager fragmentManager5 = ((Activity) context).getFragmentManager();
                        fragmentManager5.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_discover).commit();
                        MainActivity1.Drawer.closeDrawer(Gravity.LEFT); }
                    else {
                        final Toast toast = Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT);
                        toast.show();
                        new CountDownTimer(1000, 500)
                        {
                            public void onTick(long millisUntilFinished) {toast.show();}
                            public void onFinish() {toast.cancel();}
                        }.start();


                    }
        }
                break;


            case 7:
          if(Fragment_Home1.user_id.equals("null")){

              final Toast toast =Toast.makeText(context, "Please Register to enjoy Premium Functions", Toast.LENGTH_SHORT);
              toast.show();
              new CountDownTimer(1000, 500)
              {
                  public void onTick(long millisUntilFinished) {toast.show();}
                  public void onFinish() {toast.cancel();}
              }.start();


          }
            else {

              if (NetworkConnection.isConnectedToInternet(context)) {

                  Fragment_Follow fragment_follow = new Fragment_Follow();
                  FragmentManager fragmentManager6 = ((Activity) context).getFragmentManager();
                  fragmentManager6.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_follow).commit();
                  MainActivity1.Drawer.closeDrawer(Gravity.LEFT);

              }
              else {
                  final Toast toast = Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT);
                  toast.show();
                  new CountDownTimer(1000, 500)
                  {
                      public void onTick(long millisUntilFinished) {toast.show();}
                      public void onFinish() {toast.cancel();}
                  }.start();

                   }


          }

                break;

            case 8:

                if(Fragment_Home1.user_id.equals("null")){
                    final Toast toast =Toast.makeText(context, "Please Register to enjoy Premium Functions", Toast.LENGTH_SHORT);
                    toast.show();
                    new CountDownTimer(1000, 500)
                    {
                        public void onTick(long millisUntilFinished) {toast.show();}
                        public void onFinish() {toast.cancel();}
                    }.start();


                }

else {
                    Fragment_ChooseMember fragment_choose = new Fragment_ChooseMember();
                    FragmentManager fragmentManager7 = ((Activity) context).getFragmentManager();
                    fragmentManager7.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_choose).commit();
                    MainActivity1.Drawer.closeDrawer(Gravity.LEFT);


                }
                break;


            case 9:
             Fragment_Settings fragment_setting = new Fragment_Settings();
                FragmentManager fragmentManager8 = ((Activity) context).getFragmentManager();
                fragmentManager8.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_setting).commit();
                MainActivity1.Drawer.closeDrawer(Gravity.LEFT);


                break;
            case 10:


                break;


            default:

                Fragment_Home1 fragment_home1 = new Fragment_Home1();
                FragmentManager fragmentManager11 = ((Activity) context).getFragmentManager();
                fragmentManager11.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_home1).commit();
                MainActivity1.Drawer.closeDrawer(Gravity.LEFT);

                break;

        }

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();

            }

        });

    }

    @Override
    public int getItemCount() {

        return mNavTitles.length; // the number of items in the list will be +1 the titles including the header view.
    }

    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

}
