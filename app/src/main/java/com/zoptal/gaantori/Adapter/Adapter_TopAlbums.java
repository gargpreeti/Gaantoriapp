package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.JsonClasses.Json_albumdetailAll;
import com.zoptal.gaantori.JsonClasses.Model_Top;
import com.zoptal.gaantori.JsonClasses.NewRelease;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;

import java.util.ArrayList;


public class Adapter_TopAlbums extends BaseAdapter {

	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context ;
	Model_Top m;
	public static String id,albumname,artistname,img;

	public Adapter_TopAlbums(Context context,Model_Top m) {

		this.context=context;
		this.m=m;
		inflater=LayoutInflater.from(context);// inflate the layout

		}

	public void setAddList(ArrayList<NewRelease> list)
	{

		this.m.getAl_newrelease().addAll(list);
		notifyDataSetChanged();

	}
	public ArrayList<NewRelease> getCurrentList()
	{
		return m.getAl_newrelease();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return m.getAl_newrelease().size();
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
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		HolderTopAlbums holder=new HolderTopAlbums();  // view lookup cache stored in tag
		convertView=inflater.inflate(R.layout.customview_top_lists, null); // inflate the layout

		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name=(TextView) convertView.findViewById(R.id.tv_name);
		holder.txt_aname=(TextView) convertView.findViewById(R.id.tv_aname);
		holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.linear=(LinearLayout) convertView.findViewById(R.id.linear);

		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
		holder.txt_name.setText(m.getAl_newrelease().get(position).getName());
		holder.txt_aname.setText(m.getAl_newrelease().get(position).getArtists());
			Picasso.with(context).load(m.getAl_newrelease().get(position).getImage()).into(holder.img);

		holder.linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				id=m.getAl_newrelease().get(position).getId();
				albumname=m.getAl_newrelease().get(position).getName();
				artistname=m.getAl_newrelease().get(position).getArtists();
				img=m.getAl_newrelease().get(position).getImage();

				String user_id;
				if(Fragment_Home1.user_id.equals("null")){
					user_id="0";
				}
				else{

					user_id=Fragment_Home1.user_id;
				}


				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_albumdetailAll(context).execute(m.getAl_newrelease().get(position).getId(),user_id);
				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}

		}
		});

		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				id=m.getAl_newrelease().get(position).getId();
				albumname=m.getAl_newrelease().get(position).getName();
				artistname=m.getAl_newrelease().get(position).getArtists();
				img=m.getAl_newrelease().get(position).getImage();


				String user_id;
				if(Fragment_Home1.user_id.equals("null")){
					user_id="0";
				}
				else{

					user_id=Fragment_Home1.user_id;
				}


				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_albumdetailAll(context).execute(m.getAl_newrelease().get(position).getId(),user_id);
				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}


			}
		});

		return convertView;

	}

}
// View lookup cache
class HolderTopAlbums
{
	TextView txt_name;
	TextView txt_aname;
	ImageView img;
	LinearLayout linear;

}