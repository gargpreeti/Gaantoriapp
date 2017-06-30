package com.zoptal.gaantori.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.gaantori.Fragment.Fragment_PlaylistDetail1;
import com.zoptal.gaantori.JsonClasses.NewRelease;
import com.zoptal.gaantori.R;
import com.zoptal.gaantori.main.MainActivity1;

import java.util.ArrayList;


public class Adapter_FeaturedPlaylist extends BaseAdapter {

	//Varibale declartion
	//initialize view's

	LayoutInflater inflater;
	Context context ;
	ArrayList<NewRelease> al_newrelease;
	public static String pid;

	public Adapter_FeaturedPlaylist(Context context,ArrayList<NewRelease> al_newrelease) {

		this.context=context;
		this.al_newrelease=al_newrelease;
		inflater=LayoutInflater.from(context); // inflate the layout
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
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		HolderFeatured holder=new HolderFeatured();  // view lookup cache stored
		convertView=inflater.inflate(R.layout.customview_albums_browse, null);  // inflate the layout

		// get the TextView and then set the text (item name) and tag (item ID) values
		holder.txt_name=(TextView) convertView.findViewById(R.id.name);
		holder.txt_singer=(TextView) convertView.findViewById(R.id.singer);
		holder.img=(ImageView) convertView.findViewById(R.id.img);

		// object item based on the position
		// Populate the data from the data object via the viewHolder object
		// into the template view.

	    	holder.txt_name.setText(al_newrelease.get(position).getName());
			Picasso.with(context).load(al_newrelease.get(position).getImage()).into(holder.img);

//click to playlist detail
		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				pid=al_newrelease.get(position).getId();
				Fragment_PlaylistDetail1 fragment_detail = new Fragment_PlaylistDetail1();
				FragmentManager fragmentManager3 = ((Activity) context).getFragmentManager();
				fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment_detail).addToBackStack(null).commit();
				MainActivity1.Drawer.closeDrawer(Gravity.LEFT);


			}
		});
		// Return the completed view to render on screen
		return convertView;

	}

}
// View lookup cache
class HolderFeatured
{
	TextView txt_name;
	TextView txt_singer;
	ImageView img;
}