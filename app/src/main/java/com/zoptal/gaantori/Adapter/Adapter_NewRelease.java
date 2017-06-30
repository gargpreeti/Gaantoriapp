package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.JsonClasses.Json_albumdetailAll;
import com.zoptal.gaantori.JsonClasses.Json_artistdetailAll;
import com.zoptal.gaantori.JsonClasses.Model_NewRelease;
import com.zoptal.gaantori.JsonClasses.NewRelease;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Adapter_NewRelease extends BaseAdapter {


    //Varibale declartion
    //initialize view's
    public static String id, albumname, artistname, img, artistid;
    LayoutInflater inflater;
    Context context;
    Model_NewRelease m;
    ArrayList<NewRelease> al_newrelease = new ArrayList<NewRelease>();
    int j;


    public Adapter_NewRelease(Context c, Model_NewRelease m) {
        context = c;
        this.m = m;
        this.al_newrelease = new ArrayList<>();
        al_newrelease.addAll(m.getAl_newrelease());
        inflater = LayoutInflater.from(context);  // inflate the layout
    }


    // Filter Class
    public void filter(String charText) {
        al_newrelease = new ArrayList<>();
        if (charText.trim().equals("#")) {

            // al_newrelease.addAll(m.getAl_newrelease());
        }
        if (charText.trim().equals("All")) {

            al_newrelease.addAll(m.getAl_newrelease());
        }
        if (charText.trim().isEmpty()) {
            al_newrelease.addAll(m.getAl_newrelease());

        } else {

            for (int i = 0; i < m.getAl_newrelease().size(); i++) {
                if (m.getAl_newrelease().get(i).getName().startsWith(charText)) {
                    al_newrelease.add(m.getAl_newrelease().get(i));

                }
            }
        }
        notifyDataSetChanged();
    }

    public void setAddList(ArrayList<NewRelease> list) {
        m.getAl_newrelease().addAll(list);
        this.al_newrelease.addAll(list);
        notifyDataSetChanged();
    }

    public ArrayList<NewRelease> getCurrentList() {
        return al_newrelease;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return al_newrelease.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        HolderNewRel holder = new HolderNewRel();
        convertView = inflater.inflate(R.layout.customview_albums_browse1, null);

        // get the TextView and then set the text (item name) and tag (item ID) values
        holder.txt_name = (TextView) convertView.findViewById(R.id.name);
        holder.listSinger = (ListView) convertView.findViewById(R.id.listSinger);

        ViewGroup.LayoutParams listh = (ViewGroup.LayoutParams) holder.listSinger.getLayoutParams();
        listh.height =80;
        holder.listSinger.setLayoutParams(listh);

        artistname=al_newrelease.get(position).getArtists();
        List<String> alistname = Arrays.asList(artistname.split(","));



        id = al_newrelease.get(position).getArtistids();
        final List<String> alistid = Arrays.asList(id.split(","));

        for(int i=0;i<alistname.size();i++){

            int totalh=alistname.size();


            listh.height =totalh*80;
            holder.listSinger.setLayoutParams(listh);

        }


        final ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(context,R.layout.list_itemsinger,alistname);
        stringArrayAdapter.setNotifyOnChange(true);
        holder.listSinger.setAdapter(stringArrayAdapter);


        holder.img = (ImageView) convertView.findViewById(R.id.img);
        holder.txt_name.setText(al_newrelease.get(position).getName());

        String str = al_newrelease.get(position).getArtists();
        final List<String> alist = Arrays.asList(str.split(","));

        Picasso.with(context).load(al_newrelease.get(position).getImage()).into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = al_newrelease.get(position).getId();
                albumname = al_newrelease.get(position).getName();
                artistname = al_newrelease.get(position).getArtists();
                img = al_newrelease.get(position).getImage();
                artistid = al_newrelease.get(position).getArtistids();


                String user_id;
                if(Fragment_Home1.user_id.equals("null")){
                    user_id="0";
                }
                else{

                    user_id=Fragment_Home1.user_id;
                }

                if (NetworkConnection.isConnectedToInternet(context)) {

                    new Json_albumdetailAll(context).execute(al_newrelease.get(position).getId(),user_id);

                }
                else {
                    Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }


            }
        });


        holder.listSinger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {



                if (NetworkConnection.isConnectedToInternet(context)) {


                    new Json_artistdetailAll(context).execute(Fragment_Home1.user_id, alistid.get(position));

                }
                else {
                    Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                }





            }
        });

        return convertView;

    }

    class HolderNewRel {
        TextView txt_name;
        ListView listSinger;
        ImageView img;

    }
}
