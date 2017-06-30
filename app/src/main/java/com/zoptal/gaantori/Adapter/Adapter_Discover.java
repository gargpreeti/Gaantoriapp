package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_Discover;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.JsonClasses.Discover;
import com.zoptal.gaantori.JsonClasses.Json_albumdetailAll;
import com.zoptal.gaantori.JsonClasses.Json_artistdetailAll;
import com.zoptal.gaantori.JsonClasses.Model_Discover;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;

import java.util.ArrayList;


public class Adapter_Discover extends BaseAdapter {


	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context ;
	Model_Discover m;
	public static String id,albumname,artistname,img,artistid;

	public Adapter_Discover(Context context, Model_Discover m) {

		this.context=context;
		this.m=m;
		inflater=LayoutInflater.from(context);// inflate the layout
			}

	public void setAddList(ArrayList<Discover> list)
	{
		this.m.getAl_discover().addAll(list);
		notifyDataSetChanged();

	}
	public ArrayList<Discover> getCurrentList()
	{
		return m.getAl_discover();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//returning total listsize
			if(m.getAl_discover().size()==0){

			Fragment_Discover.tv_main1.setVisibility(View.VISIBLE);
		}
		else{

			Fragment_Discover.tv_main1.setVisibility(View.GONE);
		}
		return m.getAl_discover().size();

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

		HolderDiscover holder=new HolderDiscover(); // view lookup cache stored in tag
		convertView=inflater.inflate(R.layout.customview_albums_browse, null); // inflate the layout


		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name=(TextView) convertView.findViewById(R.id.name);
		holder.txt_singer=(TextView) convertView.findViewById(R.id.singer);
		holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.txt_name.setText(m.getAl_discover().get(position).getAlbumtitle());
			Picasso.with(context).load(m.getAl_discover().get(position).getImage()).into(holder.img);


		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
		SpannableString content = new SpannableString(m.getAl_discover().get(position).getSinger());
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		holder.txt_singer.setText(content);

		//Click to get Artist detail
		holder.txt_singer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_artistdetailAll(context).execute(Fragment_Home1.user_id,m.getAl_discover().get(position).getArtists());

				}
				else {
					//just toast it
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}

			}
		});


		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				id=m.getAl_discover().get(position).getId();               //get album id according position
				albumname=m.getAl_discover().get(position).getAlbumtitle();  //get album name accroding position
				artistname=m.getAl_discover().get(position).getSinger();   // get artistname accroding position
				img=m.getAl_discover().get(position).getImage();             //get image accroding position
				artistid=m.getAl_discover().get(position).getArtists();     //get artist id accroding position

				String user_id;
				if(Fragment_Home1.user_id.equals("null")){
					user_id="0";
				}
				else{

					user_id=Fragment_Home1.user_id;
				}

//network connection check
				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_albumdetailAll(context).execute(m.getAl_discover().get(position).getId(),user_id);

				}
				else {
					//just toast it
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}



			}
		});
// Return the completed view to render on screen
		return convertView;

	}

}

// View lookup cache
class HolderDiscover
{
	TextView txt_name;
	TextView txt_singer;
	ImageView img;
}