package com.zoptal.gaantori.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_Home1;
import com.zoptal.gaantori.JsonClasses.Json_albumdetailAll;
import com.zoptal.gaantori.JsonClasses.Json_artistdetailAll;
import com.zoptal.gaantori.JsonClasses.Model_Home;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.common.NetworkConnection;

public class Adapter_HomeNewRelease extends BaseAdapter {


	//Varibale declartion
	//initialize view's
	LayoutInflater inflater;
	Context context ;
     Model_Home m;
	public static String id,albumname,artistname,img,artistid;

	public Adapter_HomeNewRelease(Context c,Model_Home m) {

		context = c;
		this.m = m;

		inflater=LayoutInflater.from(context); // inflate the layout
			}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return m.getAl_homenewreleases().size();
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

		HolderHomeRelease holder=new HolderHomeRelease();  // view lookup cache stored
		convertView=inflater.inflate(R.layout.customview_albums_browse, null);   // inflate the layout


		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name=(TextView) convertView.findViewById(R.id.name);
		holder.txt_singer=(TextView) convertView.findViewById(R.id.singer);
		holder.img=(ImageView) convertView.findViewById(R.id.img);


		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.
		holder.txt_name.setText(m.getAl_homenewreleases().get(position).getAlbumtitle());
		holder.txt_singer.setText(m.getAl_homenewreleases().get(position).getSinger());

		Picasso.with(context).load(m.getAl_homenewreleases().get(position).getImage()).into(holder.img);

//To see album detail
		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				id=m.getAl_homenewreleases().get(position).getId();
				albumname=m.getAl_homenewreleases().get(position).getAlbumtitle();
				artistname=m.getAl_homenewreleases().get(position).getSinger();
				img=m.getAl_homenewreleases().get(position).getImage();
				artistid=m.getAl_homenewreleases().get(position).getArtistid();


				String user_id;
				if(Fragment_Home1.user_id.equals("null")){
					user_id="0";
				}
				else{

					user_id=Fragment_Home1.user_id;
				}


				if (NetworkConnection.isConnectedToInternet(context)) {
					new Json_albumdetailAll(context).execute(id,user_id);
				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}



			}
		});
// To see artist detail
		holder.txt_singer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				id=m.getAl_homenewreleases().get(position).getArtistid();

				if (NetworkConnection.isConnectedToInternet(context)) {

					new Json_artistdetailAll(context).execute(Fragment_Home1.user_id, id);
				}
				else {
					Toast.makeText(context,"Please Check your internet connection", Toast.LENGTH_SHORT).show();


				}





			}
		});
		// Return the completed view to render on screen
		return convertView;

	}

}
// View lookup cache
class HolderHomeRelease
{
	TextView txt_name;
	TextView txt_singer;
	ImageView img;
}